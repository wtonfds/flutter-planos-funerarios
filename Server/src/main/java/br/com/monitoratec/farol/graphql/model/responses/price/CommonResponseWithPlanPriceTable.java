package br.com.monitoratec.farol.graphql.model.responses.price;

import br.com.monitoratec.farol.graphql.model.dtos.price.PlanPriceTableDTO;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;

public class CommonResponseWithPlanPriceTable {
    private final CommonResponse commonResponse;
    private final PlanPriceTableDTO priceTable;

    public CommonResponseWithPlanPriceTable(CommonResponse commonResponse, PlanPriceTableDTO priceTable) {
        this.commonResponse = commonResponse;
        this.priceTable = priceTable;
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public PlanPriceTableDTO getPriceTable() {
        return priceTable;
    }
}
