package br.com.monitoratec.farol.graphql.model.dtos.user;

import br.com.monitoratec.farol.graphql.customTypes.Date;
import br.com.monitoratec.farol.graphql.customTypes.DateTime;
import br.com.monitoratec.farol.graphql.customTypes.Email;
import br.com.monitoratec.farol.graphql.model.dtos.document.DocumentClientDTO;
import br.com.monitoratec.farol.sql.model.user.Client;

import java.util.List;
import java.util.stream.Collectors;

public class DependentDTO {
    // SystemUser
    private Long id;
    private Email email;

    // Myself
    private String name;

    private String cpf;
    private String rg;
    private Date birthday;
    private String telephone;
    private Boolean alive;
    private DateTime createdAt;
    private Boolean temporaryPassword;
    private ClientTypeDTO clientType;
    private Boolean active;
    private Boolean deleted;
    private String oneSignalPlayerId;
    private List<ClientDTO> dependents;
    private List<DocumentClientDTO> documents;
    private GenderDTO gender;

    public DependentDTO(Client dependent) {
        this.id = dependent.getId();
        if (dependent.getEmail() != null) {
            this.email = Email.fromString(dependent.getEmail());
        }
        this.name = dependent.getName();
        this.cpf = dependent.getCPF();
        this.rg = dependent.getRG();
        if (dependent.getBirthDay() != null) {
            this.birthday = new Date(dependent.getBirthDay());
        }
        this.telephone = dependent.getTelephone();
        this.alive = dependent.isAlive();
        this.createdAt = new DateTime(dependent.getCreatedAt());
        this.temporaryPassword = dependent.isTemporaryPassword();
        this.clientType = ClientTypeDTO.valueOf(dependent.getClientType());
        this.active = dependent.isActive();
        this.deleted = dependent.isDeleted();
        this.oneSignalPlayerId = dependent.getOneSignalPlayerId();
        if (dependent.getDocumentsClients() != null) {
            this.documents = dependent.getDocumentsClients().stream().map(DocumentClientDTO::new).collect(Collectors.toList());
        }
        if (dependent.getGender() != null) {
            this.gender = GenderDTO.valueOf(dependent.getGender());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Boolean getAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getTemporaryPassword() {
        return temporaryPassword;
    }

    public void setTemporaryPassword(Boolean temporaryPassword) {
        this.temporaryPassword = temporaryPassword;
    }

    public ClientTypeDTO getClientType() {
        return clientType;
    }

    public void setClientType(ClientTypeDTO clientType) {
        this.clientType = clientType;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getOneSignalPlayerId() {
        return oneSignalPlayerId;
    }

    public void setOneSignalPlayerId(String oneSignalPlayerId) {
        this.oneSignalPlayerId = oneSignalPlayerId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public List<ClientDTO> getDependents() {
        return dependents;
    }

    public void setDependents(List<ClientDTO> dependents) {
        this.dependents = dependents;
    }

    public List<DocumentClientDTO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentClientDTO> documents) {
        this.documents = documents;
    }

    public GenderDTO getGender() {
        return gender;
    }

    public void setGender(GenderDTO gender) {
        this.gender = gender;
    }
}
