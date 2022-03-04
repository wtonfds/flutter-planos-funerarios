package br.com.monitoratec.farol.service.invoice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.security.KeyStore;

@Service
public class CertificateManager {
    private final Resource certificateFile;
    private final String certificatePassword;

    public CertificateManager(@Value("${ginfes.certificate-file}") Resource certificateFile,
                              @Value("${ginfes.certificate-password}") String certificatePassword) {
        this.certificateFile = certificateFile;
        this.certificatePassword = certificatePassword;
    }

    public SSLSocketFactory createSslSocketFactory() {
        try {
            final KeyStore keyStore = loadKeystore();

            final KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore, certificatePassword.toCharArray());

            final TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init((KeyStore) null);

            final SSLContext context = SSLContext.getInstance("TLSv1.2");
            context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            return context.getSocketFactory();
        } catch (Exception e) {
            throw new CertificateException(e);
        }
    }

    private KeyStore loadKeystore() throws Exception {
        final File file = new File("farol.pfx");
        return KeyStore.getInstance(file, certificatePassword.toCharArray());
    }
}
