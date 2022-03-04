package br.com.monitoratec.farol.graphql.model.dtos.campaign;

import br.com.monitoratec.farol.graphql.customTypes.Date;
import br.com.monitoratec.farol.graphql.customTypes.Time;

import java.util.Objects;

public class CampaignDTO {
    private long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private RecurrenceTypeDTO recurrenceType;
    private int recurrence;
    private Time timeToSend;
    private String message;
    private DeliveryModeDTO deliveryMode;
    private boolean inactiveClients;
    private boolean birthdayClients;
    private boolean childrenWithAge;
    private boolean expiringContracts;
    private boolean withoutCoupons;
    private boolean withoutTem;
    private boolean withoutFuneralAssistance;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public RecurrenceTypeDTO getRecurrenceType() {
        return recurrenceType;
    }

    public void setRecurrenceType(RecurrenceTypeDTO recurrenceType) {
        this.recurrenceType = recurrenceType;
    }

    public int getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(int recurrence) {
        this.recurrence = recurrence;
    }

    public Time getTimeToSend() {
        return timeToSend;
    }

    public void setTimeToSend(Time timeToSend) {
        this.timeToSend = timeToSend;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DeliveryModeDTO getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(DeliveryModeDTO deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public boolean isInactiveClients() {
        return inactiveClients;
    }

    public void setInactiveClients(boolean inactiveClients) {
        this.inactiveClients = inactiveClients;
    }

    public boolean isBirthdayClients() {
        return birthdayClients;
    }

    public void setBirthdayClients(boolean birthdayClients) {
        this.birthdayClients = birthdayClients;
    }

    public boolean isChildrenWithAge() {
        return childrenWithAge;
    }

    public void setChildrenWithAge(boolean childrenWithAge) {
        this.childrenWithAge = childrenWithAge;
    }

    public boolean isExpiringContracts() {
        return expiringContracts;
    }

    public void setExpiringContracts(boolean expiringContracts) {
        this.expiringContracts = expiringContracts;
    }

    public boolean isWithoutCoupons() {
        return withoutCoupons;
    }

    public void setWithoutCoupons(boolean withoutCoupons) {
        this.withoutCoupons = withoutCoupons;
    }

    public boolean isWithoutTem() {
        return withoutTem;
    }

    public void setWithoutTem(boolean withoutTem) {
        this.withoutTem = withoutTem;
    }

    public boolean isWithoutFuneralAssistance() {
        return withoutFuneralAssistance;
    }

    public void setWithoutFuneralAssistance(boolean withoutFuneralAssistance) {
        this.withoutFuneralAssistance = withoutFuneralAssistance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CampaignDTO that = (CampaignDTO) o;
        return id == that.id &&
                recurrence == that.recurrence &&
                inactiveClients == that.inactiveClients &&
                birthdayClients == that.birthdayClients &&
                childrenWithAge == that.childrenWithAge &&
                expiringContracts == that.expiringContracts &&
                withoutCoupons == that.withoutCoupons &&
                withoutTem == that.withoutTem &&
                withoutFuneralAssistance == that.withoutFuneralAssistance &&
                Objects.equals(name, that.name) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                recurrenceType == that.recurrenceType &&
                Objects.equals(timeToSend, that.timeToSend) &&
                Objects.equals(message, that.message) &&
                deliveryMode == that.deliveryMode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startDate, endDate, recurrenceType, recurrence, timeToSend, message, deliveryMode,
                inactiveClients, birthdayClients, childrenWithAge, expiringContracts, withoutCoupons, withoutTem, withoutFuneralAssistance);
    }

    @Override
    public String toString() {
        return "CampaignDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", recurrenceType=" + recurrenceType +
                ", recurrence=" + recurrence +
                ", timeToSend=" + timeToSend +
                ", message='" + message + '\'' +
                ", deliveryMode=" + deliveryMode +
                ", inactiveClients=" + inactiveClients +
                ", birthdayClients=" + birthdayClients +
                ", childrenWithAge=" + childrenWithAge +
                ", expiringContracts=" + expiringContracts +
                ", withoutCoupons=" + withoutCoupons +
                ", withoutTem=" + withoutTem +
                ", withoutFuneralAssistance=" + withoutFuneralAssistance +
                '}';
    }
}
