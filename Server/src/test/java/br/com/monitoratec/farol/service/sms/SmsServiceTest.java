package br.com.monitoratec.farol.service.sms;

import br.com.monitoratec.farol.rest.RestTemplateConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestToUriTemplate;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest
@Import(RestTemplateConfig.class)
class SmsServiceTest {
    private SmsService service;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MockRestServiceServer mockServer;

    @Value("${sms.url}")
    private String smsUrl;

    @Value("${sms.company}")
    private String company;

    @Value("${sms.user}")
    private String user;

    @Value("${sms.password}")
    private String password;

    @Value("${sms.cost_center}")
    private String costCenter;

    @BeforeEach
    void setup() {
        service = new SmsService(restTemplate, smsUrl, company, user, password, costCenter);
    }

    @AfterEach
    void tearDown() {
        mockServer.verify();
    }

    @Test
    void testSend() {
        final String number = "16999999999";
        final String message = "Senha temporária: A!24(Fa#xV1h";

        final String expectedUrl = smsUrl + "?modo=envio&empresa={company}&usuario={user}&senha={password}&telefone={to}&mensagem={message}&centro_custo={costCenter}%2055";

        mockServer.expect(requestToUriTemplate(expectedUrl, company, user, password, number, message, costCenter))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"STATUS\":1,\"HASH\":\"dmluaWNpdXMxMzc5MzYxNTU0Nzk4NQ==\"}", MediaType.APPLICATION_JSON));

        service.send(number, message);
    }
}
