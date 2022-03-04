
package br.com.monitoratec.farol.soap.ginfes;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &lt;sequence&gt;
 *           &lt;element name="NumeroLote" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsNumeroLote"/&gt;
 *           &lt;element name="Situacao" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsSituacaoLoteRps"/&gt;
 *         &lt;/sequence&gt;
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
    "numeroLote",
    "situacao",
    "listaMensagemRetorno"
})
@XmlRootElement(name = "ConsultarSituacaoLoteRpsResposta", namespace = "http://www.ginfes.com.br/servico_consultar_situacao_lote_rps_resposta_v03.xsd")
public class ConsultarSituacaoLoteRpsResposta {

    @XmlElement(name = "NumeroLote", namespace = "http://www.ginfes.com.br/servico_consultar_situacao_lote_rps_resposta_v03.xsd")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger numeroLote;
    @XmlElement(name = "Situacao", namespace = "http://www.ginfes.com.br/servico_consultar_situacao_lote_rps_resposta_v03.xsd")
    protected Byte situacao;
    @XmlElement(name = "ListaMensagemRetorno")
    protected ListaMensagemRetorno listaMensagemRetorno;

    /**
     * Obtém o valor da propriedade numeroLote.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumeroLote() {
        return numeroLote;
    }

    /**
     * Define o valor da propriedade numeroLote.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumeroLote(BigInteger value) {
        this.numeroLote = value;
    }

    /**
     * Obtém o valor da propriedade situacao.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getSituacao() {
        return situacao;
    }

    /**
     * Define o valor da propriedade situacao.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setSituacao(Byte value) {
        this.situacao = value;
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
