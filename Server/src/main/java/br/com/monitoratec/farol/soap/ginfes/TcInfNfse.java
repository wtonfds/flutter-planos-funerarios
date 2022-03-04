
package br.com.monitoratec.farol.soap.ginfes;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java de tcInfNfse complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="tcInfNfse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Numero" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsNumeroNfse"/&gt;
 *         &lt;element name="CodigoVerificacao" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsCodigoVerificacao"/&gt;
 *         &lt;element name="DataEmissao" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="IdentificacaoRps" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcIdentificacaoRps" minOccurs="0"/&gt;
 *         &lt;element name="DataEmissaoRps" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="NaturezaOperacao" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsNaturezaOperacao"/&gt;
 *         &lt;element name="RegimeEspecialTributacao" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsRegimeEspecialTributacao" minOccurs="0"/&gt;
 *         &lt;element name="OptanteSimplesNacional" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsSimNao"/&gt;
 *         &lt;element name="IncentivadorCultural" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsSimNao"/&gt;
 *         &lt;element name="Competencia" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="NfseSubstituida" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsNumeroNfse" minOccurs="0"/&gt;
 *         &lt;element name="OutrasInformacoes" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsOutrasInformacoes" minOccurs="0"/&gt;
 *         &lt;element name="Servico" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcDadosServico"/&gt;
 *         &lt;element name="ValorCredito" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsValor" minOccurs="0"/&gt;
 *         &lt;element name="PrestadorServico" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcDadosPrestador"/&gt;
 *         &lt;element name="TomadorServico" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcDadosTomador"/&gt;
 *         &lt;element name="IntermediarioServico" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcIdentificacaoIntermediarioServico" minOccurs="0"/&gt;
 *         &lt;element name="OrgaoGerador" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcIdentificacaoOrgaoGerador"/&gt;
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
@XmlType(name = "tcInfNfse", propOrder = {
    "numero",
    "codigoVerificacao",
    "dataEmissao",
    "identificacaoRps",
    "dataEmissaoRps",
    "naturezaOperacao",
    "regimeEspecialTributacao",
    "optanteSimplesNacional",
    "incentivadorCultural",
    "competencia",
    "nfseSubstituida",
    "outrasInformacoes",
    "servico",
    "valorCredito",
    "prestadorServico",
    "tomadorServico",
    "intermediarioServico",
    "orgaoGerador",
    "construcaoCivil"
})
public class TcInfNfse {

