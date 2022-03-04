package br.com.monitoratec.farol.service.invoice;

import br.com.monitoratec.farol.service.invoice.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

//Note: remove or disable this test once the integration works (to avoid calling the environments every build)
@Disabled("Should be used only to check integration with Ginfes")
class InvoiceServiceTest {
    private InvoiceService service;

    @BeforeEach
    void setup() throws FileNotFoundException {
        final String ginfesUrl = "http://homologacao.ginfes.com.br";

        final URL wsdlUrl = getClass().getResource("/wsdl/ginfes-homolog.wsdl");
        final FileSystemResource wsdlLocation = new FileSystemResource(ResourceUtils.getFile(wsdlUrl));

        final URL url = getClass().getResource("/certificates/farol.pfx");
        final FileSystemResource certificateFile = new FileSystemResource(ResourceUtils.getFile(url));

        final CertificateManager certificateManager = new CertificateManager(certificateFile, "1234");

        final XmlSigner xmlSigner = new XmlSigner(certificateFile, "1234");

        final InvoiceServiceProvider provider = new InvoiceServiceProvider(ginfesUrl, wsdlLocation, certificateManager);
        service = new InvoiceService(provider, xmlSigner);
    }

    @Test
    void testPostInvoice() {
        final Values values = new Values.Builder()
                .setServicesValue(new BigDecimal("1234.56"))
                .setAliquot(new BigDecimal("0.04"))
                .build();

        final ServiceData serviceData = new ServiceData.Builder()
                .setValues(values)
                .setServiceListItemCode("25.03") // todo check correct code here http://sped.rfb.gov.br/pagina/show/1601 | 25.03 – Planos ou convênio funerários.
                .setDiscrimination("Plano de assistencia funeral") // Discriminação do conteúdo da NFS-e
                .setCityCode(3543402) // Código de identificação do município conforme tabela do IBGE
                .setMunicipalTaxCode("25.03 / 00250300")
                .build();

        LocalDateTime now = LocalDateTime.now().withNano(0);
        final RpsInfo info = new RpsInfo.Builder()
                .setRpsIdentification(new RpsIdentification(BigInteger.ONE, "00001", RpsType.RPS))
                .setEmissionDate(now)
                .setOperationNature(RpsOperationNature.TAXATION_INSIDE_MUNICIPALITY)
                .setStatus(RpsStatus.NORMAL)
                .setServiceData(serviceData)
                .setProviderCnpj("71661599000152")
                .setProviderMunicipalRegistry("8136501")
                .setTakerData(new TakerData(null, null, null, null))
                .build();

        final List<RpsInfo> rpsInfoList = List.of(info);
        final PostInvoiceParameters parameters = new PostInvoiceParameters(new BigInteger("123456789"), "71661599000152", "8136501", rpsInfoList);

        service.postInvoice(parameters);
    }

    @Test
    void testGetInvoice() {
        final GetInvoiceStatusParameters parameters = new GetInvoiceStatusParameters.Builder()
                .setProviderCnpj("71661599000152")
                .setProviderMunicipalRegistry("8136501")
                .setProtocol("10471428")
                .build();

        service.getInvoiceStatus(parameters);
    }

    // This method returns the complete information about an NFS-e by protocol number. Used for testing status response
    // To test this method, use System.out.println inside getInvoice method to see the result.
    @Test
    void testRealGetInvoice() {
        final GetInvoiceParameters parameters = new GetInvoiceParameters.Builder()
                .setProviderCnpj("71661599000152")
                .setProviderMunicipalRegistry("8136501")
                .setProtocol("10471681")
                .build();

        service.getInvoice(parameters);
    }
}
