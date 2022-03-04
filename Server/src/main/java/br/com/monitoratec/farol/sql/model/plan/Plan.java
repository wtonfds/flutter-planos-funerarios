package br.com.monitoratec.farol.sql.model.plan;

import br.com.monitoratec.farol.sql.model.price.DependentPriceTable;
import br.com.monitoratec.farol.sql.model.price.PlanPriceTable;
import br.com.monitoratec.farol.sql.model.price.UpgradePriceTable;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "frl_plan")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frl_plan_sequence")
    @SequenceGenerator(name = "frl_plan_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private long gracePeriod;

    @Column(nullable = false)
    private int maxExtraDependentsAmount;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private String adhesionContract;

    @Column(nullable = false)
    private String contractUrl;

    @OneToMany(mappedBy = "plan")
    private List<SubscribedPlan> subscribedPlanList;

    @ManyToOne
    private PlanPriceTable planPriceTable;

    @ManyToOne
    private UpgradePriceTable upgradePriceTable;

    @ManyToOne
    private DependentPriceTable dependentPriceTable;

    @Column(nullable = false)
    private long gracePeriodExtraDependents;

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

    public long getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(long gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public int getMaxExtraDependentsAmount() {
        return maxExtraDependentsAmount;
    }

    public void setMaxExtraDependentsAmount(int maxExtraDependentsAmount) {
        this.maxExtraDependentsAmount = maxExtraDependentsAmount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAdhesionContract() {
        return adhesionContract;
    }

    public void setAdhesionContract(String adhesionContract) {
        this.adhesionContract = adhesionContract;
    }

    public List<SubscribedPlan> getSubscribedPlanList() {
        return subscribedPlanList;
    }

    public void setSubscribedPlanList(List<SubscribedPlan> subscribedPlanList) {
        this.subscribedPlanList = subscribedPlanList;
    }

    public PlanPriceTable getPlanPriceTable() {
        return planPriceTable;
    }

    public void setPlanPriceTable(PlanPriceTable planPriceTable) {
        this.planPriceTable = planPriceTable;
    }

    public UpgradePriceTable getUpgradePriceTable() {
        return upgradePriceTable;
    }

    public void setUpgradePriceTable(UpgradePriceTable upgradePriceTable) {
        this.upgradePriceTable = upgradePriceTable;
    }

    public DependentPriceTable getDependentPriceTable() {
        return dependentPriceTable;
    }

    public void setDependentPriceTable(DependentPriceTable dependentPriceTable) {
        this.dependentPriceTable = dependentPriceTable;
    }

    public String getContractUrl() {
        return contractUrl;
    }

    public void setContractUrl(String contractUrl) {
        this.contractUrl = contractUrl;
    }

    public long getGracePeriodExtraDependents() {
        return gracePeriodExtraDependents;
    }

    public void setGracePeriodExtraDependents(long gracePeriodExtraDependents) {
        this.gracePeriodExtraDependents = gracePeriodExtraDependents;
    }

    public static class Fields {
        public static final String ID = "id";
    }
}
