
package br.com.monitoratec.farol.soap.ginfes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de anonymous complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="LoteRps" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcLoteRps"/&gt;
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
@XmlType(name = "", propOrder = {
    "loteRps",
    "signature"
})
@XmlRootElement(name = "EnviarLoteRpsEnvio", namespace = "http://www.ginfes.com.br/servico_enviar_lote_rps_envio_v03.xsd")
public class EnviarLoteRpsEnvio {

    @XmlElement(name = "LoteRps", namespace = "http://www.ginfes.com.br/servico_enviar_lote_rps_envio_v03.xsd", required = true)
    protected TcLoteRps loteRps;
    @XmlElement(name = "Signature", namespace = "http://www.w3.org/2000/09/xmldsig#")
    protected SignatureType signature;

    /**
     * Obtém o valor da propriedade loteRps.
     * 
     * @return
     *     possible object is
     *     {@link TcLoteRps }
     *     
     */
    public TcLoteRps getLoteRps() {
        return loteRps;
    }

    /**
     * Define o valor da propriedade loteRps.
     * 
     * @param value
     *     allowed object is
     *     {@link TcLoteRps }
     *     
     */
    public void setLoteRps(TcLoteRps value) {
        this.loteRps = value;
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
