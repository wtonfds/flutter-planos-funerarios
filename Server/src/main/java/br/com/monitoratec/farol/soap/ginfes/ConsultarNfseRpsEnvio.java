
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
 *         &lt;element name="IdentificacaoRps" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcIdentificacaoRps"/&gt;
 *         &lt;element name="Prestador" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcIdentificacaoPrestador"/&gt;
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
    "identificacaoRps",
    "prestador",
    "signature"
})
@XmlRootElement(name = "ConsultarNfseRpsEnvio", namespace = "http://www.ginfes.com.br/servico_consultar_nfse_rps_envio_v03.xsd")
public class ConsultarNfseRpsEnvio {

    @XmlElement(name = "IdentificacaoRps", namespace = "http://www.ginfes.com.br/servico_consultar_nfse_rps_envio_v03.xsd", required = true)
    protected TcIdentificacaoRps identificacaoRps;
    @XmlElement(name = "Prestador", namespace = "http://www.ginfes.com.br/servico_consultar_nfse_rps_envio_v03.xsd", required = true)
    protected TcIdentificacaoPrestador prestador;
    @XmlElement(name = "Signature", namespace = "http://www.w3.org/2000/09/xmldsig#")
    protected SignatureType signature;

    /**
     * Obtém o valor da propriedade identificacaoRps.
     * 
     * @return
     *     possible object is
     *     {@link TcIdentificacaoRps }
     *     
     */
    public TcIdentificacaoRps getIdentificacaoRps() {
        return identificacaoRps;
    }

    /**
     * Define o valor da propriedade identificacaoRps.
     * 
     * @param value
     *     allowed object is
     *     {@link TcIdentificacaoRps }
     *     
     */
    public void setIdentificacaoRps(TcIdentificacaoRps value) {
        this.identificacaoRps = value;
    }

    /**
     * Obtém o valor da propriedade prestador.
     * 
     * @return
     *     possible object is
     *     {@link TcIdentificacaoPrestador }
     *     
     */
    public TcIdentificacaoPrestador getPrestador() {
        return prestador;
    }

    /**
     * Define o valor da propriedade prestador.
     * 
     * @param value
     *     allowed object is
     *     {@link TcIdentificacaoPrestador }
     *     
     */
    public void setPrestador(TcIdentificacaoPrestador value) {
        this.prestador = value;
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
