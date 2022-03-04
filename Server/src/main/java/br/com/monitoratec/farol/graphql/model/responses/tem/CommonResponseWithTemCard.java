package br.com.monitoratec.farol.graphql.model.responses.tem;

import br.com.monitoratec.farol.graphql.model.dtos.tem.CardDataDTO;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;

public class CommonResponseWithTemCard {
    private final CommonResponse commonResponse;
    private final CardDataDTO cardDataDTO;

    public CommonResponseWithTemCard(CommonResponse commonResponse, CardDataDTO cardDataDTO) {
        this.commonResponse = commonResponse;
        this.cardDataDTO = cardDataDTO;
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public CardDataDTO getCardDataDTO() {
        return cardDataDTO;
    }
}
