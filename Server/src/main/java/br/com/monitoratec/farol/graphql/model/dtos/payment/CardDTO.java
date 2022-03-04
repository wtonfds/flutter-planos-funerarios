package br.com.monitoratec.farol.graphql.model.dtos.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CardDTO {
    @JsonProperty("card_id")
    private String cardId;
    @JsonProperty("number_token")
    private String numberToken;
    @JsonProperty("expiration_month")
    private String expirationMonth;
    @JsonProperty("expiration_year")
    private String expirationYear;
    @JsonProperty("cardholder_name")
    private String cardholderName;

    public String getNumberToken() {
        return numberToken;
    }

    public void setNumberToken(String numberToken) {
        this.numberToken = numberToken;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public String getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }
}
