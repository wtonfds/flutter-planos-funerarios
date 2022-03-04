package br.com.monitoratec.farol.sql.model.user;

import br.com.monitoratec.farol.graphql.model.input.document.DocumentsInput;
import br.com.monitoratec.farol.sql.interfaces.user.LoggableViaCPF;
import br.com.monitoratec.farol.sql.model.plan.SubscribedPlan;
import br.com.monitoratec.farol.sql.model.push.Preferences;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "frl_user_client")
public class Client extends PhysicalUser implements LoggableViaCPF {

    private LocalDate birthDay;

    private String RG;

    @Transient
    private List<DocumentsInput> documentsInput;

    @ManyToOne
    private Client holder;

    @OneToMany(mappedBy = "holder", fetch = FetchType.EAGER)
    private List<Client> dependents;

    @Column
    private boolean alive;

    @OneToMany(mappedBy = "beneficiary")
    private List<SubscribedPlan> subscribedPlanList;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<DocumentsClient> documentsClients;

    @Column
    private String clientType;

    @Column
    private LocalDateTime createdAt;

    @Column
    private boolean deleted;

    @Column
    private String oneSignalPlayerId;

    @Column
    private LocalDate gracePeriod;

    @Column(length = 16)
    private String authCode;

    @Column
    private Boolean isAuthorized;

    @Column
    private String smsCode;

    @Column(nullable = false)
    private String gender;

    // one signal preferences
    @ManyToMany
    private List<Preferences> oneSignalPreferenceList;

    @Column
    private String luckNumber;

    @Column
    private String temUserToken;

    @Column
    private String temCardNumber;

    @Column
    private Boolean addedAfterAnticipation;

    // Login via CPF
    @Override
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public String getPasswordHash() {
        return this.passwordHash;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public String getRG() {
        return RG;
    }

    public void setRG(String RG) {
        this.RG = RG;
    }

    public Client getHolder() {
        return holder;
    }

    public void setHolder(Client holder) {
        this.holder = holder;
    }

    public List<Client> getDependents() {
        return dependents;
    }

    public void setDependents(List<Client> dependents) {
        this.dependents = dependents;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean is_alive) {
        this.alive = is_alive;
    }

    public List<SubscribedPlan> getSubscribedPlanList() {
        return subscribedPlanList;
    }

    public void setSubscribedPlanList(List<SubscribedPlan> subscribedPlanList) {
        this.subscribedPlanList = subscribedPlanList;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getOneSignalPlayerId() {
        return oneSignalPlayerId;
    }

    public void setOneSignalPlayerId(String oneSignalPlayerId) {
        this.oneSignalPlayerId = oneSignalPlayerId;
    }

    public LocalDate getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(LocalDate gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Boolean getAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(Boolean authorized) {
        isAuthorized = authorized;
    }

    public List<DocumentsClient> getDocumentsClients() {
        return documentsClients;
    }

    public void setDocumentsClients(List<DocumentsClient> documentsClients) {
        this.documentsClients = documentsClients;
    }

    public List<DocumentsInput> getDocumentsInput() {
        return documentsInput;
    }

    public void setDocumentsInput(List<DocumentsInput> documentsInput) {
        this.documentsInput = documentsInput;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTemUserToken() {
        return temUserToken;
    }

    public void setTemUserToken(String temUserToken) {
        this.temUserToken = temUserToken;
    }

    public String getTemCardNumber() {
        return temCardNumber;
    }

    public void setTemCardNumber(String temCardNumber) {
        this.temCardNumber = temCardNumber;
    }

    public List<Preferences> getOneSignalPreferenceList() {
        return oneSignalPreferenceList;
    }

    public void setOneSignalPreferenceList(List<Preferences> oneSignalPreferenceList) {
        this.oneSignalPreferenceList = oneSignalPreferenceList;
    }

    public String getLuckNumber() {
        return luckNumber;
    }

    public void setLuckNumber(String luckNumber) {
        this.luckNumber = luckNumber;
    }

    public Boolean getAddedAfterAnticipation() {
        return addedAfterAnticipation;
    }

    public void setAddedAfterAnticipation(Boolean addedAfterAnticipation) {
        this.addedAfterAnticipation = addedAfterAnticipation;
    }
}
