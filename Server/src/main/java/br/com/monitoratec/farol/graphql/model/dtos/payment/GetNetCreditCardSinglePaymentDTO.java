package br.com.monitoratec.farol.graphql.model.dtos.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetNetCreditCardSinglePaymentDTO {
    @JsonProperty("seller_id")
    private String sellerId;
    private Integer amount;
    private String currency;
    private GetNetOrderDTO order;
    private GetNetCustomerDTO customer;
    private GetNetCreditPaymentDTO credit;

    public GetNetCreditCardSinglePaymentDTO(String sellerId, Integer amount, String currency, GetNetOrderDTO order, GetNetCustomerDTO customer, GetNetCreditPaymentDTO credit) {
        this.sellerId = sellerId;
        this.amount = amount;
        this.currency = currency;
        this.order = order;
        this.customer = customer;
        this.credit = credit;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
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

    public GetNetOrderDTO getOrder() {
        return order;
    }

    public void setOrder(GetNetOrderDTO order) {
        this.order = order;
    }

    public GetNetCustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(GetNetCustomerDTO customer) {
        this.customer = customer;
    }

    public GetNetCreditPaymentDTO getCredit() {
        return credit;
    }

    public void setCredit(GetNetCreditPaymentDTO credit) {
        this.credit = credit;
    }
}
