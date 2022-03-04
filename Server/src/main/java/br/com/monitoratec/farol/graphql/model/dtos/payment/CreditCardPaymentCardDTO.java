package br.com.monitoratec.farol.graphql.model.dtos.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreditCardPaymentCardDTO {
    @JsonProperty("number_token")
    private String numberToken;
    @JsonProperty("expiration_month")
    private String expirationMonth;
    @JsonProperty("expiration_year")
    private String expirationYear;
    @JsonProperty("cardholder_name")
    private String cardholderName;

    public CreditCardPaymentCardDTO(CardDTO cardDTO) {
        this.numberToken = cardDTO.getNumberToken();
        this.expirationMonth = cardDTO.getExpirationMonth();
        this.expirationYear = cardDTO.getExpirationYear();
        this.cardholderName = cardDTO.getCardholderName();
    }

    public String getNumberToken() {
        return numberToken;
    }

    public void setNumberToken(String numberToken) {
        this.numberToken = numberToken;
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
