package br.com.monitoratec.farol.graphql.model.input.user;

import br.com.monitoratec.farol.graphql.model.dtos.user.GenderDTO;
import br.com.monitoratec.farol.graphql.model.input.document.DocumentsInput;

import java.util.List;

public class ExtraDependentInput {
    private Long id;
    private String cpf;
    private String rg;
    private String telephone;
    private GenderDTO gender;
    private List<DocumentsInput> documents;

    public ExtraDependentInput(Long id, String cpf, String rg, String telephone, GenderDTO gender, List<DocumentsInput> documents) {
        this.id = id;
        this.cpf = cpf;
        this.rg = rg;
        this.telephone = telephone;
        this.gender = gender;
        this.documents = documents;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public GenderDTO getGender() {
        return gender;
    }

    public void setGender(GenderDTO gender) {
        this.gender = gender;
    }

    public List<DocumentsInput> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentsInput> documents) {
        this.documents = documents;
    }
}
