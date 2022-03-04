package br.com.monitoratec.farol.graphql.model.dtos.payment;

import br.com.monitoratec.farol.graphql.model.dtos.tem.CardDataDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetNetCreditPaymentDTO {
    private boolean delayed;
    @JsonProperty("save_card_data")
    private boolean saveCardData;
    @JsonProperty("transaction_type")
    private String transactionType;
    @JsonProperty("number_installments")
    private Integer numberInstallments;
    private CreditCardPaymentCardDTO card;

    public GetNetCreditPaymentDTO(boolean delayed, boolean saveCardData, String transactionType, Integer numberInstallments, CreditCardPaymentCardDTO card) {
        this.delayed = delayed;
        this.saveCardData = saveCardData;
        this.transactionType = transactionType;
        this.numberInstallments = numberInstallments;
        this.card = card;
    }

    public boolean isDelayed() {
        return delayed;
    }

    public void setDelayed(boolean delayed) {
        this.delayed = delayed;
    }

    public boolean isSaveCardData() {
        return saveCardData;
    }

    public void setSaveCardData(boolean saveCardData) {
        this.saveCardData = saveCardData;
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

    public CreditCardPaymentCardDTO getCard() {
        return card;
    }

    public void setCard(CreditCardPaymentCardDTO card) {
        this.card = card;
    }
}
