
package br.com.monitoratec.farol.soap.ginfes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java de tcInfRps complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="tcInfRps"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IdentificacaoRps" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcIdentificacaoRps"/&gt;
 *         &lt;element name="DataEmissao" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="NaturezaOperacao" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsNaturezaOperacao"/&gt;
 *         &lt;element name="RegimeEspecialTributacao" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsRegimeEspecialTributacao" minOccurs="0"/&gt;
 *         &lt;element name="OptanteSimplesNacional" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsSimNao"/&gt;
 *         &lt;element name="IncentivadorCultural" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsSimNao"/&gt;
 *         &lt;element name="Status" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsStatusRps"/&gt;
 *         &lt;element name="RpsSubstituido" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcIdentificacaoRps" minOccurs="0"/&gt;
 *         &lt;element name="Servico" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcDadosServico"/&gt;
 *         &lt;element name="Prestador" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcIdentificacaoPrestador"/&gt;
 *         &lt;element name="Tomador" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcDadosTomador"/&gt;
 *         &lt;element name="IntermediarioServico" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcIdentificacaoIntermediarioServico" minOccurs="0"/&gt;
 *         &lt;element name="ConstrucaoCivil" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcDadosConstrucaoCivil" minOccurs="0"/&gt;
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
@XmlType(name = "tcInfRps", propOrder = {
    "identificacaoRps",
    "dataEmissao",
    "naturezaOperacao",
    "regimeEspecialTributacao",
    "optanteSimplesNacional",
    "incentivadorCultural",
    "status",
    "rpsSubstituido",
    "servico",
    "prestador",
    "tomador",
    "intermediarioServico",
    "construcaoCivil"
})
public class TcInfRps {

    @XmlElement(name = "IdentificacaoRps", required = true)
    protected TcIdentificacaoRps identificacaoRps;
    @XmlElement(name = "DataEmissao", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataEmissao;
    @XmlElement(name = "NaturezaOperacao")
    protected byte naturezaOperacao;
    @XmlElement(name = "RegimeEspecialTributacao")
    protected Byte regimeEspecialTributacao;
    @XmlElement(name = "OptanteSimplesNacional")
    protected byte optanteSimplesNacional;
    @XmlElement(name = "IncentivadorCultural")
    protected byte incentivadorCultural;
    @XmlElement(name = "Status")
    protected byte status;
    @XmlElement(name = "RpsSubstituido")
    protected TcIdentificacaoRps rpsSubstituido;
    @XmlElement(name = "Servico", required = true)
    protected TcDadosServico servico;
    @XmlElement(name = "Prestador", required = true)
    protected TcIdentificacaoPrestador prestador;
    @XmlElement(name = "Tomador", required = true)
    protected TcDadosTomador tomador;
    @XmlElement(name = "IntermediarioServico")
    protected TcIdentificacaoIntermediarioServico intermediarioServico;
    @XmlElement(name = "ConstrucaoCivil")
    protected TcDadosConstrucaoCivil construcaoCivil;
    @XmlAttribute(name = "Id")
    protected String id;

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
     * Obtém o valor da propriedade dataEmissao.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataEmissao() {
        return dataEmissao;
    }

    /**
     * Define o valor da propriedade dataEmissao.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataEmissao(XMLGregorianCalendar value) {
        this.dataEmissao = value;
    }

    /**
     * Obtém o valor da propriedade naturezaOperacao.
     * 
     */
    public byte getNaturezaOperacao() {
        return naturezaOperacao;
    }

    /**
     * Define o valor da propriedade naturezaOperacao.
     * 
     */
    public void setNaturezaOperacao(byte value) {
        this.naturezaOperacao = value;
    }

    /**
     * Obtém o valor da propriedade regimeEspecialTributacao.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getRegimeEspecialTributacao() {
        return regimeEspecialTributacao;
    }

    /**
     * Define o valor da propriedade regimeEspecialTributacao.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setRegimeEspecialTributacao(Byte value) {
        this.regimeEspecialTributacao = value;
    }

    /**
     * Obtém o valor da propriedade optanteSimplesNacional.
     * 
     */
    public byte getOptanteSimplesNacional() {
        return optanteSimplesNacional;
    }

    /**
     * Define o valor da propriedade optanteSimplesNacional.
     * 
     */
    public void setOptanteSimplesNacional(byte value) {
        this.optanteSimplesNacional = value;
    }

    /**
     * Obtém o valor da propriedade incentivadorCultural.
     * 
     */
    public byte getIncentivadorCultural() {
        return incentivadorCultural;
    }

    /**
     * Define o valor da propriedade incentivadorCultural.
     * 
     */
    public void setIncentivadorCultural(byte value) {
        this.incentivadorCultural = value;
    }

    /**
     * Obtém o valor da propriedade status.
     * 
     */
    public byte getStatus() {
        return status;
    }

    /**
     * Define o valor da propriedade status.
     * 
     */
    public void setStatus(byte value) {
        this.status = value;
    }

    /**
     * Obtém o valor da propriedade rpsSubstituido.
     * 
     * @return
     *     possible object is
     *     {@link TcIdentificacaoRps }
     *     
     */
    public TcIdentificacaoRps getRpsSubstituido() {
        return rpsSubstituido;
    }

    /**
     * Define o valor da propriedade rpsSubstituido.
     * 
     * @param value
     *     allowed object is
     *     {@link TcIdentificacaoRps }
     *     
     */
    public void setRpsSubstituido(TcIdentificacaoRps value) {
        this.rpsSubstituido = value;
    }

    /**
     * Obtém o valor da propriedade servico.
     * 
     * @return
     *     possible object is
     *     {@link TcDadosServico }
     *     
     */
    public TcDadosServico getServico() {
        return servico;
    }

    /**
     * Define o valor da propriedade servico.
     * 
     * @param value
     *     allowed object is
     *     {@link TcDadosServico }
     *     
     */
    public void setServico(TcDadosServico value) {
        this.servico = value;
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
     * Obtém o valor da propriedade tomador.
     * 
     * @return
     *     possible object is
     *     {@link TcDadosTomador }
     *     
     */
    public TcDadosTomador getTomador() {
        return tomador;
    }

    /**
     * Define o valor da propriedade tomador.
     * 
     * @param value
     *     allowed object is
     *     {@link TcDadosTomador }
     *     
     */
    public void setTomador(TcDadosTomador value) {
        this.tomador = value;
    }

    /**
     * Obtém o valor da propriedade intermediarioServico.
     * 
     * @return
     *     possible object is
     *     {@link TcIdentificacaoIntermediarioServico }
     *     
     */
    public TcIdentificacaoIntermediarioServico getIntermediarioServico() {
        return intermediarioServico;
    }

    /**
     * Define o valor da propriedade intermediarioServico.
     * 
     * @param value
     *     allowed object is
     *     {@link TcIdentificacaoIntermediarioServico }
     *     
     */
    public void setIntermediarioServico(TcIdentificacaoIntermediarioServico value) {
        this.intermediarioServico = value;
    }

    /**
     * Obtém o valor da propriedade construcaoCivil.
     * 
     * @return
     *     possible object is
     *     {@link TcDadosConstrucaoCivil }
     *     
     */
    public TcDadosConstrucaoCivil getConstrucaoCivil() {
        return construcaoCivil;
    }

    /**
     * Define o valor da propriedade construcaoCivil.
     * 
     * @param value
     *     allowed object is
     *     {@link TcDadosConstrucaoCivil }
     *     
     */
    public void setConstrucaoCivil(TcDadosConstrucaoCivil value) {
        this.construcaoCivil = value;
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
