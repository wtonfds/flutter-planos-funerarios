package br.com.monitoratec.farol.graphql.model.dtos.price;

import br.com.monitoratec.farol.sql.model.price.PriceTableAgeRange;

import java.util.Objects;

public class PriceTableAgeRangeDTO {
    private long id;
    private int startAge;
    private int endAge;
    private double value;

    public PriceTableAgeRangeDTO() {
    }

    public PriceTableAgeRangeDTO(long id, int startAge, int endAge, double value) {
        this.id = id;
        this.startAge = startAge;
        this.endAge = endAge;
        this.value = value;
    }

    public PriceTableAgeRangeDTO(PriceTableAgeRange priceTableAgeRange) {
        this.id = priceTableAgeRange.getId();
        this.startAge = priceTableAgeRange.getStartAge();
        this.endAge = priceTableAgeRange.getEndAge();
        this.value = priceTableAgeRange.getValue().doubleValue();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStartAge() {
        return startAge;
    }

    public void setStartAge(int startAge) {
        this.startAge = startAge;
    }

    public int getEndAge() {
        return endAge;
    }

    public void setEndAge(int endAge) {
        this.endAge = endAge;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceTableAgeRangeDTO that = (PriceTableAgeRangeDTO) o;
        return id == that.id &&
                startAge == that.startAge &&
                endAge == that.endAge &&
                Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startAge, endAge, value);
    }

    @Override
    public String toString() {
        return "PriceTableAgeRangeDTO{" +
                "id=" + id +
                ", startAge=" + startAge +
                ", endAge=" + endAge +
                ", value=" + value +
                '}';
    }
}
