package br.com.monitoratec.farol.sql.interfaces.user;

public interface LoggableViaEmail {

    void setPasswordHash(String passwordHash);
    String getPasswordHash();

}
