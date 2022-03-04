package br.com.monitoratec.farol.sql.model.campaign;

import br.com.monitoratec.farol.sql.config.PostgreSQLEnumType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "frl_campaign")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frl_campaign_sequence")
    @SequenceGenerator(name = "frl_campaign_sequence", allocationSize = 1)
    private Long id;

    private boolean active;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false, columnDefinition = "frl_campaign_recurrence_type")
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private RecurrenceType recurrenceType;

    private int recurrence;

    @Column(nullable = false)
    private LocalTime timeToSend;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false, columnDefinition = "frl_campaign_delivery_mode")
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private DeliveryMode deliveryMode;

    private boolean inactiveClients;

    private boolean birthdayClients;

    private boolean childrenWithAge;

    private boolean expiringContracts;

    private boolean withoutCoupons;

    private boolean withoutTem;

    private boolean withoutFuneralAssistance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public RecurrenceType getRecurrenceType() {
        return recurrenceType;
    }

    public void setRecurrenceType(RecurrenceType recurrenceType) {
        this.recurrenceType = recurrenceType;
    }

    public int getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(int recurrence) {
        this.recurrence = recurrence;
    }

    public LocalTime getTimeToSend() {
        return timeToSend;
    }

    public void setTimeToSend(LocalTime timeToSend) {
        this.timeToSend = timeToSend;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DeliveryMode getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(DeliveryMode deliveryMode) {
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
        Campaign campaign = (Campaign) o;
        return id != null && id.equals(campaign.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "id=" + id +
                ", active=" + active +
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

    public static class Fields {
        public static final String ID = "id";
    }
}
