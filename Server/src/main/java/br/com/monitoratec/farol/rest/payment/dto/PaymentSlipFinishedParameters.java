package br.com.monitoratec.farol.rest.payment.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;

public class PaymentSlipFinishedParameters {
    private final String id;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private final LocalDate payment_date;
    private final int amount; //in cents
    private final String status;

    public PaymentSlipFinishedParameters(String id, LocalDate payment_date, int amount, String status) {
        this.id = id;
        this.payment_date = payment_date;
        this.amount = amount;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public LocalDate getPayment_date() {
        return payment_date;
    }

    public int getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentSlipFinishedParameters that = (PaymentSlipFinishedParameters) o;
        return amount == that.amount &&
                Objects.equals(id, that.id) &&
                Objects.equals(payment_date, that.payment_date) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, payment_date, amount, status);
    }

    @Override
    public String toString() {
        return "PaymentSlipFinishedParameters{" +
                "id='" + id + '\'' +
                ", payment_date=" + payment_date +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                '}';
    }
}
