package br.com.monitoratec.farol.rest.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreditCardSubscriptionReturnDetails {
    private String description;
    @JsonProperty(value = "description_detail")
    private String descriptionDetail;
    @JsonProperty(value = "error_code")
    private String errorCode;
    @JsonProperty(value = "payment_id")
    private String paymentId;
    private String status;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionDetail() {
        return descriptionDetail;
    }

    public void setDescriptionDetail(String descriptionDetail) {
        this.descriptionDetail = descriptionDetail;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
