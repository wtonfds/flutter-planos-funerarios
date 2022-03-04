package br.com.monitoratec.farol.graphql.model.dtos.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetNetPaymentSlipReturnDTO {
    @JsonProperty("payment_id")
    private String paymentId;
    @JsonProperty("order_id")
    private String orderId;
    private String status;
    @JsonProperty("boleto")
    private GetNetPaymentSlipBodyDTO paymentBoletoDetail;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GetNetPaymentSlipBodyDTO getPaymentBoletoDetail() {
        return paymentBoletoDetail;
    }

    public void setPaymentBoletoDetail(GetNetPaymentSlipBodyDTO paymentBoletoDetail) {
        this.paymentBoletoDetail = paymentBoletoDetail;
    }
}
