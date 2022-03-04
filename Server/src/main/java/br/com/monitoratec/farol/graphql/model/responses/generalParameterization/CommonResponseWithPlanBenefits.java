package br.com.monitoratec.farol.graphql.model.responses.generalParameterization;

import br.com.monitoratec.farol.graphql.model.responses.base.BaseCommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;

public class CommonResponseWithPlanBenefits extends BaseCommonResponse {
    private CommonResponse commonResponse;
    private String planBenefits;

    public CommonResponseWithPlanBenefits(CommonResponse commonResponse, String planBenefits) {
        this.commonResponse = commonResponse;
        this.planBenefits = planBenefits;
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public String getPlanBenefits() {
        return planBenefits;
    }

    public void setPlanBenefits(String planBenefits) {
        this.planBenefits = planBenefits;
    }
}
