package br.com.monitoratec.farol.graphql.model.dtos.user;

import br.com.monitoratec.farol.graphql.customTypes.Date;
import br.com.monitoratec.farol.graphql.customTypes.DateTime;
import br.com.monitoratec.farol.graphql.customTypes.Email;
import br.com.monitoratec.farol.graphql.model.dtos.document.DocumentClientDTO;
import br.com.monitoratec.farol.sql.model.user.Client;

import java.util.List;
import java.util.stream.Collectors;

public class ClientDTO {
    // SystemUser
    private Long id;
    private Email email;

    // Myself
    private String name;

    private String cpf;
    private String rg;
    private Date birthday;
    private String telephone;
    private ClientDTO holder;
    private Boolean alive;
    private DateTime createdAt;
    private Boolean temporaryPassword;
    private ClientTypeDTO clientType;
    private Boolean active;
    private Boolean deleted;
    private Boolean authorized;
    private String oneSignalPlayerId;
    private Date gracePeriod;
    private String luckNumber;
    private List<DependentDTO> dependents;
    private List<DocumentClientDTO> documents;
    private GenderDTO gender;

    public ClientDTO(Client client) {
        this.id = client.getId();
        if (client.getEmail() != null) {
            this.email = Email.fromString(client.getEmail());
        }
        this.name = client.getName();
        if (client.getCPF() != null) {
            this.cpf = client.getCPF();
        }
        if (client.getRG() != null) {
            this.rg = client.getRG();
        }
        if (client.getBirthDay() != null) {
            this.birthday = new Date(client.getBirthDay());
        }
        this.telephone = client.getTelephone();
        if (client.getHolder() != null) {
            this.holder = new ClientDTO(client.getHolder());
        }
        this.alive = client.isAlive();
        this.createdAt = new DateTime(client.getCreatedAt());
        this.temporaryPassword = client.isTemporaryPassword();
        this.clientType = ClientTypeDTO.valueOf(client.getClientType());
        this.active = client.isActive();
        this.deleted = client.isDeleted();
        this.oneSignalPlayerId = client.getOneSignalPlayerId();
        if (client.getGracePeriod() != null) {
            this.gracePeriod = new Date(client.getGracePeriod());
        }
        this.authorized = client.getAuthorized();
        if (client.getDependents() != null) {
            this.dependents = client.getDependents().stream().map(DependentDTO::new).collect(Collectors.toList());
        }
        if (client.getDocumentsClients() != null) {
            this.documents = client.getDocumentsClients().stream().map(DocumentClientDTO::new).collect(Collectors.toList());
        }
        if (client.getGender() != null) {
            this.gender = GenderDTO.valueOf(client.getGender());
        }
        if (client.getLuckNumber() != null) {
            this.luckNumber = client.getLuckNumber();
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

    public ClientDTO getHolder() {
        return holder;
    }

    public void setHolder(ClientDTO holder) {
        this.holder = holder;
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

    public Date getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(Date gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public Boolean getAuthorized() {
        return authorized;
    }

    public void setAuthorized(Boolean authorized) {
        this.authorized = authorized;
    }

    public List<DependentDTO> getDependents() {
        return dependents;
    }

    public void setDependents(List<DependentDTO> dependents) {
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

    public String getLuckNumber() {
        return luckNumber;
    }

    public void setLuckNumber(String luckNumber) {
        this.luckNumber = luckNumber;
    }
}
