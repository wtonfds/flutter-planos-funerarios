package br.com.monitoratec.farol.graphql.model.dtos.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetNetCreditDTO {
    @JsonProperty("transaction_type")
    private String transactionType;
    @JsonProperty("number_installments")
    private Integer numberInstallments;

    private GetNetSubscriptionCardDTO card;

    public GetNetCreditDTO(String transactionType, Integer numberInstallments, GetNetSubscriptionCardDTO card) {
        this.transactionType = transactionType;
        this.numberInstallments = numberInstallments;
        this.card = card;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getNumberInstallments() {
        return numberInstallments;
    }

    public void setNumberInstallments(Integer numberInstallments) {
        this.numberInstallments = numberInstallments;
    }

    public GetNetSubscriptionCardDTO getCard() {
        return card;
    }

    public void setCard(GetNetSubscriptionCardDTO card) {
        this.card = card;
    }
}
