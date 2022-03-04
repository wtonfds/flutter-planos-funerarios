package br.com.monitoratec.farol.graphql.model.input.user;

import br.com.monitoratec.farol.graphql.model.dtos.user.GenderDTO;

public class ClientDependentUpdateInput {
    private String email;
    private String passwordHash;
    private String name;
    private String telephone;
    private String rg;
    private GenderDTO gender;

    public ClientDependentUpdateInput() {
    }

    public ClientDependentUpdateInput(String email, String passwordHash, String name, String telephone, String rg, GenderDTO gender) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.name = name;
        this.telephone = telephone;
        this.rg = rg;
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public GenderDTO getGender() {
        return gender;
    }

    public void setGender(GenderDTO gender) {
        this.gender = gender;
    }
}
