package br.com.monitoratec.farol.graphql.model.dtos.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetNetSubscriptionDTO {
    @JsonProperty("seller_id")
    private String getNetSellerId;
    @JsonProperty("customer_id")
    private String customerId;
    @JsonProperty("plan_id")
    private String planId;

    private GetNetSubscriptionBodyDTO subscription;

    public GetNetSubscriptionDTO(String getNetSellerId, String customerId, String planId, GetNetSubscriptionBodyDTO subscription) {
        this.getNetSellerId = getNetSellerId;
        this.customerId = customerId;
        this.planId = planId;
        this.subscription = subscription;
    }

    public String getGetNetSellerId() {
        return getNetSellerId;
    }

    public void setGetNetSellerId(String getNetSellerId) {
        this.getNetSellerId = getNetSellerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public GetNetSubscriptionBodyDTO getSubscription() {
        return subscription;
    }

    public void setSubscription(GetNetSubscriptionBodyDTO subscription) {
        this.subscription = subscription;
    }
}
