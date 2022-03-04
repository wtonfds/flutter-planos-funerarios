
package br.com.monitoratec.farol.soap.ginfes;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de tcInfSubstituicaoNfse complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="tcInfSubstituicaoNfse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NfseSubstituidora" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsNumeroNfse"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="Id" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsIdTag" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tcInfSubstituicaoNfse", propOrder = {
    "nfseSubstituidora"
})
public class TcInfSubstituicaoNfse {

    @XmlElement(name = "NfseSubstituidora", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger nfseSubstituidora;
    @XmlAttribute(name = "Id")
    protected String id;

    /**
     * Obtém o valor da propriedade nfseSubstituidora.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNfseSubstituidora() {
        return nfseSubstituidora;
    }

    /**
     * Define o valor da propriedade nfseSubstituidora.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNfseSubstituidora(BigInteger value) {
        this.nfseSubstituidora = value;
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
