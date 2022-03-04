package br.com.monitoratec.farol.sql.interfaces.user;

public interface LoggableViaCPF{

    void setPasswordHash(String passwordHash);
    String getPasswordHash();

}
