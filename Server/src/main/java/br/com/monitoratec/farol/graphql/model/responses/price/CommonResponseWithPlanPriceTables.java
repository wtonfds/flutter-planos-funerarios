package br.com.monitoratec.farol.graphql.model.responses.price;

import br.com.monitoratec.farol.graphql.model.dtos.price.PlanPriceTableDTO;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.PaginationInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public class CommonResponseWithPlanPriceTables {
    private final CommonResponse commonResponse;
    private final PaginationInfo paginationInfo;
    private final List<PlanPriceTableDTO> priceTables;

    public CommonResponseWithPlanPriceTables(CommonResponse commonResponse, Page<PlanPriceTableDTO> page) {
        this.commonResponse = commonResponse;
        this.paginationInfo = new PaginationInfo(page);
        this.priceTables = page.getContent();
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public PaginationInfo getPaginationInfo() {
        return paginationInfo;
    }

    public List<PlanPriceTableDTO> getPriceTables() {
        return priceTables;
    }
}
