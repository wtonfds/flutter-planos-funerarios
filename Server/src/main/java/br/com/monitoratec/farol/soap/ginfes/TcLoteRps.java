
package br.com.monitoratec.farol.soap.ginfes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de tcLoteRps complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="tcLoteRps"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NumeroLote" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsNumeroLote"/&gt;
 *         &lt;element name="Cnpj" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsCnpj"/&gt;
 *         &lt;element name="InscricaoMunicipal" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsInscricaoMunicipal"/&gt;
 *         &lt;element name="QuantidadeRps" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsQuantidadeRps"/&gt;
 *         &lt;element name="ListaRps"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Rps" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcRps" maxOccurs="unbounded"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
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
@XmlType(name = "tcLoteRps", propOrder = {
    "numeroLote",
    "cnpj",
    "inscricaoMunicipal",
    "quantidadeRps",
    "listaRps"
})
public class TcLoteRps {

    @XmlElement(name = "NumeroLote", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger numeroLote;
    @XmlElement(name = "Cnpj", required = true)
    protected String cnpj;
    @XmlElement(name = "InscricaoMunicipal", required = true)
    protected String inscricaoMunicipal;
    @XmlElement(name = "QuantidadeRps")
    protected int quantidadeRps;
    @XmlElement(name = "ListaRps", required = true)
    protected TcLoteRps.ListaRps listaRps;
    @XmlAttribute(name = "Id")
    protected String id;

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
     * Obtém o valor da propriedade cnpj.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * Define o valor da propriedade cnpj.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCnpj(String value) {
        this.cnpj = value;
    }

    /**
     * Obtém o valor da propriedade inscricaoMunicipal.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInscricaoMunicipal() {
        return inscricaoMunicipal;
    }

    /**
     * Define o valor da propriedade inscricaoMunicipal.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInscricaoMunicipal(String value) {
        this.inscricaoMunicipal = value;
    }

    /**
     * Obtém o valor da propriedade quantidadeRps.
     * 
     */
    public int getQuantidadeRps() {
        return quantidadeRps;
    }

    /**
     * Define o valor da propriedade quantidadeRps.
     * 
     */
    public void setQuantidadeRps(int value) {
        this.quantidadeRps = value;
    }

    /**
     * Obtém o valor da propriedade listaRps.
     * 
     * @return
     *     possible object is
     *     {@link TcLoteRps.ListaRps }
     *     
     */
    public TcLoteRps.ListaRps getListaRps() {
        return listaRps;
    }

    /**
     * Define o valor da propriedade listaRps.
     * 
     * @param value
     *     allowed object is
     *     {@link TcLoteRps.ListaRps }
     *     
     */
    public void setListaRps(TcLoteRps.ListaRps value) {
        this.listaRps = value;
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
     *         &lt;element name="Rps" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcRps" maxOccurs="unbounded"/&gt;
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
        "rps"
    })
    public static class ListaRps {

        @XmlElement(name = "Rps", required = true)
        protected List<TcRps> rps;

        /**
         * Gets the value of the rps property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the rps property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRps().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TcRps }
         * 
         * 
         */
        public List<TcRps> getRps() {
            if (rps == null) {
                rps = new ArrayList<TcRps>();
            }
            return this.rps;
        }

    }

}
