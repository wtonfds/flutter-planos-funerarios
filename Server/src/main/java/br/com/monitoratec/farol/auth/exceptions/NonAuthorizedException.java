package br.com.monitoratec.farol.auth.exceptions;

public class NonAuthorizedException extends Exception {

    public NonAuthorizedException() {
        super("Non authorized");
    }

}
