package br.com.monitoratec.farol.graphql.model.dtos.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetNetPeriodDTO {
    private String type;
    @JsonProperty("billing_cycle")
    private Integer billingCycle;

    public GetNetPeriodDTO() {
        this("monthly", 9999);
    }

    public GetNetPeriodDTO(String type, Integer billingCycle) {
        this.type = type;
        this.billingCycle = billingCycle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(Integer billingCycle) {
        this.billingCycle = billingCycle;
    }
}
