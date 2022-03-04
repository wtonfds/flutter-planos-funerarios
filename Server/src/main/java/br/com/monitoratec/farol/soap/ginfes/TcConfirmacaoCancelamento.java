
package br.com.monitoratec.farol.soap.ginfes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de tcConfirmacaoCancelamento complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="tcConfirmacaoCancelamento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Pedido" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcPedidoCancelamento"/&gt;
 *         &lt;element name="InfConfirmacaoCancelamento" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcInfConfirmacaoCancelamento"/&gt;
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
@XmlType(name = "tcConfirmacaoCancelamento", propOrder = {
    "pedido",
    "infConfirmacaoCancelamento"
})
public class TcConfirmacaoCancelamento {

    @XmlElement(name = "Pedido", required = true)
    protected TcPedidoCancelamento pedido;
    @XmlElement(name = "InfConfirmacaoCancelamento", required = true)
    protected TcInfConfirmacaoCancelamento infConfirmacaoCancelamento;
    @XmlAttribute(name = "Id")
    protected String id;

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
     * Obtém o valor da propriedade infConfirmacaoCancelamento.
     * 
     * @return
     *     possible object is
     *     {@link TcInfConfirmacaoCancelamento }
     *     
     */
    public TcInfConfirmacaoCancelamento getInfConfirmacaoCancelamento() {
        return infConfirmacaoCancelamento;
    }

    /**
     * Define o valor da propriedade infConfirmacaoCancelamento.
     * 
     * @param value
     *     allowed object is
     *     {@link TcInfConfirmacaoCancelamento }
     *     
     */
    public void setInfConfirmacaoCancelamento(TcInfConfirmacaoCancelamento value) {
        this.infConfirmacaoCancelamento = value;
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
