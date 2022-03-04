
package br.com.monitoratec.farol.soap.ginfes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de tcPedidoCancelamento complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="tcPedidoCancelamento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InfPedidoCancelamento" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcInfPedidoCancelamento"/&gt;
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
@XmlType(name = "tcPedidoCancelamento", propOrder = {
    "infPedidoCancelamento",
    "signature"
})
public class TcPedidoCancelamento {

    @XmlElement(name = "InfPedidoCancelamento", required = true)
    protected TcInfPedidoCancelamento infPedidoCancelamento;
    @XmlElement(name = "Signature", namespace = "http://www.w3.org/2000/09/xmldsig#")
    protected SignatureType signature;

    /**
     * Obtém o valor da propriedade infPedidoCancelamento.
     * 
     * @return
     *     possible object is
     *     {@link TcInfPedidoCancelamento }
     *     
     */
    public TcInfPedidoCancelamento getInfPedidoCancelamento() {
        return infPedidoCancelamento;
    }

    /**
     * Define o valor da propriedade infPedidoCancelamento.
     * 
     * @param value
     *     allowed object is
     *     {@link TcInfPedidoCancelamento }
     *     
     */
    public void setInfPedidoCancelamento(TcInfPedidoCancelamento value) {
        this.infPedidoCancelamento = value;
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
