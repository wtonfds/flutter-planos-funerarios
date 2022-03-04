package br.com.monitoratec.farol.auth.model;

import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.model.user.FarolUser;
import br.com.monitoratec.farol.sql.model.user.SystemUser;

public enum AccessingEntity {
    CLIENT, FAROL_USER;

    public static AccessingEntity fromSystemUser(SystemUser systemUser) {
        if (systemUser instanceof Client) {
            return CLIENT;
        } else if (systemUser instanceof FarolUser) {
            return FAROL_USER;
        }
        return null;
    }
}
