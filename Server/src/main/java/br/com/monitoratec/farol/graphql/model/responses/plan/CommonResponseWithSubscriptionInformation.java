package br.com.monitoratec.farol.graphql.model.responses.plan;

import br.com.monitoratec.farol.graphql.model.dtos.plan.SubscriptionPlanDTO;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.sql.model.plan.SubscribedPlan;

public class CommonResponseWithSubscriptionInformation {

    private CommonResponse commonResponse;
    private SubscriptionPlanDTO subscriptionPlanDTO;

    public CommonResponseWithSubscriptionInformation(CommonResponse commonResponse, SubscribedPlan subscribedPlan) {
        this.commonResponse = commonResponse;
        this.subscriptionPlanDTO = new SubscriptionPlanDTO(subscribedPlan);
    }
    public CommonResponseWithSubscriptionInformation(CommonResponse commonResponse, SubscribedPlan subscribedPlan, Double totalValue) {
        this.commonResponse = commonResponse;
        this.subscriptionPlanDTO = new SubscriptionPlanDTO(subscribedPlan, totalValue);
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public SubscriptionPlanDTO getSubscriptionPlanDTO() {
        return subscriptionPlanDTO;
    }

    public void setSubscriptionPlanDTO(SubscriptionPlanDTO subscriptionPlanDTO) {
        this.subscriptionPlanDTO = subscriptionPlanDTO;
    }
}
