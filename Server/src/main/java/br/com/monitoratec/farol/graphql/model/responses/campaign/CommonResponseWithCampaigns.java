package br.com.monitoratec.farol.graphql.model.responses.campaign;

import br.com.monitoratec.farol.graphql.model.dtos.campaign.CampaignDTO;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.PaginationInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public class CommonResponseWithCampaigns {
    private final CommonResponse commonResponse;
    private final PaginationInfo paginationInfo;
    private final List<CampaignDTO> campaigns;

    public CommonResponseWithCampaigns(CommonResponse commonResponse, Page<CampaignDTO> page) {
        this.commonResponse = commonResponse;
        this.paginationInfo = new PaginationInfo(page);
        this.campaigns = page.getContent();
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public PaginationInfo getPaginationInfo() {
        return paginationInfo;
    }

    public List<CampaignDTO> getCampaigns() {
        return campaigns;
    }
}
