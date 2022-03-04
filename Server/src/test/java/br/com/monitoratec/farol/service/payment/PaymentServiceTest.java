package br.com.monitoratec.farol.service.payment;

import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.dtos.payment.PaymentSituationDTO;
import br.com.monitoratec.farol.rest.RestTemplateConfig;
import br.com.monitoratec.farol.service.invoice.InvoiceService;
import br.com.monitoratec.farol.service.mail.MailService;
import br.com.monitoratec.farol.service.plan.PlanService;
import br.com.monitoratec.farol.service.plan.PlanSubscriptionService;
import br.com.monitoratec.farol.service.s3.S3Service;
import br.com.monitoratec.farol.sql.repository.company.CompanyRepository;
import br.com.monitoratec.farol.sql.repository.generalParameterization.GeneralParameterizationRepository;
import br.com.monitoratec.farol.sql.repository.payment.PaymentHistoryRepository;
import br.com.monitoratec.farol.sql.repository.payment.PaymentMonthRepository;
import br.com.monitoratec.farol.sql.repository.payment.UncheckedInvoiceRepository;
import br.com.monitoratec.farol.sql.repository.subscribedPlan.SubscribedPlanRepository;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static br.com.monitoratec.farol.utils.responses.StatusCodes.Error.Payment.PAYMENT_SITUATION_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureWebClient
@AutoConfigureMockRestServiceServer
@Import(RestTemplateConfig.class)
class PaymentServiceTest {
    private PaymentService service;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MockRestServiceServer mockServer;

    @Value("${getnet.url}")
    private String getNetUrl;

    @Value("${getnet.client_id}")
    private String getNetClientId;

    @Value("${getnet.client_secret}")
    private String getNetClientSecret;

    @Value("${getnet.seller_id}")
    private String getNetSellerId;

    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;

    @Autowired
    private SubscribedPlanRepository subscribedPlanRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private InvoiceService invoiceService;

    @Mock
    private MailService mailService;

    @Value("${mail.reset-password-from}")
    private String vidaPlanoMail;

    @Value("${mail.charge-client-subject}")
    private String paymentSlipMailSubject;

    @Value("${mail.payment-slip-body}")
    private String paymentSlipMailBody;

    @Value("{mail.nfse-body}")
    private String nfseBody;

    @Value("{mail.nfse-subject}")
    private String nfseSubject;

    @Value("{mail.nfse-from}")
    private String nfseFrom;

    @Value("${payment-days}")
    private String paymentDays;

    @Autowired
    private PaymentMonthRepository paymentMonthRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PlanSubscriptionService planSubscriptionService;

    @Autowired
    private PlanService planService;

    @Autowired
    private UncheckedInvoiceRepository uncheckedInvoiceRepository;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private GeneralParameterizationRepository generalParameterizationRepository;

    @BeforeEach
    void setup() {
        service = new PaymentService(restTemplate, getNetUrl, getNetClientId, getNetClientSecret, getNetSellerId,
                paymentHistoryRepository, subscribedPlanRepository, paymentMonthRepository, mailService, companyRepository,
                invoiceService, vidaPlanoMail, paymentSlipMailSubject, paymentSlipMailBody, nfseSubject, nfseBody, nfseFrom,
                paymentDays, clientRepository, planService, uncheckedInvoiceRepository, s3Service, generalParameterizationRepository);
    }

    @AfterEach
    void tearDown() {
        mockServer.verify();
    }

    @Test
    void testGetClientPaymentSituationNonExistingClient() {
        final ErrorOnProcessingRequestException exception = assertThrows(ErrorOnProcessingRequestException.class, () -> service.getClientPaymentSituation(50L));
        assertEquals(PAYMENT_SITUATION_NOT_FOUND, exception.getCommonResponse());
    }

    @Test
    void testGetClientPaymentSituationWithoutSubscribedPlan() {
        final ErrorOnProcessingRequestException exception = assertThrows(ErrorOnProcessingRequestException.class, () -> service.getClientPaymentSituation(3L));
        assertEquals(PAYMENT_SITUATION_NOT_FOUND, exception.getCommonResponse());
    }

    @Test
    void testGetClientPaymentSituationWithoutActiveSubscribedPlan() {
        final ErrorOnProcessingRequestException exception = assertThrows(ErrorOnProcessingRequestException.class, () -> service.getClientPaymentSituation(4L));
        assertEquals(PAYMENT_SITUATION_NOT_FOUND, exception.getCommonResponse());
    }

    @Test
    void testGetClientPaymentSituationWithActiveSubscribedPlan() {
        final List<PaymentSituationDTO> dtos = service.getClientPaymentSituation(2L);

        final List<PaymentSituationDTO> expectedDtos = List.of(
                new PaymentSituationDTO(),
                new PaymentSituationDTO()
        );
        assertEquals(expectedDtos, dtos);
    }
}
