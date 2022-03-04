package br.com.monitoratec.farol.graphql.model.responses.payment;

import br.com.monitoratec.farol.graphql.model.dtos.payment.PaymentSituationDTO;
import br.com.monitoratec.farol.graphql.model.responses.base.BaseCommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;

import java.util.List;

public class CommonResponseWithClientPaymentSituation extends BaseCommonResponse {
    private final CommonResponse commonResponse;
    private final List<PaymentSituationDTO> paymentSituation;

    public CommonResponseWithClientPaymentSituation(CommonResponse commonResponse, List<PaymentSituationDTO> paymentSituation) {
        this.commonResponse = commonResponse;
        this.paymentSituation = paymentSituation;
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public List<PaymentSituationDTO> getPaymentSituation() {
        return paymentSituation;
    }
}
