package br.com.monitoratec.farol.service.push;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PushService {
    private final RestTemplate restTemplate;
    private final String url;
    private final String oneSignalAppId;
    private final String oneSignalApiKey;

    public PushService(RestTemplate restTemplate, @Value("${push.onesignal-url}") String url,
                       @Value("${push.onesignal-app-id}") String oneSignalAppId, @Value("${push.onesignal-api-key}") String oneSignalApiKey) {
        this.restTemplate = restTemplate;
        this.url = url;
        this.oneSignalAppId = oneSignalAppId;
        this.oneSignalApiKey = oneSignalApiKey;
    }

    public String sendNotificationOneSignal(List<String> includePlayerIds, String message) {
        final HttpHeaders headers = createHeaders();
        final Map<String, Object> request = createRequest(includePlayerIds, message);

        final HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        String response;
        try {
            response = restTemplate.postForObject(url, entity, String.class);
        } catch (RestClientResponseException e) {
            response = e.getResponseBodyAsString();
        }

        return response;
    }

    private HttpHeaders createHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(oneSignalApiKey);

        return headers;
    }

    /*
    Format:
    {
      "app_id": "<one_signal_app_id>",
      "contents": {"en": "<message>"},
      "include_player_ids": ["<player_1_id>", "<player_2_id>"]
    }

    include_player_ids might be replaced with "included_segments": ["Active Users"] if the list is empty.
     */
    private Map<String, Object> createRequest(List<String> includePlayerIds, String message) {
        final Map<String, Object> request = new LinkedHashMap<>(3);
        request.put("app_id", oneSignalAppId);
        request.put("contents", Map.of("en", message));

        if (includePlayerIds.isEmpty()) {
            request.put("included_segments", List.of("Active Users"));
        } else {
            request.put("include_player_ids", includePlayerIds);
        }

        return request;
    }
}
