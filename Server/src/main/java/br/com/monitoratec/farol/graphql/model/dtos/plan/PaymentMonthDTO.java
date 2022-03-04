package br.com.monitoratec.farol.graphql.model.dtos.plan;

import br.com.monitoratec.farol.graphql.customTypes.Date;
import br.com.monitoratec.farol.sql.model.payment.PaymentMonth;

import java.time.LocalDate;

public class PaymentMonthDTO {
    private Integer month;
    private Integer year;
    private Boolean paid;
    private Date dueDate;
    private Boolean isOverdue;

    public PaymentMonthDTO(PaymentMonth paymentMonth) {
        this.month = paymentMonth.getMonth();
        this.year = paymentMonth.getYear();
        this.paid = paymentMonth.isPaid();

        //TODO oneToMany e logica para cartão de crédito

        if(paymentMonth.getPaymentHistoryList().size() > 0  &&
                paymentMonth.getPaymentHistoryList().get(0).getExpirationDate() != null) {
            this.dueDate = new Date(paymentMonth.getPaymentHistoryList().get(0).getExpirationDate());
            if(this.dueDate.getDate() != null) {
                this.isOverdue = LocalDate.now().isAfter(this.dueDate.getDate());
            }
        }
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

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getOverdue() {
        return isOverdue;
    }

    public void setOverdue(Boolean overdue) {
        isOverdue = overdue;
    }
}
