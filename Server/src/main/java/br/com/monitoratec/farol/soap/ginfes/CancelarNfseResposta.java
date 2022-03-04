
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
 *       &lt;choice&gt;
 *         &lt;element name="Cancelamento" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcCancelamentoNfse"/&gt;
 *         &lt;element ref="{http://www.ginfes.com.br/tipos_v03.xsd}ListaMensagemRetorno"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cancelamento",
    "listaMensagemRetorno"
})
@XmlRootElement(name = "CancelarNfseResposta", namespace = "http://www.ginfes.com.br/servico_cancelar_nfse_resposta_v03.xsd")
public class CancelarNfseResposta {

    @XmlElement(name = "Cancelamento", namespace = "http://www.ginfes.com.br/servico_cancelar_nfse_resposta_v03.xsd")
    protected TcCancelamentoNfse cancelamento;
    @XmlElement(name = "ListaMensagemRetorno")
    protected ListaMensagemRetorno listaMensagemRetorno;

    /**
     * Obtém o valor da propriedade cancelamento.
     * 
     * @return
     *     possible object is
     *     {@link TcCancelamentoNfse }
     *     
     */
    public TcCancelamentoNfse getCancelamento() {
        return cancelamento;
    }

    /**
     * Define o valor da propriedade cancelamento.
     * 
     * @param value
     *     allowed object is
     *     {@link TcCancelamentoNfse }
     *     
     */
    public void setCancelamento(TcCancelamentoNfse value) {
        this.cancelamento = value;
    }

    /**
     * Obtém o valor da propriedade listaMensagemRetorno.
     * 
     * @return
     *     possible object is
     *     {@link ListaMensagemRetorno }
     *     
     */
    public ListaMensagemRetorno getListaMensagemRetorno() {
        return listaMensagemRetorno;
    }

    /**
     * Define o valor da propriedade listaMensagemRetorno.
     * 
     * @param value
     *     allowed object is
     *     {@link ListaMensagemRetorno }
     *     
     */
    public void setListaMensagemRetorno(ListaMensagemRetorno value) {
        this.listaMensagemRetorno = value;
    }

}
