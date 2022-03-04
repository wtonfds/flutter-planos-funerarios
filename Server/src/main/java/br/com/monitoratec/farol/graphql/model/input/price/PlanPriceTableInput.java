package br.com.monitoratec.farol.graphql.model.input.price;

import java.util.List;

public class PlanPriceTableInput {
    private String name;
    private List<PriceTableAgeRangeInput> ageRanges;

    public PlanPriceTableInput() {
    }

    public PlanPriceTableInput(String name, List<PriceTableAgeRangeInput> ageRanges) {
        this.name = name;
        this.ageRanges = ageRanges;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PriceTableAgeRangeInput> getAgeRanges() {
        return ageRanges;
    }

    public void setAgeRanges(List<PriceTableAgeRangeInput> ageRanges) {
        this.ageRanges = ageRanges;
    }
}
