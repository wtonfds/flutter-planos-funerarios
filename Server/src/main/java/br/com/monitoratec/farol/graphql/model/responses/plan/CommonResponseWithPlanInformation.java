package br.com.monitoratec.farol.graphql.model.responses.plan;

import br.com.monitoratec.farol.graphql.model.dtos.plan.PlanDTO;
import br.com.monitoratec.farol.graphql.model.responses.base.BaseCommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.sql.model.plan.Plan;

public class CommonResponseWithPlanInformation extends BaseCommonResponse {
    private CommonResponse commonResponse;
    private PlanDTO planDTO;

    public CommonResponseWithPlanInformation(CommonResponse commonResponse, Plan plan) {
        this.commonResponse = commonResponse;
        this.planDTO = new PlanDTO(plan);
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public PlanDTO getPlanDTO() {
        return planDTO;
    }

    public void setPlanDTO(PlanDTO planDTO) {
        this.planDTO = planDTO;
    }
}
