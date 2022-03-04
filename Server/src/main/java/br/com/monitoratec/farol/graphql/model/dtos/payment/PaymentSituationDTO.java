package br.com.monitoratec.farol.graphql.model.dtos.payment;

import br.com.monitoratec.farol.graphql.customTypes.DateTime;
import br.com.monitoratec.farol.sql.model.payment.PaymentHistory;
import br.com.monitoratec.farol.sql.model.payment.PaymentMonth;

import java.time.LocalDateTime;
import java.util.Objects;

public class PaymentSituationDTO {
    private String status;
    private String paymentType;
    private DateTime liquidationDate;
    private Integer month;
    private Integer year;
    private Double value;

    public PaymentSituationDTO() {
    }

    public PaymentSituationDTO(PaymentMonth paymentMonth, PaymentHistory paymentHistory) {
        this.status = paymentHistory.getStatus();
        this.paymentType = paymentHistory.getPaymentType().name();
        LocalDateTime liquidationDate = paymentHistory.getLiquidationDate();
        this.liquidationDate = liquidationDate == null ? null : new DateTime(paymentHistory.getLiquidationDate());
        this.month = paymentMonth.getMonth();
        this.year = paymentMonth.getYear();
        this.value = paymentHistory.getValue();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public DateTime getLiquidationDate() {
        return liquidationDate;
    }

    public void setLiquidationDate(DateTime liquidationDate) {
        this.liquidationDate = liquidationDate;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentSituationDTO that = (PaymentSituationDTO) o;
        return Objects.equals(status, that.status) &&
                Objects.equals(paymentType, that.paymentType) &&
                Objects.equals(liquidationDate, that.liquidationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, paymentType, liquidationDate);
    }

    @Override
    public String toString() {
        return "PaymentSituationDTO{" +
                "status='" + status + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", liquidationDate=" + liquidationDate +
                '}';
    }
}
