
package br.com.monitoratec.farol.soap.ginfes;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de tcSubstituicaoNfse complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="tcSubstituicaoNfse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SubstituicaoNfse" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcInfSubstituicaoNfse"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Signature" maxOccurs="2"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tcSubstituicaoNfse", propOrder = {
    "substituicaoNfse",
    "signature"
})
public class TcSubstituicaoNfse {

    @XmlElement(name = "SubstituicaoNfse", required = true)
    protected TcInfSubstituicaoNfse substituicaoNfse;
    @XmlElement(name = "Signature", namespace = "http://www.w3.org/2000/09/xmldsig#", required = true)
    protected List<SignatureType> signature;

    /**
     * Obtém o valor da propriedade substituicaoNfse.
     * 
     * @return
     *     possible object is
     *     {@link TcInfSubstituicaoNfse }
     *     
     */
    public TcInfSubstituicaoNfse getSubstituicaoNfse() {
        return substituicaoNfse;
    }

    /**
     * Define o valor da propriedade substituicaoNfse.
     * 
     * @param value
     *     allowed object is
     *     {@link TcInfSubstituicaoNfse }
     *     
     */
    public void setSubstituicaoNfse(TcInfSubstituicaoNfse value) {
        this.substituicaoNfse = value;
    }

    /**
     * Gets the value of the signature property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signature property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignature().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SignatureType }
     * 
     * 
     */
    public List<SignatureType> getSignature() {
        if (signature == null) {
            signature = new ArrayList<SignatureType>();
        }
        return this.signature;
    }

}
