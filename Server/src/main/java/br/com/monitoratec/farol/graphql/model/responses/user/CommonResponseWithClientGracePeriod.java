package br.com.monitoratec.farol.graphql.model.responses.user;

import br.com.monitoratec.farol.graphql.model.responses.base.BaseCommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;

public class CommonResponseWithClientGracePeriod extends BaseCommonResponse {
    private CommonResponse commonResponse;
    private Boolean status;

    public CommonResponseWithClientGracePeriod(CommonResponse commonResponse, Boolean status) {
        this.commonResponse = commonResponse;
        this.status = status;
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
