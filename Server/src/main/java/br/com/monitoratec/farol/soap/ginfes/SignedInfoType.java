
package br.com.monitoratec.farol.soap.ginfes;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java de SignedInfoType complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="SignedInfoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}CanonicalizationMethod"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}SignatureMethod"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Reference" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignedInfoType", namespace = "http://www.w3.org/2000/09/xmldsig#", propOrder = {
    "canonicalizationMethod",
    "signatureMethod",
    "reference"
})
public class SignedInfoType {

    @XmlElement(name = "CanonicalizationMethod", required = true)
    protected CanonicalizationMethodType canonicalizationMethod;
    @XmlElement(name = "SignatureMethod", required = true)
    protected SignatureMethodType signatureMethod;
    @XmlElement(name = "Reference", required = true)
    protected List<ReferenceType> reference;
    @XmlAttribute(name = "Id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Obtém o valor da propriedade canonicalizationMethod.
     * 
     * @return
     *     possible object is
     *     {@link CanonicalizationMethodType }
     *     
     */
    public CanonicalizationMethodType getCanonicalizationMethod() {
        return canonicalizationMethod;
    }

    /**
     * Define o valor da propriedade canonicalizationMethod.
     * 
     * @param value
     *     allowed object is
     *     {@link CanonicalizationMethodType }
     *     
     */
    public void setCanonicalizationMethod(CanonicalizationMethodType value) {
        this.canonicalizationMethod = value;
    }

    /**
     * Obtém o valor da propriedade signatureMethod.
     * 
     * @return
     *     possible object is
     *     {@link SignatureMethodType }
     *     
     */
    public SignatureMethodType getSignatureMethod() {
        return signatureMethod;
    }

    /**
     * Define o valor da propriedade signatureMethod.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureMethodType }
     *     
     */
    public void setSignatureMethod(SignatureMethodType value) {
        this.signatureMethod = value;
    }

    /**
     * Gets the value of the reference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReferenceType }
     * 
     * 
     */
    public List<ReferenceType> getReference() {
        if (reference == null) {
            reference = new ArrayList<ReferenceType>();
        }
        return this.reference;
    }

    /**
     * Obtém o valor da propriedade id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Define o valor da propriedade id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
