package br.com.monitoratec.farol.graphql.model.input.price;

public class PriceTableAgeRangeInput {
    private int startAge;
    private int endAge;
    private double value;

    public PriceTableAgeRangeInput() {
    }

    public PriceTableAgeRangeInput(int startAge, int endAge, double value) {
        this.startAge = startAge;
        this.endAge = endAge;
        this.value = value;
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
}
