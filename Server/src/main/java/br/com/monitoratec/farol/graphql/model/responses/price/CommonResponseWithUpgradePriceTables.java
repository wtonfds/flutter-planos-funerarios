package br.com.monitoratec.farol.graphql.model.responses.price;

import br.com.monitoratec.farol.graphql.model.dtos.price.UpgradePriceTableDTO;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.PaginationInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public class CommonResponseWithUpgradePriceTables {
    private final CommonResponse commonResponse;
    private final PaginationInfo paginationInfo;
    private final List<UpgradePriceTableDTO> priceTables;

    public CommonResponseWithUpgradePriceTables(CommonResponse commonResponse, Page<UpgradePriceTableDTO> page) {
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

    public List<UpgradePriceTableDTO> getPriceTables() {
        return priceTables;
    }
}
