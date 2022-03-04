package br.com.monitoratec.farol.service.invoice.model;

import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Optional;

public class Values {
    private final BigDecimal servicesValue;
    private final BigDecimal deductionsValue;
    private final BigDecimal pisValue;
    private final BigDecimal cofinsValue;
    private final BigDecimal inssValue;
    private final BigDecimal taxIncomeValue; //Imposto de Renda
    private final BigDecimal csllValue;
    private final boolean retainedTaxIncome;
    private final BigDecimal issValue;
    private final BigDecimal retainedIssValue;
    private final BigDecimal otherRetentions;
    private final BigDecimal aliquot; //Percent - 0.0000-1.0000
    private final BigDecimal unconditionedDiscount;
    private final BigDecimal conditionedDiscount;

    private final BigDecimal calculationBasis;
    private final BigDecimal invoiceNetValue;

    private Values(Builder builder) {
        Assert.notNull(builder.servicesValue, "Services value must not be null");
        if (builder.aliquot != null) {
            Assert.isTrue(builder.aliquot.compareTo(BigDecimal.ZERO) >= 0 && builder.aliquot.compareTo(BigDecimal.ONE) <= 0, "Aliquot must be between 0 and 1");
        }

        this.servicesValue = builder.servicesValue;
        this.deductionsValue = builder.deductionsValue;
        this.pisValue = builder.pisValue;
        this.cofinsValue = builder.cofinsValue;
        this.inssValue = builder.inssValue;
        this.taxIncomeValue = builder.taxIncomeValue;
        this.csllValue = builder.csllValue;
        this.retainedTaxIncome = builder.retainedTaxIncome;
        this.issValue = builder.issValue;
        this.retainedIssValue = builder.retainedIssValue;
        this.otherRetentions = builder.otherRetentions;
        this.aliquot = builder.aliquot;
        this.unconditionedDiscount = builder.unconditionedDiscount;
        this.conditionedDiscount = builder.conditionedDiscount;

        this.calculationBasis = calculateBasis();
        this.invoiceNetValue = calculateNetValue();
    }

    private BigDecimal calculateBasis() {
        BigDecimal result = servicesValue;
        if (deductionsValue != null) {
            result = result.subtract(deductionsValue);
        }
        if (unconditionedDiscount != null) {
            result = result.subtract(unconditionedDiscount);
        }

        return result;
    }

    private BigDecimal calculateNetValue() {
        BigDecimal result = servicesValue;
        if (pisValue != null) {
            result = result.subtract(pisValue);
        }
        if (cofinsValue != null) {
            result = result.subtract(cofinsValue);
        }
        if (inssValue != null) {
            result = result.subtract(inssValue);
        }
        if (taxIncomeValue != null) {
            result = result.subtract(taxIncomeValue);
        }
        if (csllValue != null) {
            result = result.subtract(csllValue);
        }
        if (otherRetentions != null) {
            result = result.subtract(otherRetentions);
        }
        if (retainedIssValue != null) {
            result = result.subtract(retainedIssValue);
        }
        if (unconditionedDiscount != null) {
            result = result.subtract(unconditionedDiscount);
        }
        if (conditionedDiscount != null) {
            result = result.subtract(conditionedDiscount);
        }

        return result;
    }

    public BigDecimal getServicesValue() {
        return servicesValue;
    }

    public Optional<BigDecimal> getDeductionsValue() {
        return Optional.ofNullable(deductionsValue);
    }

    public Optional<BigDecimal> getPisValue() {
        return Optional.ofNullable(pisValue);
    }

    public Optional<BigDecimal> getCofinsValue() {
        return Optional.ofNullable(cofinsValue);
    }

    public Optional<BigDecimal> getInssValue() {
        return Optional.ofNullable(inssValue);
    }

    public Optional<BigDecimal> getTaxIncomeValue() {
        return Optional.ofNullable(taxIncomeValue);
    }

    public Optional<BigDecimal> getCsllValue() {
        return Optional.ofNullable(csllValue);
    }

    public boolean isRetainedTaxIncome() {
        return retainedTaxIncome;
    }

    public Optional<BigDecimal> getIssValue() {
        return Optional.ofNullable(issValue);
    }

    public Optional<BigDecimal> getRetainedIssValue() {
        return Optional.ofNullable(retainedIssValue);
    }

    public Optional<BigDecimal> getOtherRetentions() {
        return Optional.ofNullable(otherRetentions);
    }

    public Optional<BigDecimal> getAliquot() {
        return Optional.ofNullable(aliquot);
    }

    public Optional<BigDecimal> getUnconditionedDiscount() {
        return Optional.ofNullable(unconditionedDiscount);
    }

    public Optional<BigDecimal> getConditionedDiscount() {
        return Optional.ofNullable(conditionedDiscount);
    }

    public BigDecimal getCalculationBasis() {
        return calculationBasis;
    }

    public BigDecimal getInvoiceNetValue() {
        return invoiceNetValue;
    }

    public static final class Builder {
        private BigDecimal servicesValue;
        private BigDecimal deductionsValue;
        private BigDecimal pisValue;
        private BigDecimal cofinsValue;
        private BigDecimal inssValue;
        private BigDecimal taxIncomeValue; //Imposto de Renda
        private BigDecimal csllValue;
        private boolean retainedTaxIncome;
        private BigDecimal issValue;
        private BigDecimal retainedIssValue;
        private BigDecimal otherRetentions;
        private BigDecimal aliquot;
        private BigDecimal unconditionedDiscount;
        private BigDecimal conditionedDiscount;

        public Builder setServicesValue(BigDecimal servicesValue) {
            this.servicesValue = servicesValue;
            return this;
        }

        public Builder setDeductionsValue(BigDecimal deductionsValue) {
            this.deductionsValue = deductionsValue;
            return this;
        }

        public Builder setPisValue(BigDecimal pisValue) {
            this.pisValue = pisValue;
            return this;
        }

        public Builder setCofinsValue(BigDecimal cofinsValue) {
            this.cofinsValue = cofinsValue;
            return this;
        }

        public Builder setInssValue(BigDecimal inssValue) {
            this.inssValue = inssValue;
            return this;
        }

        public Builder setTaxIncomeValue(BigDecimal taxIncomeValue) {
            this.taxIncomeValue = taxIncomeValue;
            return this;
        }

        public Builder setCsllValue(BigDecimal csllValue) {
            this.csllValue = csllValue;
            return this;
        }

        public Builder setRetainedTaxIncome(boolean retainedTaxIncome) {
            this.retainedTaxIncome = retainedTaxIncome;
            return this;
        }

        public Builder setIssValue(BigDecimal issValue) {
            this.issValue = issValue;
            return this;
        }

        public Builder setRetainedIssValue(BigDecimal retainedIssValue) {
            this.retainedIssValue = retainedIssValue;
            return this;
        }

        public Builder setOtherRetentions(BigDecimal otherRetentions) {
            this.otherRetentions = otherRetentions;
            return this;
        }

        public Builder setAliquot(BigDecimal aliquot) {
            this.aliquot = aliquot;
            return this;
        }

        public Builder setUnconditionedDiscount(BigDecimal unconditionedDiscount) {
            this.unconditionedDiscount = unconditionedDiscount;
            return this;
        }

        public Builder setConditionedDiscount(BigDecimal conditionedDiscount) {
            this.conditionedDiscount = conditionedDiscount;
            return this;
        }

        public Values build() {
            return new Values(this);
        }
    }
}
