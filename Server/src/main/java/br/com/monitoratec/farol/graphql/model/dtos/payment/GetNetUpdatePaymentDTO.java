package br.com.monitoratec.farol.graphql.model.dtos.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetNetUpdatePaymentDTO {
    @JsonProperty("number_token")
    private String numberToken;
    @JsonProperty("cardholder_name")
    private String cardholderName;
    private String brand;
    @JsonProperty("expiration_month")
    private String expirationMonth;
    @JsonProperty("expiration_year")
    private String expirationYear;
    private String bin;

    private GetNetUpdatePaymentDTO updatedPayment;

    public GetNetUpdatePaymentDTO(String numberToken, String cardholderName, String brand, String expirationMonth, String expirationYear, String bin, GetNetUpdatePaymentDTO updatedPayment) {
        this.numberToken = numberToken;
        this.cardholderName = cardholderName;
        this.brand = brand;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.bin = bin;
        this.updatedPayment = updatedPayment;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public GetNetUpdatePaymentDTO getUpdatedPayment() {
        return updatedPayment;
    }

    public void setUpdatedPayment(GetNetUpdatePaymentDTO updatedPayment) {
        this.updatedPayment = updatedPayment;
    }
}
