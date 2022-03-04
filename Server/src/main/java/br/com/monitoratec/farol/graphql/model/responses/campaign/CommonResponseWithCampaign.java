package br.com.monitoratec.farol.graphql.model.responses.campaign;

import br.com.monitoratec.farol.graphql.model.dtos.campaign.CampaignDTO;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;

public class CommonResponseWithCampaign {
    private final CommonResponse commonResponse;
    private final CampaignDTO campaign;

    public CommonResponseWithCampaign(CommonResponse commonResponse, CampaignDTO campaign) {
        this.commonResponse = commonResponse;
        this.campaign = campaign;
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public CampaignDTO getCampaign() {
        return campaign;
    }
}
