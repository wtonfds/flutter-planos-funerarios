package br.com.monitoratec.farol.graphql.model.responses.user;

import br.com.monitoratec.farol.graphql.model.dtos.user.ClientDTO;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.sql.model.user.Client;

import java.util.List;
import java.util.stream.Collectors;

public class CommonResponseWithListClients {

    private CommonResponse commonResponse;
    private List<ClientDTO> clients;

    public CommonResponseWithListClients(CommonResponse commonResponse, List<Client> clients) {
        this.commonResponse = commonResponse;
        this.clients = clients.stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public List<ClientDTO> getClients() {
        return clients;
    }

    public void setClients(List<ClientDTO> clients) {
        this.clients = clients;
    }
}
