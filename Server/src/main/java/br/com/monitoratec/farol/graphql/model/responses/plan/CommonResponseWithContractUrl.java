package br.com.monitoratec.farol.graphql.model.responses.plan;

import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;

public class CommonResponseWithContractUrl {

    private CommonResponse commonResponse;
    private String url;

    public CommonResponseWithContractUrl(CommonResponse commonResponse, String url) {
        this.commonResponse = commonResponse;
        this.url = url;
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
