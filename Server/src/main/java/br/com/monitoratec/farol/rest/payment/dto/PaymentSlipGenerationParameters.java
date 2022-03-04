package br.com.monitoratec.farol.rest.payment.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

//Note: we are ignoring parameter "payment_type" as this class will be used only for payment slips
public class PaymentSlipGenerationParameters {
    private final String order_id; //The property names must follow the same naming convention as the request parameters
    private final String payment_id;
    private final String id;
    private final int amount; //in cents
    private final String status;
    private final String bank;
    private final String our_number;
    private final String typeful_line;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private final LocalDateTime issue_date;

    public PaymentSlipGenerationParameters(String order_id, String payment_id, String id, int amount, String status,
                                           String bank, String our_number, String typeful_line, LocalDateTime issue_date) {
        this.order_id = order_id;
        this.payment_id = payment_id;
        this.id = id;
        this.amount = amount;
        this.status = status;
        this.bank = bank;
        this.our_number = our_number;
        this.typeful_line = typeful_line;
        this.issue_date = issue_date;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public String getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public String getBank() {
        return bank;
    }

    public String getOur_number() {
        return our_number;
    }

    public String getTypeful_line() {
        return typeful_line;
    }

    public LocalDateTime getIssue_date() {
        return issue_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentSlipGenerationParameters that = (PaymentSlipGenerationParameters) o;
        return amount == that.amount &&
                Objects.equals(order_id, that.order_id) &&
                Objects.equals(payment_id, that.payment_id) &&
                Objects.equals(id, that.id) &&
                Objects.equals(status, that.status) &&
                Objects.equals(bank, that.bank) &&
                Objects.equals(our_number, that.our_number) &&
                Objects.equals(typeful_line, that.typeful_line) &&
                Objects.equals(issue_date, that.issue_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order_id, payment_id, id, amount, status, bank, our_number, typeful_line, issue_date);
    }

    @Override
    public String toString() {
        return "PaymentSlipNotificationParameters{" +
                "order_id='" + order_id + '\'' +
                ", payment_id='" + payment_id + '\'' +
                ", id='" + id + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", bank='" + bank + '\'' +
                ", our_number='" + our_number + '\'' +
                ", typeful_line='" + typeful_line + '\'' +
                ", issue_date=" + issue_date +
                '}';
    }
}
