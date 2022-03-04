package br.com.monitoratec.farol.graphql.customTypes;

import br.com.monitoratec.farol.utils.data.EmailUtils;
import graphql.schema.CoercingParseLiteralException;

public class Email {
    private String emailString;

    private Email(String emailString) {
        this.emailString = emailString;
    }

    private Email() {
    }

    public String stringValue() {
        return this.emailString;
    }

    public static Email fromString(String emailString) throws CoercingParseLiteralException {
        if (EmailUtils.isEmailValid(emailString)) {
            return new Email(emailString);
        } else {
            throw new CoercingParseLiteralException("Invalid e-mail format: " + emailString);
        }
    }

    public String getEmailString() {
        return emailString;
    }

    public void setEmailString(String emailString) {
        this.emailString = emailString;
    }
}
