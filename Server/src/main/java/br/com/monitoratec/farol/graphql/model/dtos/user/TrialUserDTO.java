package br.com.monitoratec.farol.graphql.model.dtos.user;

import br.com.monitoratec.farol.graphql.customTypes.Email;
import br.com.monitoratec.farol.sql.model.user.TrialUser;

public class TrialUserDTO {
    private String name;
    private Email email;

    public TrialUserDTO(TrialUser trialUser) {
        this.name = trialUser.getName();
        this.email = Email.fromString(trialUser.getEmail());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }
}
