package br.com.monitoratec.farol.sql.model.payment;

import javax.persistence.*;

@Entity
@Table(name = "frl_unchecked_invoice")
public class UncheckedInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frl_unchecked_invoice_sequence")
    @SequenceGenerator(name = "frl_unchecked_invoice_sequence", allocationSize = 1)
    private Long id;

    @OneToOne
    private PaymentHistory paymentHistory;

    private Boolean error;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentHistory getPaymentHistory() {
        return paymentHistory;
    }

    public void setPaymentHistory(PaymentHistory paymentHistory) {
        this.paymentHistory = paymentHistory;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }
}
