package br.com.monitoratec.farol.rest.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CreditCardSubscriptionErrorWrapper {
    private List<CreditCardSubscriptionReturnDetails> details;

    @JsonProperty(value = "status_code")
    private Integer statusCode;
    private String name;
    private String message;

    public List<CreditCardSubscriptionReturnDetails> getDetails() {
        return details;
    }

    public void setDetails(List<CreditCardSubscriptionReturnDetails> details) {
        this.details = details;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
