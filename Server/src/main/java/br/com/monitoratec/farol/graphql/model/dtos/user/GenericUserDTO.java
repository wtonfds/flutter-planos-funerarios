package br.com.monitoratec.farol.graphql.model.dtos.user;

import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.model.user.FarolUser;
import br.com.monitoratec.farol.sql.model.user.SystemUser;

public class GenericUserDTO {

    private FarolUserDTO farolUser;
    private ClientDTO client;

    public GenericUserDTO(SystemUser systemUser) {
        if(systemUser instanceof Client) {
            this.client = new ClientDTO((Client) systemUser);
        }

        if(systemUser instanceof FarolUser){
            this.farolUser = new FarolUserDTO((FarolUser) systemUser);
        }

    }

    public ClientDTO getClient() { return client; }

    public void setClient (ClientDTO client){
        this.client = client;
    }

    public FarolUserDTO getFarolUser() {
        return farolUser;
    }

    public void setFarolUser(FarolUserDTO farolUser) {
        this.farolUser = farolUser;
    }
}
