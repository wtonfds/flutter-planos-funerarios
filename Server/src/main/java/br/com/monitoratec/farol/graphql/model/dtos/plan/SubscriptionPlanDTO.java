package br.com.monitoratec.farol.graphql.model.dtos.plan;

import br.com.monitoratec.farol.graphql.customTypes.Date;
import br.com.monitoratec.farol.graphql.customTypes.DateTime;
import br.com.monitoratec.farol.graphql.model.dtos.location.AddressDTO;
import br.com.monitoratec.farol.graphql.model.dtos.payment.PaymentTypeDTO;
import br.com.monitoratec.farol.graphql.model.dtos.user.ClientDTO;
import br.com.monitoratec.farol.sql.model.plan.SubscribedPlan;

public class SubscriptionPlanDTO {
    private Long id;
    private String adhesionContract;
    private DateTime subscribedIn;
    private Double value;
    private Long clientNumber;
    private Date gracePeriod;
    private ClientDTO clientDTO;
    private PlanDTO planDTO;
    private Boolean active;
    private Long luckNumber;
    private Boolean isDefault;
    private DateTime cancelledIn;
    private AddressDTO addressDTO;
    private PaymentTypeDTO paymentType;
    private Double totalValue;
    private Boolean anticipationHaveDependent;

    public SubscriptionPlanDTO(SubscribedPlan subscribedPlan) {
        this.id = subscribedPlan.getId();
        this.adhesionContract = subscribedPlan.getAdhesionContract();

        if (subscribedPlan.getSubscribedIn() != null) {
            this.subscribedIn = new DateTime(subscribedPlan.getSubscribedIn());
        }

        this.value = subscribedPlan.getValue();
        this.clientNumber = subscribedPlan.getClientNumber();

        if (subscribedPlan.getGracePeriod() != null) {
            this.gracePeriod = new Date(subscribedPlan.getGracePeriod());
        }

        this.clientDTO = new ClientDTO(subscribedPlan.getBeneficiary());
        this.planDTO = new PlanDTO(subscribedPlan.getPlan());
        this.active = subscribedPlan.isActive();
        this.luckNumber = subscribedPlan.getLuckNumber();
        this.isDefault = subscribedPlan.isDefault();
        this.addressDTO = new AddressDTO(subscribedPlan.getAddress());
        this.totalValue = subscribedPlan.getValue();
        this.anticipationHaveDependent = subscribedPlan.getAnticipationHaveDependent();

        if (subscribedPlan.getCancelledIn() != null) {
            this.cancelledIn = new DateTime(subscribedPlan.getCancelledIn());
        }

        this.paymentType = PaymentTypeDTO.valueOf(subscribedPlan.getPaymentType());
    }

    public SubscriptionPlanDTO(SubscribedPlan subscribedPlan, Double totalValue) {
        this.id = subscribedPlan.getId();
        this.adhesionContract = subscribedPlan.getAdhesionContract();

        if (subscribedPlan.getSubscribedIn() != null) {
            this.subscribedIn = new DateTime(subscribedPlan.getSubscribedIn());
        }

        this.value = subscribedPlan.getValue();
        this.clientNumber = subscribedPlan.getClientNumber();

        if (subscribedPlan.getGracePeriod() != null) {
            this.gracePeriod = new Date(subscribedPlan.getGracePeriod());
        }

        this.clientDTO = new ClientDTO(subscribedPlan.getBeneficiary());
        this.planDTO = new PlanDTO(subscribedPlan.getPlan());
        this.active = subscribedPlan.isActive();
        this.luckNumber = subscribedPlan.getLuckNumber();
        this.isDefault = subscribedPlan.isDefault();
        this.addressDTO = new AddressDTO(subscribedPlan.getAddress());
        this.totalValue = totalValue;
        this.anticipationHaveDependent = subscribedPlan.getAnticipationHaveDependent();

        if (subscribedPlan.getCancelledIn() != null) {
            this.cancelledIn = new DateTime(subscribedPlan.getCancelledIn());
        }

        this.paymentType = PaymentTypeDTO.valueOf(subscribedPlan.getPaymentType());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdhesionContract() {
        return adhesionContract;
    }

    public void setAdhesionContract(String adhesionContract) {
        this.adhesionContract = adhesionContract;
    }

    public DateTime getSubscribedIn() {
        return subscribedIn;
    }

    public void setSubscribedIn(DateTime subscribedIn) {
        this.subscribedIn = subscribedIn;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Long getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(Long clientNumber) {
        this.clientNumber = clientNumber;
    }

    public Date getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(Date gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public ClientDTO getClientDTO() {
        return clientDTO;
    }

    public void setClientDTO(ClientDTO clientDTO) {
        this.clientDTO = clientDTO;
    }

    public PlanDTO getPlanDTO() {
        return planDTO;
    }

    public void setPlanDTO(PlanDTO planDTO) {
        this.planDTO = planDTO;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getLuckNumber() {
        return luckNumber;
    }

    public void setLuckNumber(Long luckNumber) {
        this.luckNumber = luckNumber;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public DateTime getCancelledIn() {
        return cancelledIn;
    }

    public void setCancelledIn(DateTime cancelledIn) {
        this.cancelledIn = cancelledIn;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    public void setAddressDTO(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
    }

    public PaymentTypeDTO getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentTypeDTO paymentType) {
        this.paymentType = paymentType;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public Boolean getAnticipationHaveDependent() {
        return anticipationHaveDependent;
    }

    public void setAnticipationHaveDependent(Boolean anticipationHaveDependent) {
        this.anticipationHaveDependent = anticipationHaveDependent;
    }
}
