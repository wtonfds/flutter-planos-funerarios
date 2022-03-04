package br.com.monitoratec.farol.graphql.model.responses.user;

import br.com.monitoratec.farol.graphql.model.dtos.user.ClientDTO;
import br.com.monitoratec.farol.graphql.model.responses.base.BaseCommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.sql.model.user.Client;

public class CommonResponseWithClientInformation extends BaseCommonResponse {
    private CommonResponse commonResponse;
    private ClientDTO genericClient;

    public CommonResponseWithClientInformation() {
    }

    public CommonResponseWithClientInformation(CommonResponse commonResponse, Client client) {
        this.commonResponse = commonResponse;
        this.genericClient = new ClientDTO(client);
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public ClientDTO getGenericClient() {
        return genericClient;
    }

    public void setGenericClient(ClientDTO genericClient) {
        this.genericClient = genericClient;
    }
}
