package br.com.monitoratec.farol.graphql.model.dtos.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class GetNetPlanDTO {
    @JsonProperty("seller_id")
    private String sellerId;
    @JsonProperty("product_type")
    private String productType;
    @JsonProperty("payment_types")
    private List<String> paymentTypes;
    private String name;
    private Integer amount;
    private String currency;
    private GetNetPeriodDTO period;

    public GetNetPlanDTO(String sellerId, String productType, List<String> paymentTypes, String name, Integer amount, String currency, GetNetPeriodDTO period) {
        this.sellerId = sellerId;
        this.productType = productType;
        this.paymentTypes = paymentTypes;
        this.name = name;
        this.amount = amount;
        this.currency = currency;
        this.period = period;
    }
    public GetNetPlanDTO(String sellerId, String name, Integer amount, GetNetPeriodDTO getNetPeriodDTO) {
        this.sellerId = sellerId;
        this.productType = "renew_subs";
        ArrayList<String> payment = new ArrayList<>();
        payment.add("credit_card");
        this.paymentTypes = payment;
        this.name = name;
        this.amount = amount;
        this.currency = "BRL";
        this.period = getNetPeriodDTO;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public GetNetPeriodDTO getPeriod() {
        return period;
    }

    public void setPeriod(GetNetPeriodDTO period) {
        this.period = period;
    }
}
