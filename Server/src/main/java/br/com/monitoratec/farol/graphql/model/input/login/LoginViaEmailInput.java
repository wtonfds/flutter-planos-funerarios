package br.com.monitoratec.farol.graphql.model.input.login;

import br.com.monitoratec.farol.graphql.customTypes.Email;

public class LoginViaEmailInput {
    private Email email;
    private String password;

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