    @XmlElement(name = "Numero", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger numero;
    @XmlElement(name = "CodigoVerificacao", required = true)
    protected String codigoVerificacao;
    @XmlElement(name = "DataEmissao", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataEmissao;
    @XmlElement(name = "IdentificacaoRps")
    protected TcIdentificacaoRps identificacaoRps;
    @XmlElement(name = "DataEmissaoRps")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataEmissaoRps;
    @XmlElement(name = "NaturezaOperacao")
    protected byte naturezaOperacao;
    @XmlElement(name = "RegimeEspecialTributacao")
    protected Byte regimeEspecialTributacao;
    @XmlElement(name = "OptanteSimplesNacional")
    protected byte optanteSimplesNacional;
    @XmlElement(name = "IncentivadorCultural")
    protected byte incentivadorCultural;
    @XmlElement(name = "Competencia", required = true)
    protected String competencia;
    @XmlElement(name = "NfseSubstituida")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger nfseSubstituida;
    @XmlElement(name = "OutrasInformacoes")
    protected String outrasInformacoes;
    @XmlElement(name = "Servico", required = true)
    protected TcDadosServico servico;
    @XmlElement(name = "ValorCredito")
    protected BigDecimal valorCredito;
    @XmlElement(name = "PrestadorServico", required = true)
    protected TcDadosPrestador prestadorServico;
    @XmlElement(name = "TomadorServico", required = true)
    protected TcDadosTomador tomadorServico;
    @XmlElement(name = "IntermediarioServico")
    protected TcIdentificacaoIntermediarioServico intermediarioServico;
    @XmlElement(name = "OrgaoGerador", required = true)
    protected TcIdentificacaoOrgaoGerador orgaoGerador;
    @XmlElement(name = "ConstrucaoCivil")
    protected TcDadosConstrucaoCivil construcaoCivil;
    @XmlAttribute(name = "Id")
    protected String id;

    /**
     * Obtém o valor da propriedade numero.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumero() {
        return numero;
    }

    /**
     * Define o valor da propriedade numero.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumero(BigInteger value) {
        this.numero = value;
    }

    /**
     * Obtém o valor da propriedade codigoVerificacao.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoVerificacao() {
        return codigoVerificacao;
    }

    /**
     * Define o valor da propriedade codigoVerificacao.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoVerificacao(String value) {
        this.codigoVerificacao = value;
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
     * Obtém o valor da propriedade dataEmissaoRps.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataEmissaoRps() {
        return dataEmissaoRps;
    }

    /**
     * Define o valor da propriedade dataEmissaoRps.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataEmissaoRps(XMLGregorianCalendar value) {
        this.dataEmissaoRps = value;
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
     * Obtém o valor da propriedade competencia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompetencia() {
        return competencia;
    }

    /**
     * Define o valor da propriedade competencia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompetencia(String value) {
        this.competencia = value;
    }

    /**
     * Obtém o valor da propriedade nfseSubstituida.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNfseSubstituida() {
        return nfseSubstituida;
    }

    /**
     * Define o valor da propriedade nfseSubstituida.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNfseSubstituida(BigInteger value) {
        this.nfseSubstituida = value;
    }

    /**
     * Obtém o valor da propriedade outrasInformacoes.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutrasInformacoes() {
        return outrasInformacoes;
    }

    /**
     * Define o valor da propriedade outrasInformacoes.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutrasInformacoes(String value) {
        this.outrasInformacoes = value;
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
     * Obtém o valor da propriedade valorCredito.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorCredito() {
        return valorCredito;
    }

    /**
     * Define o valor da propriedade valorCredito.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorCredito(BigDecimal value) {
        this.valorCredito = value;
    }

    /**
     * Obtém o valor da propriedade prestadorServico.
     * 
     * @return
     *     possible object is
     *     {@link TcDadosPrestador }
     *     
     */
    public TcDadosPrestador getPrestadorServico() {
        return prestadorServico;
    }

    /**
     * Define o valor da propriedade prestadorServico.
     * 
     * @param value
     *     allowed object is
     *     {@link TcDadosPrestador }
     *     
     */
    public void setPrestadorServico(TcDadosPrestador value) {
        this.prestadorServico = value;
    }

    /**
     * Obtém o valor da propriedade tomadorServico.
     * 
     * @return
     *     possible object is
     *     {@link TcDadosTomador }
     *     
     */
    public TcDadosTomador getTomadorServico() {
        return tomadorServico;
    }

    /**
     * Define o valor da propriedade tomadorServico.
     * 
     * @param value
     *     allowed object is
     *     {@link TcDadosTomador }
     *     
     */
    public void setTomadorServico(TcDadosTomador value) {
        this.tomadorServico = value;
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
     * Obtém o valor da propriedade orgaoGerador.
     * 
     * @return
     *     possible object is
     *     {@link TcIdentificacaoOrgaoGerador }
     *     
     */
    public TcIdentificacaoOrgaoGerador getOrgaoGerador() {
        return orgaoGerador;
    }

    /**
     * Define o valor da propriedade orgaoGerador.
     * 
     * @param value
     *     allowed object is
     *     {@link TcIdentificacaoOrgaoGerador }
     *     
     */
    public void setOrgaoGerador(TcIdentificacaoOrgaoGerador value) {
        this.orgaoGerador = value;
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
