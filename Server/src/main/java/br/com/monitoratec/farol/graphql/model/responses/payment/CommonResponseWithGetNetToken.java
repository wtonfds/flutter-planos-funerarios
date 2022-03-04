package br.com.monitoratec.farol.graphql.model.responses.payment;

import br.com.monitoratec.farol.graphql.model.responses.base.BaseCommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;

public class CommonResponseWithGetNetToken extends BaseCommonResponse {

    private CommonResponse commonResponse;
    private String getNetToken;


    public CommonResponseWithGetNetToken(CommonResponse commonResponse, String getNetToken) {
        this.commonResponse = commonResponse;
        this.getNetToken = getNetToken;
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public String getGetNetToken() {
        return getNetToken;
    }

    public void setGetNetToken(String getNetToken) {
        this.getNetToken = getNetToken;
    }
}
