package br.com.monitoratec.farol.graphql.model.responses.common;

import br.com.monitoratec.farol.graphql.model.responses.base.BaseCommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;

public class JustCommonResponse extends BaseCommonResponse {
    private final CommonResponse commonResponse;

    public JustCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }
}
