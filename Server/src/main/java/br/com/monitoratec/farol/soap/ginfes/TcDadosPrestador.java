
package br.com.monitoratec.farol.soap.ginfes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de tcDadosPrestador complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="tcDadosPrestador"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IdentificacaoPrestador" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcIdentificacaoPrestador"/&gt;
 *         &lt;element name="RazaoSocial" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsRazaoSocial"/&gt;
 *         &lt;element name="NomeFantasia" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsNomeFantasia" minOccurs="0"/&gt;
 *         &lt;element name="Endereco" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcEndereco"/&gt;
 *         &lt;element name="Contato" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcContato" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tcDadosPrestador", propOrder = {
    "identificacaoPrestador",
    "razaoSocial",
    "nomeFantasia",
    "endereco",
    "contato"
})
public class TcDadosPrestador {

    @XmlElement(name = "IdentificacaoPrestador", required = true)
    protected TcIdentificacaoPrestador identificacaoPrestador;
    @XmlElement(name = "RazaoSocial", required = true)
    protected String razaoSocial;
    @XmlElement(name = "NomeFantasia")
    protected String nomeFantasia;
    @XmlElement(name = "Endereco", required = true)
    protected TcEndereco endereco;
    @XmlElement(name = "Contato")
    protected TcContato contato;

    /**
     * Obtém o valor da propriedade identificacaoPrestador.
     * 
     * @return
     *     possible object is
     *     {@link TcIdentificacaoPrestador }
     *     
     */
    public TcIdentificacaoPrestador getIdentificacaoPrestador() {
        return identificacaoPrestador;
    }

    /**
     * Define o valor da propriedade identificacaoPrestador.
     * 
     * @param value
     *     allowed object is
     *     {@link TcIdentificacaoPrestador }
     *     
     */
    public void setIdentificacaoPrestador(TcIdentificacaoPrestador value) {
        this.identificacaoPrestador = value;
    }

    /**
     * Obtém o valor da propriedade razaoSocial.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRazaoSocial() {
        return razaoSocial;
    }

    /**
     * Define o valor da propriedade razaoSocial.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRazaoSocial(String value) {
        this.razaoSocial = value;
    }

    /**
     * Obtém o valor da propriedade nomeFantasia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeFantasia() {
        return nomeFantasia;
    }

    /**
     * Define o valor da propriedade nomeFantasia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeFantasia(String value) {
        this.nomeFantasia = value;
    }

    /**
     * Obtém o valor da propriedade endereco.
     * 
     * @return
     *     possible object is
     *     {@link TcEndereco }
     *     
     */
    public TcEndereco getEndereco() {
        return endereco;
    }

    /**
     * Define o valor da propriedade endereco.
     * 
     * @param value
     *     allowed object is
     *     {@link TcEndereco }
     *     
     */
    public void setEndereco(TcEndereco value) {
        this.endereco = value;
    }

    /**
     * Obtém o valor da propriedade contato.
     * 
     * @return
     *     possible object is
     *     {@link TcContato }
     *     
     */
    public TcContato getContato() {
        return contato;
    }

    /**
     * Define o valor da propriedade contato.
     * 
     * @param value
     *     allowed object is
     *     {@link TcContato }
     *     
     */
    public void setContato(TcContato value) {
        this.contato = value;
    }

}
