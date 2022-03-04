package br.com.monitoratec.farol.graphql.model.responses.plan;

import br.com.monitoratec.farol.graphql.model.dtos.plan.PlanDTO;
import br.com.monitoratec.farol.graphql.model.dtos.user.ClientDTO;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.sql.model.plan.Plan;
import br.com.monitoratec.farol.sql.model.user.Client;

public class CommonResponseWithPlanSubscriptionInformation {
    private CommonResponse commonResponse;
    private PlanDTO planDTO;
    private ClientDTO clientDTO;

    public CommonResponseWithPlanSubscriptionInformation(CommonResponse commonResponse, Plan plan, Client client) {
        this.commonResponse = commonResponse;
        this.planDTO = new PlanDTO(plan);
        this.clientDTO = new ClientDTO(client);
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

    public ClientDTO getClientDTO() {
        return clientDTO;
    }

    public void setClientDTO(ClientDTO clientDTO) {
        this.clientDTO = clientDTO;
    }
}
