package br.com.monitoratec.farol.graphql.model.responses.plan;

import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;

public class CommonResponseWithAvailableAnticipationMonths {
    private Long months;
    private CommonResponse commonResponse;

    public CommonResponseWithAvailableAnticipationMonths(Long months, CommonResponse commonResponse) {
        this.months = months;
        this.commonResponse = commonResponse;
    }

    public Long getMonths() {
        return months;
    }

    public void setMonths(Long months) {
        this.months = months;
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }
}
