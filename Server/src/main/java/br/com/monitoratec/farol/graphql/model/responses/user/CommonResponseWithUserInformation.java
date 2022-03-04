package br.com.monitoratec.farol.graphql.model.responses.user;

import br.com.monitoratec.farol.graphql.model.dtos.user.GenericUserDTO;
import br.com.monitoratec.farol.graphql.model.responses.base.BaseCommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.sql.model.user.SystemUser;

public class CommonResponseWithUserInformation extends BaseCommonResponse {
    private CommonResponse commonResponse;
    private GenericUserDTO genericUser;

    public CommonResponseWithUserInformation() {
    }

    public CommonResponseWithUserInformation(CommonResponse commonResponse, SystemUser systemUser) {
        this.commonResponse = commonResponse;
        this.genericUser = new GenericUserDTO(systemUser);
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public GenericUserDTO getGenericUser() {
        return genericUser;
    }

    public void setGenericUser(GenericUserDTO genericUser) {
        this.genericUser = genericUser;
    }
}
