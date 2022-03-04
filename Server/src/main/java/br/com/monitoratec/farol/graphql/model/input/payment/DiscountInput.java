package br.com.monitoratec.farol.graphql.model.input.payment;

import java.math.BigDecimal;

public class DiscountInput {
    private int month;
    private BigDecimal discount;

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
