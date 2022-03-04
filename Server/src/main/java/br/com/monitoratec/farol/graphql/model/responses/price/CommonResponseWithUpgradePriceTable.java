package br.com.monitoratec.farol.graphql.model.responses.price;

import br.com.monitoratec.farol.graphql.model.dtos.price.UpgradePriceTableDTO;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;

public class CommonResponseWithUpgradePriceTable {
    private final CommonResponse commonResponse;
    private final UpgradePriceTableDTO priceTable;

    public CommonResponseWithUpgradePriceTable(CommonResponse commonResponse, UpgradePriceTableDTO priceTable) {
        this.commonResponse = commonResponse;
        this.priceTable = priceTable;
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public UpgradePriceTableDTO getPriceTable() {
        return priceTable;
    }
}
