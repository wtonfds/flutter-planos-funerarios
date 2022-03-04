package br.com.monitoratec.farol.graphql.model.responses.user;

import br.com.monitoratec.farol.graphql.model.dtos.user.ClientDTO;
import br.com.monitoratec.farol.graphql.model.responses.base.BaseCommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.sql.model.user.Client;

import java.util.ArrayList;
import java.util.List;

public class CommonResponseWithClientDependentsInformation extends BaseCommonResponse {
    private CommonResponse commonResponse;
    private List<ClientDTO> dependents;

    public CommonResponseWithClientDependentsInformation(CommonResponse commonResponse, List<Client> dependents) {
        this.commonResponse = commonResponse;
        this.dependents = new ArrayList<>();
        for (Client dependent : dependents) {
            this.dependents.add(new ClientDTO(dependent));
        }
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
