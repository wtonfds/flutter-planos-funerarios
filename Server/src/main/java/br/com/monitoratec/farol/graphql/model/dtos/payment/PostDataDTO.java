package br.com.monitoratec.farol.graphql.model.dtos.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostDataDTO {
    @JsonProperty("issuer_payment_id")
    private String issuerPaymentId;
    @JsonProperty("payer_authentication_request")
    private String payerAuthenticationRequest;

    public String getIssuerPaymentId() {
        return issuerPaymentId;
    }

    public void setIssuerPaymentId(String issuerPaymentId) {
        this.issuerPaymentId = issuerPaymentId;
    }

    public String getPayerAuthenticationRequest() {
        return payerAuthenticationRequest;
    }

    public void setPayerAuthenticationRequest(String payerAuthenticationRequest) {
        this.payerAuthenticationRequest = payerAuthenticationRequest;
    }
}
