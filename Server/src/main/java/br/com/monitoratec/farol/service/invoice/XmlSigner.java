package br.com.monitoratec.farol.service.invoice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.xml.transform.StringResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.List;

@Service
public class XmlSigner {
    private final X509Certificate certificate;
    private final PrivateKey privateKey;

    public XmlSigner(@Value("${ginfes.certificate-file}") Resource certificateFile,
                     @Value("${ginfes.certificate-password}") String certificatePassword) {
        final char[] password = certificatePassword.toCharArray();

        final KeyStore keyStore;
        // Spring is returning exception when calling certificateFile.getFile()
        // so the workaround here is to get the file input stream and create a new file on the project
        // with the same name and content
        try {
            InputStream inputStream = certificateFile.getInputStream();
            Files.copy(inputStream, Paths.get("farol.pfx"), StandardCopyOption.REPLACE_EXISTING);
            File file = new File("farol.pfx");

            keyStore = KeyStore.getInstance(file, password);
        } catch (Exception e) {
            throw new CertificateException(e);
        }

        final KeyStore.PrivateKeyEntry privateKeyEntry = findPrivateKeyEntry(keyStore, password);
        certificate = (X509Certificate) privateKeyEntry.getCertificate();
        privateKey = privateKeyEntry.getPrivateKey();
    }

    private KeyStore.PrivateKeyEntry findPrivateKeyEntry(KeyStore keyStore, char[] password) {
        try {
            final Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                final String alias = aliases.nextElement();

                if (keyStore.isKeyEntry(alias)) {
                    return (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, new KeyStore.PasswordProtection(password));
                }
            }
        } catch (Exception e) {
            throw new CertificateException(e);
        }

        //Not found
        throw new CertificateException("Private key entry not found");
    }

    /**
     * Sign the XML document according to the provider's certificate. This will include an element of type "Signature"
     * to the elements identified by {@code tagToSign} if specified, or to the document root if not. This method will then
     * look for the first descendant of that element named {@code tagWithId} and use its ID (identified by attribute "Id")
     * for calculating the sign value. If {@code tagWithId} is {@code null}, the signature will be calculated using the
     * whole document.
     *
     * @param xml       The XML document without signature.
     * @param tagToSign The tag to be signed, or {@code null} if the whole document should be signed.
     * @param tagWithId The tag containing the ID to use for calculating the sign value.
     * @return The signed XML document.
     */
    public String signXml(String xml, String tagToSign, String tagWithId) {
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);

            final Document document = factory.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));

            if (tagToSign != null) {
                final NodeList elementsToSign = document.getElementsByTagNameNS("*", tagToSign);
                for (int i = 0; i < elementsToSign.getLength(); ++i) {
                    final Element elementToSign = (Element) elementsToSign.item(i);

                    final Element elementWithId = (Element) elementToSign.getElementsByTagNameNS("*", tagWithId).item(0);
                    final String id = elementWithId.getAttribute("Id");
                    elementWithId.setIdAttribute("Id", true);

                    sign(id, elementToSign);
                }
            } else {
                final String id;
                if (tagWithId != null) {
                    final Element elementWithId = (Element) document.getElementsByTagNameNS("*", tagWithId).item(0);

                    id = elementWithId.getAttribute("Id");
                    elementWithId.setIdAttribute("Id", true);
                } else {
                    id = null;
                }

                sign(id, document.getFirstChild());
            }

            final Transformer transformer = TransformerFactory.newInstance().newTransformer();

            final StringResult result = new StringResult();
            transformer.transform(new DOMSource(document), result);

            return result.toString()
                    .replaceAll("\\r\\n", "")
                    .replace("&#13;", "")
                    .replace(" standalone=\"no\"", "");
        } catch (Exception e) {
            throw new CertificateException(e);
        }
    }

    private void sign(String id, Node nodeToSign) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, MarshalException, XMLSignatureException {
        final XMLSignatureFactory factory = XMLSignatureFactory.getInstance("DOM");
        final CanonicalizationMethod canonicalizationMethod = factory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, (XMLStructure) null);

        final SignatureMethod signatureMethod = factory.newSignatureMethod(SignatureMethod.RSA_SHA1, null);

        final DigestMethod digestMethod = factory.newDigestMethod(DigestMethod.SHA1, null);

        final List<Transform> transforms = List.of(
                factory.newTransform(Transform.ENVELOPED, (XMLStructure) null),
                factory.newTransform(CanonicalizationMethod.INCLUSIVE, (XMLStructure) null)
        );

        //Add a reference; if id is set, prefix it with "#", otherwise pass an empty String (meaning the whole document)
        final Reference reference = factory.newReference(id != null ? "#" + id : "", digestMethod, transforms, null, null);

        final SignedInfo signedInfo = factory.newSignedInfo(canonicalizationMethod, signatureMethod, List.of(reference));

        final KeyInfoFactory keyInfoFactory = factory.getKeyInfoFactory();
        final X509Data x509Data = keyInfoFactory.newX509Data(List.of(certificate));

        final KeyInfo keyInfo = keyInfoFactory.newKeyInfo(List.of(x509Data));

        final XMLSignature signature = factory.newXMLSignature(signedInfo, keyInfo);
        signature.sign(new DOMSignContext(privateKey, nodeToSign));
    }
}
