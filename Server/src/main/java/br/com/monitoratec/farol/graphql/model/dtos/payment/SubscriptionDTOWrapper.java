package br.com.monitoratec.farol.graphql.model.dtos.payment;

import java.util.List;

public class SubscriptionDTOWrapper {
    private List<SubscriptionDTO> subscriptions;

    public List<SubscriptionDTO> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<SubscriptionDTO> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
