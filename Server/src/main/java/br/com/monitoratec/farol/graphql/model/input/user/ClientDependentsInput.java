package br.com.monitoratec.farol.graphql.model.input.user;

import br.com.monitoratec.farol.graphql.customTypes.Date;
import br.com.monitoratec.farol.graphql.model.dtos.user.GenderDTO;
import br.com.monitoratec.farol.graphql.model.dtos.user.ClientTypeDTO;
import br.com.monitoratec.farol.graphql.model.input.document.DocumentsInput;

import java.util.List;

public class ClientDependentsInput {
    private String name;
    private String cpf;
    private String rg;
    private Date birthday;
    private String telephone;
    private ClientTypeDTO clientType;
    private GenderDTO gender;
    private List<DocumentsInput> documentsInput;

    public ClientDependentsInput() {

    }

    public ClientDependentsInput(String name, String cpf, String rg, Date birthday, String telephone, ClientTypeDTO clientType,
                                 List<DocumentsInput> documentsInput, GenderDTO gender) {
        this.name = name;
        this.cpf = cpf;
        this.rg = rg;
        this.birthday = birthday;
        this.telephone = telephone;
        this.clientType = clientType;
        this.documentsInput = documentsInput;
        this.gender = gender;
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

    public ClientTypeDTO getClientType() {
        return clientType;
    }

    public void setClientType(ClientTypeDTO clientType) {
        this.clientType = clientType;
    }

    public List<DocumentsInput> getDocumentsInput() {
        return documentsInput;
    }

    public void setDocumentsInput(List<DocumentsInput> documentsInput) {
        this.documentsInput = documentsInput;
    }

    public GenderDTO getGender() {
        return gender;
    }

    public void setGender(GenderDTO gender) {
        this.gender = gender;
    }
}
