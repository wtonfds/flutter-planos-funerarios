
package br.com.monitoratec.farol.soap.ginfes;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="ListaNfse"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="CompNfse" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcCompNfse" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
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
    "listaNfse",
    "listaMensagemRetorno"
})
@XmlRootElement(name = "ConsultarNfseResposta", namespace = "http://www.ginfes.com.br/servico_consultar_nfse_resposta_v03.xsd")
public class ConsultarNfseResposta {

    @XmlElement(name = "ListaNfse", namespace = "http://www.ginfes.com.br/servico_consultar_nfse_resposta_v03.xsd")
    protected ConsultarNfseResposta.ListaNfse listaNfse;
    @XmlElement(name = "ListaMensagemRetorno")
    protected ListaMensagemRetorno listaMensagemRetorno;

    /**
     * Obtém o valor da propriedade listaNfse.
     * 
     * @return
     *     possible object is
     *     {@link ConsultarNfseResposta.ListaNfse }
     *     
     */
    public ConsultarNfseResposta.ListaNfse getListaNfse() {
        return listaNfse;
    }

    /**
     * Define o valor da propriedade listaNfse.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsultarNfseResposta.ListaNfse }
     *     
     */
    public void setListaNfse(ConsultarNfseResposta.ListaNfse value) {
        this.listaNfse = value;
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
     *         &lt;element name="CompNfse" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcCompNfse" maxOccurs="unbounded" minOccurs="0"/&gt;
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
        "compNfse"
    })
    public static class ListaNfse {

        @XmlElement(name = "CompNfse", namespace = "http://www.ginfes.com.br/servico_consultar_nfse_resposta_v03.xsd")
        protected List<TcCompNfse> compNfse;

        /**
         * Gets the value of the compNfse property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the compNfse property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCompNfse().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TcCompNfse }
         * 
         * 
         */
        public List<TcCompNfse> getCompNfse() {
            if (compNfse == null) {
                compNfse = new ArrayList<TcCompNfse>();
            }
            return this.compNfse;
        }

    }

}
