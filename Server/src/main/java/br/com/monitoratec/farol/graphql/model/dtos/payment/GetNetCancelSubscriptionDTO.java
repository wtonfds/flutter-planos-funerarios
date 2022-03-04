package br.com.monitoratec.farol.graphql.model.dtos.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetNetCancelSubscriptionDTO {
    @JsonProperty("seller_id")
    private String sellerId;

    @JsonProperty("status_details")
    private String statusDetails;

    public GetNetCancelSubscriptionDTO(String sellerId, String statusDetails) {
        this.sellerId = sellerId;
        this.statusDetails = statusDetails;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getStatusDetails() {
        return statusDetails;
    }

    public void setStatusDetails(String statusDetails) {
        this.statusDetails = statusDetails;
    }
}
