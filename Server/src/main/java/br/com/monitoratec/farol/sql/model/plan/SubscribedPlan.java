package br.com.monitoratec.farol.sql.model.plan;

import br.com.monitoratec.farol.sql.model.location.Address;
import br.com.monitoratec.farol.sql.model.user.Client;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "frl_subscribed_plan")
public class SubscribedPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frl_plan_sequence")
    @SequenceGenerator(name = "frl_plan_sequence", allocationSize = 1)
    private long id;

    @Column(length = 511, nullable = false)
    private String adhesionContract;

    @Column(nullable = false)
    private LocalDateTime subscribedIn;

    @Column(nullable = false)
    private double value;

    @Column(nullable = false)
    private long clientNumber;

    @Column(nullable = false)
    private LocalDate gracePeriod;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = Client.class)
    private Client beneficiary;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = Plan.class)
    private Plan plan;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private long luckNumber;

    @Column(nullable = false)
    private boolean isDefault;

    @Column(nullable = false)
    private LocalDateTime lastPayment;

    @OneToOne
    private Address address;

    @Column
    private LocalDateTime cancelledIn;

    @Column
    private String paymentType;

    @Column
    private Integer paymentDay;

    @Column
    private Boolean waitingForLastPaymentDate;

    @Column
    private LocalDate validUntil;

    private LocalDateTime anticipationLastPayment;

    private Boolean anticipationHaveDependent;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAdhesionContract() {
        return adhesionContract;
    }

    public void setAdhesionContract(String adhesionContract) {
        this.adhesionContract = adhesionContract;
    }

    public LocalDateTime getSubscribedIn() {
        return subscribedIn;
    }

    public void setSubscribedIn(LocalDateTime subscribedIn) {
        this.subscribedIn = subscribedIn;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public long getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(long clientNumber) {
        this.clientNumber = clientNumber;
    }

    public LocalDate getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(LocalDate gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public Client getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Client beneficiary) {
        this.beneficiary = beneficiary;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getLuckNumber() {
        return luckNumber;
    }

    public void setLuckNumber(long luckNumber) {
        this.luckNumber = luckNumber;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public LocalDateTime getCancelledIn() {
        return cancelledIn;
    }

    public void setCancelledIn(LocalDateTime cancelledIn) {
        this.cancelledIn = cancelledIn;
    }

    public LocalDateTime getLastPayment() {
        return lastPayment;
    }

    public void setLastPayment(LocalDateTime lastPayment) {
        this.lastPayment = lastPayment;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getPaymentDay() {
        return paymentDay;
    }

    public void setPaymentDay(Integer paymentSlipDate) {
        this.paymentDay = paymentSlipDate;
    }

    public Boolean getWaitingForLastPaymentDate() {
        return waitingForLastPaymentDate;
    }

    public void setWaitingForLastPaymentDate(Boolean waitingForLastPaymentDate) {
        this.waitingForLastPaymentDate = waitingForLastPaymentDate;
    }

    public LocalDateTime getAnticipationLastPayment() {
        return anticipationLastPayment;
    }

    public void setAnticipationLastPayment(LocalDateTime anticipationLastPayment) {
        this.anticipationLastPayment = anticipationLastPayment;
    }

    public Boolean getAnticipationHaveDependent() {
        return anticipationHaveDependent;
    }

    public void setAnticipationHaveDependent(Boolean anticipationHaveDependent) {
        this.anticipationHaveDependent = anticipationHaveDependent;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }
}
