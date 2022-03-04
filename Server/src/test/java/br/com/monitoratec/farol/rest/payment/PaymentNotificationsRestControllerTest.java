package br.com.monitoratec.farol.rest.payment;

import br.com.monitoratec.farol.rest.payment.dto.PaymentSlipFinishedParameters;
import br.com.monitoratec.farol.rest.payment.dto.PaymentSlipGenerationParameters;
import br.com.monitoratec.farol.rest.payment.dto.RecurrenceUpdatedParameters;
import br.com.monitoratec.farol.service.payment.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = PaymentNotificationsRestController.class)
class PaymentNotificationsRestControllerTest {
    private static final String ENDPOINT = "/payment-notifications";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService service;

    @Test
    void testPaymentSlipGenerated() throws Exception {
        final String url = ENDPOINT + "/slip?payment_type=boleto" +
                "&order_id=PEDIDO-123" +
                "&payment_id=5d9c9a7e15e3d700105dc490" +
                "&id=a760c304-0594-4579-afe2-0c6ecc2d3745" +
                "&amount=1" +
                "&status=PENDING" +
                "&bank=0033" +
                "&our_number=0099887766578" +
                "&typeful_line=03399.5891093000.99887776657.801015180580000000001" +
                "&issue_date=2019-10-08T05:05:00.000Z";

        mockMvc.perform(get(url));

        final PaymentSlipGenerationParameters expectedParameters = new PaymentSlipGenerationParameters("PEDIDO-123",
                "5d9c9a7e15e3d700105dc490", "a760c304-0594-4579-afe2-0c6ecc2d3745", 1, "PENDING",
                "0033", "0099887766578", "03399.5891093000.99887776657.801015180580000000001",
                LocalDateTime.of(2019, 10, 8, 5, 5));

        verify(service).paymentSlipGenerated(expectedParameters);
        verifyNoMoreInteractions(service);
    }

    @Test
    void testPaymentSlipFinished() throws Exception {
        final String url = ENDPOINT + "/slip?id=a760c304-0594-4579-afe2-0c6ecc2d3745" +
                "&payment_date=08/10/2019" +
                "&amount=1" +
                "&status=PAID";

        mockMvc.perform(get(url));

        final PaymentSlipFinishedParameters expectedParameters = new PaymentSlipFinishedParameters("a760c304-0594-4579-afe2-0c6ecc2d3745",
                LocalDate.of(2019, 10, 8), 1, "PAID");

        verify(service).paymentSlipFinished(expectedParameters);
        verifyNoMoreInteractions(service);
    }

    @Test
    void testRecurrenceUpdated() throws Exception {
        final String url = ENDPOINT + "/recurrence?payment_type=credit" +
                "&order_id=teste-notificacao-1" +
                "&payment_id=5d9c9a7e15e3d700105dc490" +
                "&amount=10000" +
                "&status=AUTHORIZED" +
                "&authorization_timestamp=2019-10-11T13:43:47Z" +
                "&acquirer_transaction_id=000100912401" +
                "&customer_id=customer123" +
                "&subscription_id=5d740ea0-b7d1-42f5-ad64-5a5521e12er9" +
                "&plan_id=51995e24-b1ae-4826-8e15-2a568a87abdd" +
                "&charge_id=52bc81b2-f079-4f7b-b63a-f653ff29d794" +
                "&number_installments=1";

        mockMvc.perform(get(url));

        final RecurrenceUpdatedParameters expectedParameters = new RecurrenceUpdatedParameters("teste-notificacao-1",
                "5d9c9a7e15e3d700105dc490", 10000, "AUTHORIZED", LocalDateTime.of(2019, 10, 11, 13, 43, 47),
                "000100912401", "customer123", "5d740ea0-b7d1-42f5-ad64-5a5521e12er9",
                "51995e24-b1ae-4826-8e15-2a568a87abdd", "52bc81b2-f079-4f7b-b63a-f653ff29d794", 1);

        verify(service).recurrenceUpdated(expectedParameters);
        verifyNoMoreInteractions(service);
    }
}
