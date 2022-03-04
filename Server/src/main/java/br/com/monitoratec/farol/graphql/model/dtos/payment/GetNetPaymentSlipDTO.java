package br.com.monitoratec.farol.graphql.model.dtos.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetNetPaymentSlipDTO {
    @JsonProperty("seller_id")
    private String sellerId;
    private Integer amount;
    private String currency;
    private GetNetOrderDTO order;
    private GetNetPaymentSlipBodyDTO boleto;
    private GetNetCustomerDTO customer;

    public GetNetPaymentSlipDTO(String sellerId, Integer amount, String currency, GetNetOrderDTO order, GetNetPaymentSlipBodyDTO boleto, GetNetCustomerDTO customer) {
        this.sellerId = sellerId;
        this.amount = amount;
        this.currency = currency;
        this.order = order;
        this.boleto = boleto;
        this.customer = customer;
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

    public GetNetPaymentSlipBodyDTO getBoleto() {
        return boleto;
    }

    public void setBoleto(GetNetPaymentSlipBodyDTO boleto) {
        this.boleto = boleto;
    }

    public GetNetCustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(GetNetCustomerDTO customer) {
        this.customer = customer;
    }
}
