package br.com.monitoratec.farol.graphql.model.responses.user;

import br.com.monitoratec.farol.graphql.model.responses.base.BaseCommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;

public class CommonResponseWithSuccessUpdateDocuments extends BaseCommonResponse {
    private CommonResponse commonResponse;

    public CommonResponseWithSuccessUpdateDocuments(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }
}
