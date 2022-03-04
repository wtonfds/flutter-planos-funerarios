
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
 *         &lt;element name="Pedido" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcPedidoCancelamento"/&gt;
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
    "pedido",
    "signature"
})
@XmlRootElement(name = "CancelarNfseEnvio", namespace = "http://www.ginfes.com.br/servico_cancelar_nfse_envio_v03.xsd")
public class CancelarNfseEnvio {

    @XmlElement(name = "Pedido", namespace = "", required = true)
    protected TcPedidoCancelamento pedido;
    @XmlElement(name = "Signature", namespace = "http://www.w3.org/2000/09/xmldsig#")
    protected SignatureType signature;

    /**
     * Obtém o valor da propriedade pedido.
     * 
     * @return
     *     possible object is
     *     {@link TcPedidoCancelamento }
     *     
     */
    public TcPedidoCancelamento getPedido() {
        return pedido;
    }

    /**
     * Define o valor da propriedade pedido.
     * 
     * @param value
     *     allowed object is
     *     {@link TcPedidoCancelamento }
     *     
     */
    public void setPedido(TcPedidoCancelamento value) {
        this.pedido = value;
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
