package br.com.monitoratec.farol.service.sms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SmsService {
    private final RestTemplate restTemplate;
    private final String smsUrl;
    private final String company;
    private final String user;
    private final String password;
    private final String costCenter;

    public SmsService(RestTemplate restTemplate, @Value("${sms.url}") String smsUrl, @Value("${sms.company}") String company,
                      @Value("${sms.user}") String user, @Value("${sms.password}") String password, @Value("${sms.cost_center}") String costCenter) {
        this.restTemplate = restTemplate;
        this.smsUrl = smsUrl;
        this.company = company;
        this.user = user;
        this.password = password;
        this.costCenter = costCenter;
    }

    public void send(String to, String message) {
        final String url = smsUrl + "?modo=envio&empresa={company}&usuario={user}&senha={password}&telefone={to}&mensagem={message}&centro_custo={costCenter}%2055";

        restTemplate.getForObject(url, String.class, company, user, password, to, message, costCenter);
    }
}
