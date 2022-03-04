package br.com.monitoratec.farol.graphql.model.input.payment;

public class CardInput {
    private String numberToken;
    private String cardholderName;
    private String securityCode;
    private String expirationMonth;
    private String expirationYear;

    public CardInput(String numberToken, String cardholderName, String securityCode, String expirationMonth, String expirationYear) {
        this.numberToken = numberToken;
        this.cardholderName = cardholderName;
        this.securityCode = securityCode;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
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
}
