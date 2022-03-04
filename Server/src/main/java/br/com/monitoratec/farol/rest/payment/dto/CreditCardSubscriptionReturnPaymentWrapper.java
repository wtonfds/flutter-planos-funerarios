package br.com.monitoratec.farol.rest.payment.dto;

public class CreditCardSubscriptionReturnPaymentWrapper {
    private CreditCardSubscriptionErrorWrapper error;

    public CreditCardSubscriptionErrorWrapper getError() {
        return error;
    }

    public void setError(CreditCardSubscriptionErrorWrapper error) {
        this.error = error;
    }
}
