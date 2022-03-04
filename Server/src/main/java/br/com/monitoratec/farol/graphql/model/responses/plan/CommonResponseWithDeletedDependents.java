package br.com.monitoratec.farol.graphql.model.responses.plan;

import br.com.monitoratec.farol.graphql.model.dtos.user.ClientDTO;
import br.com.monitoratec.farol.graphql.model.responses.base.BaseCommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.sql.model.user.Client;

import java.util.ArrayList;
import java.util.List;

public class CommonResponseWithDeletedDependents extends BaseCommonResponse {
    private CommonResponse commonResponse;
    private List<ClientDTO> dependents;

    public CommonResponseWithDeletedDependents(CommonResponse commonResponse, List<Client> dependents) {
        this.commonResponse = commonResponse;
        this.dependents = toClient(dependents);
    }

    private List<ClientDTO> toClient(List<Client> clients) {
        List<ClientDTO> clientDTOS = new ArrayList<>(clients.size());
        for (Client client : clients) {
            clientDTOS.add(new ClientDTO(client));
        }
        return clientDTOS;
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public List<ClientDTO> getDependents() {
        return dependents;
    }

    public void setDependents(List<ClientDTO> dependents) {
        this.dependents = dependents;
    }
}
