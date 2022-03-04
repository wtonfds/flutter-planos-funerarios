package br.com.monitoratec.farol.graphql.model.dtos.plan;

import br.com.monitoratec.farol.graphql.model.dtos.price.DependentPriceTableDTO;
import br.com.monitoratec.farol.graphql.model.dtos.price.PlanPriceTableDTO;
import br.com.monitoratec.farol.graphql.model.dtos.price.PriceTableAgeRangeDTO;
import br.com.monitoratec.farol.graphql.model.dtos.price.UpgradePriceTableDTO;
import br.com.monitoratec.farol.sql.model.plan.Plan;

import java.util.stream.Collectors;

public class PlanDTO {

    private Long id;
    private String name;
    private Long gracePeriod;
    private Long gracePeriodExtraDependents;
    private Integer maxExtraDependentsAmount;
    private Boolean active;
    private String adhesionContract;
    private String contractUrl;
    private PlanPriceTableDTO planPriceTableDTO;
    private UpgradePriceTableDTO upgradePriceTableDTO;
    private DependentPriceTableDTO dependentPriceTableDTO;

    public PlanDTO(Plan plan) {
        this.id = plan.getId();
        this.name = plan.getName();
        this.gracePeriod = plan.getGracePeriod();
        this.gracePeriodExtraDependents = plan.getGracePeriodExtraDependents();
        this.maxExtraDependentsAmount = plan.getMaxExtraDependentsAmount();
        this.active = plan.isActive();
        this.adhesionContract = plan.getAdhesionContract();
        this.contractUrl = plan.getContractUrl();
        this.planPriceTableDTO = new PlanPriceTableDTO(
                plan.getPlanPriceTable().getId(),
                plan.getPlanPriceTable().getName(),
                plan.getPlanPriceTable().getAgeRanges().stream().map(PriceTableAgeRangeDTO::new).collect(Collectors.toList())
        );
        this.upgradePriceTableDTO = new UpgradePriceTableDTO(
                plan.getUpgradePriceTable().getId(),
                plan.getUpgradePriceTable().getName(),
                plan.getUpgradePriceTable().getAgeRanges().stream().map(PriceTableAgeRangeDTO::new).collect(Collectors.toList())
        );
        this.dependentPriceTableDTO = new DependentPriceTableDTO(
                plan.getDependentPriceTable().getId(),
                plan.getDependentPriceTable().getName(),
                plan.getDependentPriceTable().getAgeRanges().stream().map(PriceTableAgeRangeDTO::new).collect(Collectors.toList())
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public PlanPriceTableDTO getPlanPriceTableDTO() {
        return planPriceTableDTO;
    }

    public void setPlanPriceTableDTO(PlanPriceTableDTO planPriceTableDTO) {
        this.planPriceTableDTO = planPriceTableDTO;
    }

    public UpgradePriceTableDTO getUpgradePriceTableDTO() {
        return upgradePriceTableDTO;
    }

    public void setUpgradePriceTableDTO(UpgradePriceTableDTO upgradePriceTableDTO) {
        this.upgradePriceTableDTO = upgradePriceTableDTO;
    }

    public String getContractUrl() {
        return contractUrl;
    }

    public void setContractUrl(String contractUrl) {
        this.contractUrl = contractUrl;
    }

    public DependentPriceTableDTO getDependentPriceTableDTO() {
        return dependentPriceTableDTO;
    }

    public void setDependentPriceTableDTO(DependentPriceTableDTO dependentPriceTableDTO) {
        this.dependentPriceTableDTO = dependentPriceTableDTO;
    }
}
