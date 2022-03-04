package br.com.monitoratec.farol.rest.payment.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class RecurrenceUpdatedParameters {
    private final String order_id;
    private final String payment_id;
    private final int amount; //in cents
    private final String status;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private final LocalDateTime authorization_timestamp;
    private final String acquirer_transaction_id;
    private final String customer_id;
    private final String subscription_id;
    private final String plan_id;
    private final String charge_id;
    private final int number_installments;

    public RecurrenceUpdatedParameters(String order_id, String payment_id, int amount, String status,
                                       LocalDateTime authorization_timestamp, String acquirer_transaction_id, String customer_id,
                                       String subscription_id, String plan_id, String charge_id, int number_installments) {
        this.order_id = order_id;
        this.payment_id = payment_id;
        this.amount = amount;
        this.status = status;
        this.authorization_timestamp = authorization_timestamp;
        this.acquirer_transaction_id = acquirer_transaction_id;
        this.customer_id = customer_id;
        this.subscription_id = subscription_id;
        this.plan_id = plan_id;
        this.charge_id = charge_id;
        this.number_installments = number_installments;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public int getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getAuthorization_timestamp() {
        return authorization_timestamp;
    }

    public String getAcquirer_transaction_id() {
        return acquirer_transaction_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getSubscription_id() {
        return subscription_id;
    }

    public String getPlan_id() {
        return plan_id;
    }

    public String getCharge_id() {
        return charge_id;
    }

    public int getNumber_installments() {
        return number_installments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecurrenceUpdatedParameters that = (RecurrenceUpdatedParameters) o;
        return amount == that.amount &&
                number_installments == that.number_installments &&
                Objects.equals(order_id, that.order_id) &&
                Objects.equals(payment_id, that.payment_id) &&
                Objects.equals(status, that.status) &&
                Objects.equals(authorization_timestamp, that.authorization_timestamp) &&
                Objects.equals(acquirer_transaction_id, that.acquirer_transaction_id) &&
                Objects.equals(customer_id, that.customer_id) &&
                Objects.equals(subscription_id, that.subscription_id) &&
                Objects.equals(plan_id, that.plan_id) &&
                Objects.equals(charge_id, that.charge_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order_id, payment_id, amount, status, authorization_timestamp, acquirer_transaction_id,
                customer_id, subscription_id, plan_id, charge_id, number_installments);
    }

    @Override
    public String toString() {
        return "RecurrenceUpdatedParameters{" +
                "order_id='" + order_id + '\'' +
                ", payment_id='" + payment_id + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", authorization_timestamp=" + authorization_timestamp +
                ", acquirer_transaction_id='" + acquirer_transaction_id + '\'' +
                ", customer_id='" + customer_id + '\'' +
                ", subscription_id='" + subscription_id + '\'' +
                ", plan_id='" + plan_id + '\'' +
                ", charge_id='" + charge_id + '\'' +
                ", number_installments=" + number_installments +
                '}';
    }
}
