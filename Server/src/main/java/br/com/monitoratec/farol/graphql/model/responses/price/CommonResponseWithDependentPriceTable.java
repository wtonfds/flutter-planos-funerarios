package br.com.monitoratec.farol.graphql.model.responses.price;

import br.com.monitoratec.farol.graphql.model.dtos.price.DependentPriceTableDTO;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;

public class CommonResponseWithDependentPriceTable {
    private final CommonResponse commonResponse;
    private final DependentPriceTableDTO priceTable;

    public CommonResponseWithDependentPriceTable(CommonResponse commonResponse, DependentPriceTableDTO priceTable) {
        this.commonResponse = commonResponse;
        this.priceTable = priceTable;
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public DependentPriceTableDTO getPriceTable() {
        return priceTable;
    }
}
