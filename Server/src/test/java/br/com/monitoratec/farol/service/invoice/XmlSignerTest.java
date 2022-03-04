package br.com.monitoratec.farol.service.invoice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.net.URL;

@Disabled("Should be used only to check integration with Ginfes")
class XmlSignerTest {
    private XmlSigner signer;

    @BeforeEach
    void setup() throws FileNotFoundException {
        final URL url = getClass().getResource("/certificates/farol.pfx");
        final FileSystemResource certificateFile = new FileSystemResource(ResourceUtils.getFile(url));

        signer = new XmlSigner(certificateFile, "1234");
    }

    @Test
    void testSignWholeDocument() {
        final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<ns5:ConsultarNfseEnvio xmlns:ns2=\"http://www.ginfes.com.br/tipos_v03.xsd\" xmlns:ns4=\"http://www.ginfes.com.br/servico_consultar_situacao_lote_rps_envio_v03.xsd\" xmlns:ns3=\"http://www.ginfes.com.br/servico_consultar_lote_rps_resposta_v03.xsd\" xmlns:ns6=\"http://www.ginfes.com.br/servico_consultar_situacao_lote_rps_resposta_v03.xsd\" xmlns:ns5=\"http://www.ginfes.com.br/servico_consultar_nfse_envio_v03.xsd\" xmlns:ns8=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns7=\"http://www.ginfes.com.br/servico_consultar_nfse_resposta_v03.xsd\" xmlns:ns13=\"http://www.ginfes.com.br/servico_cancelar_nfse_envio_v03.xsd\" xmlns:ns9=\"http://www.ginfes.com.br/cabecalho_v03.xsd\" xmlns:ns12=\"http://www.ginfes.com.br/servico_consultar_lote_rps_envio_v03.xsd\" xmlns:ns11=\"http://www.ginfes.com.br/servico_enviar_lote_rps_envio_v03.xsd\" xmlns:ns10=\"http://www.ginfes.com.br/servico_cancelar_nfse_resposta_v03.xsd\" xmlns:ns16=\"http://www.ginfes.com.br/servico_consultar_nfse_rps_envio_v03.xsd\" xmlns:ns15=\"http://www.ginfes.com.br/servico_enviar_lote_rps_resposta_v03.xsd\" xmlns:ns14=\"http://www.ginfes.com.br/servico_consultar_nfse_rps_resposta_v03.xsd\">" +
                "<ns5:Prestador><ns2:Cnpj>86119859000130</ns2:Cnpj><ns2:InscricaoMunicipal>453.341.056.063</ns2:InscricaoMunicipal></ns5:Prestador><ns5:NumeroNfse>164321234123342</ns5:NumeroNfse>" +
                "<ns5:PeriodoEmissao><ns5:DataInicial>2019-01-01</ns5:DataInicial><ns5:DataFinal>2019-12-31</ns5:DataFinal></ns5:PeriodoEmissao>" +
                "<ns5:Tomador><ns2:CpfCnpj><ns2:Cpf>46499225002</ns2:Cpf></ns2:CpfCnpj><ns2:InscricaoMunicipal>999.999.999.999</ns2:InscricaoMunicipal></ns5:Tomador>" +
                "<ns5:IntermediarioServico><ns2:RazaoSocial>111.222.333.444</ns2:RazaoSocial><ns2:CpfCnpj><ns2:Cnpj>77486574000146</ns2:Cnpj></ns2:CpfCnpj></ns5:IntermediarioServico>" +
                "</ns5:ConsultarNfseEnvio>";

        final String signed = signer.signXml(xml, null, null);
        System.out.println(signed);
    }

    @Test
    void testSignWholeDocumentWithTag() {
        final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<ns5:ConsultarNfseEnvio xmlns:ns2=\"http://www.ginfes.com.br/tipos_v03.xsd\" xmlns:ns4=\"http://www.ginfes.com.br/servico_consultar_situacao_lote_rps_envio_v03.xsd\" xmlns:ns3=\"http://www.ginfes.com.br/servico_consultar_lote_rps_resposta_v03.xsd\" xmlns:ns6=\"http://www.ginfes.com.br/servico_consultar_situacao_lote_rps_resposta_v03.xsd\" xmlns:ns5=\"http://www.ginfes.com.br/servico_consultar_nfse_envio_v03.xsd\" xmlns:ns8=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns7=\"http://www.ginfes.com.br/servico_consultar_nfse_resposta_v03.xsd\" xmlns:ns13=\"http://www.ginfes.com.br/servico_cancelar_nfse_envio_v03.xsd\" xmlns:ns9=\"http://www.ginfes.com.br/cabecalho_v03.xsd\" xmlns:ns12=\"http://www.ginfes.com.br/servico_consultar_lote_rps_envio_v03.xsd\" xmlns:ns11=\"http://www.ginfes.com.br/servico_enviar_lote_rps_envio_v03.xsd\" xmlns:ns10=\"http://www.ginfes.com.br/servico_cancelar_nfse_resposta_v03.xsd\" xmlns:ns16=\"http://www.ginfes.com.br/servico_consultar_nfse_rps_envio_v03.xsd\" xmlns:ns15=\"http://www.ginfes.com.br/servico_enviar_lote_rps_resposta_v03.xsd\" xmlns:ns14=\"http://www.ginfes.com.br/servico_consultar_nfse_rps_resposta_v03.xsd\">" +
                "<ns5:Prestador><ns2:Cnpj>86119859000130</ns2:Cnpj><ns2:InscricaoMunicipal>453.341.056.063</ns2:InscricaoMunicipal></ns5:Prestador><ns5:NumeroNfse Id=\"NFe164321234123342\">164321234123342</ns5:NumeroNfse>" +
                "<ns5:PeriodoEmissao><ns5:DataInicial>2019-01-01</ns5:DataInicial><ns5:DataFinal>2019-12-31</ns5:DataFinal></ns5:PeriodoEmissao>" +
                "<ns5:Tomador><ns2:CpfCnpj><ns2:Cpf>46499225002</ns2:Cpf></ns2:CpfCnpj><ns2:InscricaoMunicipal>999.999.999.999</ns2:InscricaoMunicipal></ns5:Tomador>" +
                "<ns5:IntermediarioServico><ns2:RazaoSocial>111.222.333.444</ns2:RazaoSocial><ns2:CpfCnpj><ns2:Cnpj>77486574000146</ns2:Cnpj></ns2:CpfCnpj></ns5:IntermediarioServico>" +
                "</ns5:ConsultarNfseEnvio>";

        final String signed = signer.signXml(xml, null, "NumeroNfse");
        System.out.println(signed);
    }

    @Test
    void testSignSpecificTag() {
        final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<Root>" +
                "<Example><Element1 Id=\"element-1\"/><AnotherElement/></Example>" +
                "<Example><Element1 Id=\"element-2\"/><AnotherElement/></Example>" +
                "</Root>";

        final String signed = signer.signXml(xml, "Example", "Element1");
        System.out.println(signed);
    }

    @Test
    void testSignSpecificTagAndWholeDocument() {
        final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<Root>" +
                "<RootElement Id=\"root-element\">" +
                "<Example><Element1 Id=\"element-1\"/><AnotherElement/></Example>" +
                "<Example><Element1 Id=\"element-2\"/><AnotherElement/></Example>" +
                "</RootElement>" +
                "</Root>";

        String signed = signer.signXml(xml, "Example", "Element1");
        signed = signer.signXml(signed, null, "RootElement");
        System.out.println(signed);
    }
}
