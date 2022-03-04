package br.com.monitoratec.farol.graphql.model.dtos.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetNetSubscriptionCreditDTO {
    private GetNetSubscriptionCardDTO card;
    @JsonProperty("transaction_type")
    private String transactionType;
    @JsonProperty("number_installments")
    private String numberInstallments;

    public GetNetSubscriptionCreditDTO(GetNetSubscriptionCardDTO card, String transactionType, String numberInstallments) {
        this.card = card;
        this.transactionType = transactionType;
        this.numberInstallments = numberInstallments;
    }

    public GetNetSubscriptionCardDTO getCard() {
        return card;
    }

    public void setCard(GetNetSubscriptionCardDTO card) {
        this.card = card;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getNumberInstallments() {
        return numberInstallments;
    }

    public void setNumberInstallments(String numberInstallments) {
        this.numberInstallments = numberInstallments;
    }
}
