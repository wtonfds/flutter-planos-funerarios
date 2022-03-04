package br.com.monitoratec.farol.sql.model.payment;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "frl_payment_discount")
public class PaymentDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frl_payment_discount_sequence")
    @SequenceGenerator(name = "frl_payment_discount_sequence", allocationSize = 1)
    private long id;

    private int month;

    private BigDecimal discount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
}
