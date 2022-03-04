package br.com.monitoratec.farol.rest.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreditCardSubscriptionReturnWrapper {
    private CreditCardSubscriptionReturnPaymentWrapper payment;

    private String status;
    @JsonProperty(value = "status_details")
    private String statusDetails;

    public CreditCardSubscriptionReturnPaymentWrapper getPayment() {
        return payment;
    }

    public void setPayment(CreditCardSubscriptionReturnPaymentWrapper payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDetails() {
        return statusDetails;
    }

    public void setStatusDetails(String statusDetails) {
        this.statusDetails = statusDetails;
    }
}
