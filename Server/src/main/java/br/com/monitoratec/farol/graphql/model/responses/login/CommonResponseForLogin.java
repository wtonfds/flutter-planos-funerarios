package br.com.monitoratec.farol.graphql.model.responses.login;

import br.com.monitoratec.farol.graphql.model.dtos.user.GenericUserDTO;
import br.com.monitoratec.farol.graphql.model.responses.base.BaseCommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.sql.model.user.SystemUser;

public class CommonResponseForLogin extends BaseCommonResponse {
    private CommonResponse commonResponse;
    private String authToken;
    private GenericUserDTO genericUser;

    public CommonResponseForLogin() {
    }

    public CommonResponseForLogin(CommonResponse commonResponse, String authToken, SystemUser systemUser) {
        this.commonResponse = commonResponse;
        this.authToken = authToken;
        this.genericUser = new GenericUserDTO(systemUser);
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public GenericUserDTO getGenericUser() {
        return genericUser;
    }

    public void setGenericUser(GenericUserDTO genericUser) {
        this.genericUser = genericUser;
    }
}
