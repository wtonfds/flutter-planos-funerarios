package br.com.monitoratec.farol.graphql.model.dtos.payment;

import br.com.monitoratec.farol.graphql.model.input.payment.CardInput;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CardInputDTO {
    @JsonProperty("number_token")
    private String numberToken;
    @JsonProperty("cardholder_name")
    private String cardholderName;
    @JsonProperty("security_code")
    private String securityCode;
    @JsonProperty("expiration_month")
    private String expirationMonth;
    @JsonProperty("expiration_year")
    private String expirationYear;
    @JsonProperty("customer_id")
    private String customerId;

    public CardInputDTO(CardInput cardInput, String customerId) {
        this.numberToken = cardInput.getNumberToken();
        this.cardholderName = cardInput.getCardholderName();
        this.securityCode = cardInput.getSecurityCode();
        this.expirationMonth = cardInput.getExpirationMonth();
        this.expirationYear = cardInput.getExpirationYear();
        this.customerId = customerId;
    }

    public String getNumberToken() {
        return numberToken;
    }

    public void setNumberToken(String numberToken) {
        this.numberToken = numberToken;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
