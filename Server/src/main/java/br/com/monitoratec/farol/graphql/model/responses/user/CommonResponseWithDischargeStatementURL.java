package br.com.monitoratec.farol.graphql.model.responses.user;

import br.com.monitoratec.farol.graphql.model.responses.base.BaseCommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;

public class CommonResponseWithDischargeStatementURL extends BaseCommonResponse {
    private CommonResponse commonResponse;
    private String dischargeStatementURL;

    public CommonResponseWithDischargeStatementURL(CommonResponse commonResponse, String dischargeStatementURL) {
        this.commonResponse = commonResponse;
        this.dischargeStatementURL = dischargeStatementURL;
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public String getDischargeStatementURL() {
        return dischargeStatementURL;
    }

    public void setDischargeStatementURL(String dischargeStatementURL) {
        this.dischargeStatementURL = dischargeStatementURL;
    }
}
