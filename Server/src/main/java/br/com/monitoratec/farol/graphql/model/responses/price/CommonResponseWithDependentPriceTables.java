package br.com.monitoratec.farol.graphql.model.responses.price;

import br.com.monitoratec.farol.graphql.model.dtos.price.DependentPriceTableDTO;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.PaginationInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public class CommonResponseWithDependentPriceTables {
    private final CommonResponse commonResponse;
    private final PaginationInfo paginationInfo;
    private final List<DependentPriceTableDTO> priceTables;

    public CommonResponseWithDependentPriceTables(CommonResponse commonResponse, Page<DependentPriceTableDTO> page) {
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

    public List<DependentPriceTableDTO> getPriceTables() {
        return priceTables;
    }
}
