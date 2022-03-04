package br.com.monitoratec.farol.graphql.model.responses.user;

import br.com.monitoratec.farol.graphql.model.dtos.user.FarolUserDTO;
import br.com.monitoratec.farol.graphql.model.responses.base.BaseCommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.sql.model.user.FarolUser;

public class CommonResponseWithFarolUserInformation extends BaseCommonResponse {
    private CommonResponse commonResponse;
    private FarolUserDTO genericFarolUser;

    public CommonResponseWithFarolUserInformation() {
    }

    public CommonResponseWithFarolUserInformation(CommonResponse commonResponse, FarolUser farolUser) {
        this.commonResponse = commonResponse;
        this.genericFarolUser = new FarolUserDTO(farolUser);
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public FarolUserDTO getGenericFarolUser() {
        return genericFarolUser;
    }

    public void setGenericFarolUser(FarolUserDTO genericFarolUser) {
        this.genericFarolUser = genericFarolUser;
    }
}
