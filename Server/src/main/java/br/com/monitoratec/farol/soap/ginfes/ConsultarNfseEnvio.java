
package br.com.monitoratec.farol.soap.ginfes;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="Prestador" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcIdentificacaoPrestador"/&gt;
 *         &lt;element name="NumeroNfse" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsNumeroNfse" minOccurs="0"/&gt;
 *         &lt;element name="PeriodoEmissao" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="DataInicial" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *                   &lt;element name="DataFinal" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Tomador" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcIdentificacaoTomador" minOccurs="0"/&gt;
 *         &lt;element name="IntermediarioServico" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcIdentificacaoIntermediarioServico" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Signature" minOccurs="0"/&gt;
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
@XmlType(name = "", propOrder = {
    "prestador",
    "numeroNfse",
    "periodoEmissao",
    "tomador",
    "intermediarioServico",
    "signature"
})
@XmlRootElement(name = "ConsultarNfseEnvio", namespace = "http://www.ginfes.com.br/servico_consultar_nfse_envio_v03.xsd")
public class ConsultarNfseEnvio {

    @XmlElement(name = "Prestador", namespace = "http://www.ginfes.com.br/servico_consultar_nfse_envio_v03.xsd", required = true)
    protected TcIdentificacaoPrestador prestador;
    @XmlElement(name = "NumeroNfse", namespace = "http://www.ginfes.com.br/servico_consultar_nfse_envio_v03.xsd")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger numeroNfse;
    @XmlElement(name = "PeriodoEmissao", namespace = "http://www.ginfes.com.br/servico_consultar_nfse_envio_v03.xsd")
    protected ConsultarNfseEnvio.PeriodoEmissao periodoEmissao;
    @XmlElement(name = "Tomador", namespace = "http://www.ginfes.com.br/servico_consultar_nfse_envio_v03.xsd")
    protected TcIdentificacaoTomador tomador;
    @XmlElement(name = "IntermediarioServico", namespace = "http://www.ginfes.com.br/servico_consultar_nfse_envio_v03.xsd")
    protected TcIdentificacaoIntermediarioServico intermediarioServico;
    @XmlElement(name = "Signature", namespace = "http://www.w3.org/2000/09/xmldsig#")
    protected SignatureType signature;
    @XmlAttribute(name = "Id")
    protected String id;

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
     * Obtém o valor da propriedade numeroNfse.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumeroNfse() {
        return numeroNfse;
    }

    /**
     * Define o valor da propriedade numeroNfse.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumeroNfse(BigInteger value) {
        this.numeroNfse = value;
    }

    /**
     * Obtém o valor da propriedade periodoEmissao.
     * 
     * @return
     *     possible object is
     *     {@link ConsultarNfseEnvio.PeriodoEmissao }
     *     
     */
    public ConsultarNfseEnvio.PeriodoEmissao getPeriodoEmissao() {
        return periodoEmissao;
    }

    /**
     * Define o valor da propriedade periodoEmissao.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsultarNfseEnvio.PeriodoEmissao }
     *     
     */
    public void setPeriodoEmissao(ConsultarNfseEnvio.PeriodoEmissao value) {
        this.periodoEmissao = value;
    }

    /**
     * Obtém o valor da propriedade tomador.
     * 
     * @return
     *     possible object is
     *     {@link TcIdentificacaoTomador }
     *     
     */
    public TcIdentificacaoTomador getTomador() {
        return tomador;
    }

    /**
     * Define o valor da propriedade tomador.
     * 
     * @param value
     *     allowed object is
     *     {@link TcIdentificacaoTomador }
     *     
     */
    public void setTomador(TcIdentificacaoTomador value) {
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
     *         &lt;element name="DataInicial" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
     *         &lt;element name="DataFinal" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
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
        "dataInicial",
        "dataFinal"
    })
    public static class PeriodoEmissao {

        @XmlElement(name = "DataInicial", namespace = "http://www.ginfes.com.br/servico_consultar_nfse_envio_v03.xsd", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar dataInicial;
        @XmlElement(name = "DataFinal", namespace = "http://www.ginfes.com.br/servico_consultar_nfse_envio_v03.xsd", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar dataFinal;

        /**
         * Obtém o valor da propriedade dataInicial.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDataInicial() {
            return dataInicial;
        }

        /**
         * Define o valor da propriedade dataInicial.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDataInicial(XMLGregorianCalendar value) {
            this.dataInicial = value;
        }

        /**
         * Obtém o valor da propriedade dataFinal.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDataFinal() {
            return dataFinal;
        }

        /**
         * Define o valor da propriedade dataFinal.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDataFinal(XMLGregorianCalendar value) {
            this.dataFinal = value;
        }

    }

}
