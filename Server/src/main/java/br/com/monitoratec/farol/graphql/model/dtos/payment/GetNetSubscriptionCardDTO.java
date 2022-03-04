package br.com.monitoratec.farol.graphql.model.dtos.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetNetSubscriptionCardDTO {
    @JsonProperty("number_token")
    private String numberToken;
    @JsonProperty("expiration_month")
    private String expirationMonth;
    @JsonProperty("expiration_year")
    private String expirationYear;
    @JsonProperty("cardholder_name")
    private String cardholderName;

    public GetNetSubscriptionCardDTO(CardDTO cardDTO) {
        this.numberToken = cardDTO.getNumberToken();
        String expire = cardDTO.getExpirationMonth();
        if (expire.length() == 1) {
            expire = "0" + expire;
        }
        this.expirationMonth = expire;
        this.expirationYear = cardDTO.getExpirationYear();
        this.cardholderName = cardDTO.getCardholderName();
    }

    public String getNumberToken() {
        return numberToken;
    }

    public void setNumberToken(String cardToken) {
        this.numberToken = cardToken;
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
