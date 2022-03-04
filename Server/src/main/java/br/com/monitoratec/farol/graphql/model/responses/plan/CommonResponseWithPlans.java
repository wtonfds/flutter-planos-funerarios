package br.com.monitoratec.farol.graphql.model.responses.plan;

import br.com.monitoratec.farol.graphql.model.dtos.plan.PlanDTO;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.PaginationInfo;
import br.com.monitoratec.farol.sql.model.plan.Plan;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class CommonResponseWithPlans {

    private CommonResponse commonResponse;
    private PaginationInfo paginationInfo;
    private List<PlanDTO> plansList;

    public CommonResponseWithPlans(CommonResponse commonResponse, Page<Plan> plansPage) {
        this.commonResponse = commonResponse;
        this.paginationInfo = new PaginationInfo(plansPage);
        this.plansList = plansPage.getContent().stream().map(PlanDTO::new).collect(Collectors.toList());
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public PaginationInfo getPaginationInfo() {
        return paginationInfo;
    }

    public void setPaginationInfo(PaginationInfo paginationInfo) {
        this.paginationInfo = paginationInfo;
    }

    public List<PlanDTO> getPlansList() {
        return plansList;
    }

    public void setPlansList(List<PlanDTO> plansList) {
        this.plansList = plansList;
    }
}
