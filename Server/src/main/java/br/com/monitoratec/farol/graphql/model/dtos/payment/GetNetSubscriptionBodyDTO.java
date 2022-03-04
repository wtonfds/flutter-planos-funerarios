package br.com.monitoratec.farol.graphql.model.dtos.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetNetSubscriptionBodyDTO {
    @JsonProperty("payment_type")
    private GetNetPaymentTypeDTO paymentType;

    public GetNetSubscriptionBodyDTO(GetNetPaymentTypeDTO paymentType) {
        this.paymentType = paymentType;
    }

    public GetNetPaymentTypeDTO getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(GetNetPaymentTypeDTO paymentType) {
        this.paymentType = paymentType;
    }
}
