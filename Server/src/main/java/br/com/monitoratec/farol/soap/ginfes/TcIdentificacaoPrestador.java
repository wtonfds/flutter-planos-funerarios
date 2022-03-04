
package br.com.monitoratec.farol.soap.ginfes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de tcIdentificacaoPrestador complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="tcIdentificacaoPrestador"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Cnpj" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsCnpj"/&gt;
 *         &lt;element name="InscricaoMunicipal" type="{http://www.ginfes.com.br/tipos_v03.xsd}tsInscricaoMunicipal" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tcIdentificacaoPrestador", propOrder = {
    "cnpj",
    "inscricaoMunicipal"
})
public class TcIdentificacaoPrestador {

    @XmlElement(name = "Cnpj", required = true)
    protected String cnpj;
    @XmlElement(name = "InscricaoMunicipal")
    protected String inscricaoMunicipal;

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

}
