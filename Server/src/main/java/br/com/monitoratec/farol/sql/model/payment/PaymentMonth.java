package br.com.monitoratec.farol.sql.model.payment;

import br.com.monitoratec.farol.sql.model.plan.SubscribedPlan;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "frl_payment_month")
public class PaymentMonth {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frl_payment_month_sequence")
    @SequenceGenerator(name = "frl_payment_month_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private int month;

    @Column(nullable = false)
    private int year;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private SubscribedPlan subscribedPlan;

    @Column(nullable = false)
    private boolean paid;

    @ManyToMany(mappedBy = "paymentMonth", fetch = FetchType.EAGER)
    private List<PaymentHistory> paymentHistoryList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public SubscribedPlan getSubscribedPlan() {
        return subscribedPlan;
    }

    public void setSubscribedPlan(SubscribedPlan subscribedPlan) {
        this.subscribedPlan = subscribedPlan;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public List<PaymentHistory> getPaymentHistoryList() {
        return paymentHistoryList;
    }

    public void setPaymentHistoryList(List<PaymentHistory> paymentHistoryList) {
        this.paymentHistoryList = paymentHistoryList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentMonth that = (PaymentMonth) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
