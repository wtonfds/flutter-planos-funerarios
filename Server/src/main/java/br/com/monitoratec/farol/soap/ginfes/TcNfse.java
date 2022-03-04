
package br.com.monitoratec.farol.soap.ginfes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de tcNfse complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="tcNfse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InfNfse" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcInfNfse"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Signature" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tcNfse", propOrder = {
    "infNfse",
    "signature"
})
public class TcNfse {

    @XmlElement(name = "InfNfse", required = true)
    protected TcInfNfse infNfse;
    @XmlElement(name = "Signature", namespace = "http://www.w3.org/2000/09/xmldsig#")
    protected SignatureType signature;

    /**
     * Obtém o valor da propriedade infNfse.
     * 
     * @return
     *     possible object is
     *     {@link TcInfNfse }
     *     
     */
    public TcInfNfse getInfNfse() {
        return infNfse;
    }

    /**
     * Define o valor da propriedade infNfse.
     * 
     * @param value
     *     allowed object is
     *     {@link TcInfNfse }
     *     
     */
    public void setInfNfse(TcInfNfse value) {
        this.infNfse = value;
    }

    /**
     * Obtém o valor da propriedade signature.
     * 
     * @return
     *     possible object is
     *     {@link SignatureType }
     *     
     */
    public SignatureType getSignature() {
        return signature;
    }

    /**
     * Define o valor da propriedade signature.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureType }
     *     
     */
    public void setSignature(SignatureType value) {
        this.signature = value;
    }

}
