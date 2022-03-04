package br.com.monitoratec.farol.sql.model.payment;

import br.com.monitoratec.farol.graphql.model.dtos.payment.PaymentTypeDTO;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "frl_payment_history")
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frl_payment_history_sequence")
    @SequenceGenerator(name = "frl_payment_history_sequence", allocationSize = 1)
    private long id;
    private LocalDateTime createdAt;
    private String orderId;
    private String paymentId;
    private String paymentSlipId;
    private String status;
    private Double value;
    private PaymentTypeDTO paymentType;
    private LocalDate expirationDate;
    private String invoiceProtocolNumber;

    private LocalDateTime liquidationDate;

    @ManyToMany()
    private List<PaymentMonth> paymentMonth;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentSlipId() {
        return paymentSlipId;
    }

    public void setPaymentSlipId(String paymentSlipId) {
        this.paymentSlipId = paymentSlipId;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDateTime getLiquidationDate() {
        return liquidationDate;
    }

    public void setLiquidationDate(LocalDateTime liquidationDate) {
        this.liquidationDate = liquidationDate;
    }

    public List<PaymentMonth> getPaymentMonth() {
        return paymentMonth;
    }

    public void setPaymentMonth(List<PaymentMonth> paymentMonth) {
        this.paymentMonth = paymentMonth;
    }

    public PaymentTypeDTO getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentTypeDTO paymentType) {
        this.paymentType = paymentType;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getInvoiceProtocolNumber() {
        return invoiceProtocolNumber;
    }

    public void setInvoiceProtocolNumber(String invoiceProtocolNumber) {
        this.invoiceProtocolNumber = invoiceProtocolNumber;
    }
}
