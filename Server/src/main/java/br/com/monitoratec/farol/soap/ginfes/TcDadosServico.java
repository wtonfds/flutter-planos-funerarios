
package br.com.monitoratec.farol.soap.ginfes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de tcDadosServico complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="tcDadosServico"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Valores" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcValores"/&gt;
 *         &lt;element name="ItemListaServico" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsItemListaServico"/&gt;
 *         &lt;element name="CodigoCnae" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsCodigoCnae" minOccurs="0"/&gt;
 *         &lt;element name="CodigoTributacaoMunicipio" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsCodigoTributacao" minOccurs="0"/&gt;
 *         &lt;element name="Discriminacao" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsDiscriminacao"/&gt;
 *         &lt;element name="CodigoMunicipio" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsCodigoMunicipioIbge"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tcDadosServico", propOrder = {
    "valores",
    "itemListaServico",
    "codigoCnae",
    "codigoTributacaoMunicipio",
    "discriminacao",
    "codigoMunicipio"
})
public class TcDadosServico {

    @XmlElement(name = "Valores", required = true)
    protected TcValores valores;
    @XmlElement(name = "ItemListaServico", required = true)
    protected String itemListaServico;
    @XmlElement(name = "CodigoCnae")
    protected Integer codigoCnae;
    @XmlElement(name = "CodigoTributacaoMunicipio")
    protected String codigoTributacaoMunicipio;
    @XmlElement(name = "Discriminacao", required = true)
    protected String discriminacao;
    @XmlElement(name = "CodigoMunicipio")
    protected int codigoMunicipio;

    /**
     * Obtém o valor da propriedade valores.
     * 
     * @return
     *     possible object is
     *     {@link TcValores }
     *     
     */
    public TcValores getValores() {
        return valores;
    }

    /**
     * Define o valor da propriedade valores.
     * 
     * @param value
     *     allowed object is
     *     {@link TcValores }
     *     
     */
    public void setValores(TcValores value) {
        this.valores = value;
    }

    /**
     * Obtém o valor da propriedade itemListaServico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemListaServico() {
        return itemListaServico;
    }

    /**
     * Define o valor da propriedade itemListaServico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemListaServico(String value) {
        this.itemListaServico = value;
    }

    /**
     * Obtém o valor da propriedade codigoCnae.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodigoCnae() {
        return codigoCnae;
    }

    /**
     * Define o valor da propriedade codigoCnae.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodigoCnae(Integer value) {
        this.codigoCnae = value;
    }

    /**
     * Obtém o valor da propriedade codigoTributacaoMunicipio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoTributacaoMunicipio() {
        return codigoTributacaoMunicipio;
    }

    /**
     * Define o valor da propriedade codigoTributacaoMunicipio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoTributacaoMunicipio(String value) {
        this.codigoTributacaoMunicipio = value;
    }

    /**
     * Obtém o valor da propriedade discriminacao.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiscriminacao() {
        return discriminacao;
    }

    /**
     * Define o valor da propriedade discriminacao.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscriminacao(String value) {
        this.discriminacao = value;
    }

    /**
     * Obtém o valor da propriedade codigoMunicipio.
     * 
     */
    public int getCodigoMunicipio() {
        return codigoMunicipio;
    }

    /**
     * Define o valor da propriedade codigoMunicipio.
     * 
     */
    public void setCodigoMunicipio(int value) {
        this.codigoMunicipio = value;
    }

}
