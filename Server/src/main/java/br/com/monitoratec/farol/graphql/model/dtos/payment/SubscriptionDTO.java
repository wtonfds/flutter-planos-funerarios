package br.com.monitoratec.farol.graphql.model.dtos.payment;

public class SubscriptionDTO {
    private SubscriptionBodyDTO subscription;

    public SubscriptionBodyDTO getSubscription() {
        return subscription;
    }

    public void setSubscription(SubscriptionBodyDTO subscription) {
        this.subscription = subscription;
    }
}
