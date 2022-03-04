package br.com.monitoratec.farol.service.push;

import br.com.monitoratec.farol.rest.RestTemplateConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest
@Import(RestTemplateConfig.class)
class PushServiceTest {
    private PushService service;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MockRestServiceServer mockServer;

    @Value("${push.onesignal-url}")
    private String url;

    @Value("${push.onesignal-app-id}")
    private String oneSignalAppId;

    @Value("${push.onesignal-api-key}")
    private String oneSignalApiKey;

    @BeforeEach
    void setup() {
        service = new PushService(restTemplate, url, oneSignalAppId, oneSignalApiKey);
    }

    @AfterEach
    void tearDown() {
        mockServer.verify();
    }

    @Test
    void testSendWithSuccessEmptyPlayerIds() {
        final String message = "Example message";

        final String expectedRequest = "{" +
                "\"app_id\":\"" + oneSignalAppId + "\"," +
                "\"contents\":{\"en\":\"" + message + "\"}," +
                "\"included_segments\":[\"Active Users\"]" +
                "}";

        final String expectedResponse = "{" +
                "\"id\":\"" + UUID.randomUUID().toString() + "\"," +
                "\"recipients\":1," +
                "\"external_id\":null" +
                "}";

        mockServer.expect(requestTo(url))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header(HttpHeaders.AUTHORIZATION, "Basic " + oneSignalApiKey))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedRequest))
                .andRespond(withSuccess(expectedResponse, MediaType.APPLICATION_JSON));

        final String response = service.sendNotificationOneSignal(List.of(), message);
        assertEquals(expectedResponse, response);
    }

    @Test
    void testSendWithSuccessNonEmptyPlayerIds() {
        final String playerId1 = UUID.randomUUID().toString();
        final String playerId2 = UUID.randomUUID().toString();

        final String message = "Example message";

        final String expectedPayload = "{" +
                "\"app_id\":\"" + oneSignalAppId + "\"," +
                "\"contents\":{\"en\":\"" + message + "\"}," +
                "\"include_player_ids\":[\"" + playerId1 + "\",\"" + playerId2 + "\"]" +
                "}";

        final String expectedResponse = "{" +
                "\"id\":\"" + UUID.randomUUID().toString() + "\"," +
                "\"recipients\":1," +
                "\"external_id\":null" +
                "}";

        mockServer.expect(requestTo(url))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header(HttpHeaders.AUTHORIZATION, "Basic " + oneSignalApiKey))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedPayload))
                .andRespond(withSuccess(expectedResponse, MediaType.APPLICATION_JSON));

        final String response = service.sendNotificationOneSignal(List.of(playerId1, playerId2), message);
        assertEquals(expectedResponse, response);
    }

    @Test
    void testSendWithBadRequest() {
        final String playerId1 = "invalid-id";
        final String message = "Example message";

        final String expectedPayload = "{" +
                "\"app_id\":\"" + oneSignalAppId + "\"," +
                "\"contents\":{\"en\":\"" + message + "\"}," +
                "\"include_player_ids\":[\"" + playerId1 + "\"]" +
                "}";

        final String expectedResponse = "{" +
                "\"id\":\"" + UUID.randomUUID().toString() + "\"," +
                "\"recipients\":1," +
                "\"external_id\":null," +
                "\"errors\":{" +
                "\"invalid_player_ids\":[\"" + playerId1 + "\"]" +
                "}" +
                "}";

        mockServer.expect(requestTo(url))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header(HttpHeaders.AUTHORIZATION, "Basic " + oneSignalApiKey))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedPayload))
                .andRespond(withBadRequest().body(expectedResponse));

        final String response = service.sendNotificationOneSignal(List.of(playerId1), message);
        assertEquals(expectedResponse, response);
    }
}
