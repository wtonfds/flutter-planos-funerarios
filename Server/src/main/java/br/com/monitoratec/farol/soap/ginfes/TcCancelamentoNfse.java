
package br.com.monitoratec.farol.soap.ginfes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de tcCancelamentoNfse complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="tcCancelamentoNfse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Confirmacao" type="{http://www.ginfes.com.br/tipos_v03.xsd}tcConfirmacaoCancelamento"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Signature" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tcCancelamentoNfse", propOrder = {
    "confirmacao",
    "signature"
})
public class TcCancelamentoNfse {

    @XmlElement(name = "Confirmacao", required = true)
    protected TcConfirmacaoCancelamento confirmacao;
    @XmlElement(name = "Signature", namespace = "http://www.w3.org/2000/09/xmldsig#")
    protected SignatureType signature;

    /**
     * Obtém o valor da propriedade confirmacao.
     * 
     * @return
     *     possible object is
     *     {@link TcConfirmacaoCancelamento }
     *     
     */
    public TcConfirmacaoCancelamento getConfirmacao() {
        return confirmacao;
    }

    /**
     * Define o valor da propriedade confirmacao.
     * 
     * @param value
     *     allowed object is
     *     {@link TcConfirmacaoCancelamento }
     *     
     */
    public void setConfirmacao(TcConfirmacaoCancelamento value) {
        this.confirmacao = value;
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

}
