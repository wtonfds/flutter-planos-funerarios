package br.com.monitoratec.farol.graphql.model.responses.push;

import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;

public class CommonResponseWithPushResult {
    private final CommonResponse commonResponse;
    private final String jsonResponse;

    public CommonResponseWithPushResult(CommonResponse commonResponse, String jsonResponse) {
        this.commonResponse = commonResponse;
        this.jsonResponse = jsonResponse;
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public String getJsonResponse() {
        return jsonResponse;
    }
}
