package br.com.monitoratec.farol.graphql.model.input.plan;

public class PlanInput {
    private String name;
    private Long gracePeriod;
    private Long gracePeriodExtraDependents;
    private Integer maxExtraDependentsAmount;
    private Boolean active;
    private String adhesionContract;
    private Long planPriceTableId;
    private Long upgradePriceTableId;
    private Long dependentPriceTableId;
    private String contractUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(Long gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public Long getGracePeriodExtraDependents() {
        return gracePeriodExtraDependents;
    }

    public void setGracePeriodExtraDependents(Long gracePeriodExtraDependents) {
        this.gracePeriodExtraDependents = gracePeriodExtraDependents;
    }

    public Integer getMaxExtraDependentsAmount() {
        return maxExtraDependentsAmount;
    }

    public void setMaxExtraDependentsAmount(Integer maxExtraDependentsAmount) {
        this.maxExtraDependentsAmount = maxExtraDependentsAmount;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAdhesionContract() {
        return adhesionContract;
    }

    public void setAdhesionContract(String adhesionContract) {
        this.adhesionContract = adhesionContract;
    }

    public Long getPlanPriceTableId() {
        return planPriceTableId;
    }

    public void setPlanPriceTableId(Long planPriceTableId) {
        this.planPriceTableId = planPriceTableId;
    }

    public Long getUpgradePriceTableId() {
        return upgradePriceTableId;
    }

    public void setUpgradePriceTableId(Long upgradePriceTableId) {
        this.upgradePriceTableId = upgradePriceTableId;
    }

    public Long getDependentPriceTableId() {
        return dependentPriceTableId;
    }

    public void setDependentPriceTableId(Long dependentPriceTableId) {
        this.dependentPriceTableId = dependentPriceTableId;
    }

    public String getContractUrl() {
        return contractUrl;
    }

    public void setContractUrl(String contractUrl) {
        this.contractUrl = contractUrl;
    }
}
