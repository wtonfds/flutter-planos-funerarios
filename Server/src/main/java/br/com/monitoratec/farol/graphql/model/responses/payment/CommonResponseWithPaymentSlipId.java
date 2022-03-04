package br.com.monitoratec.farol.graphql.model.responses.payment;

import br.com.monitoratec.farol.graphql.model.responses.base.BaseCommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;

public class CommonResponseWithPaymentSlipId extends BaseCommonResponse {
    private CommonResponse commonResponse;
    private String paymentSlipId;


    public CommonResponseWithPaymentSlipId(CommonResponse commonResponse, String paymentSlipId) {
        this.commonResponse = commonResponse;
        this.paymentSlipId = paymentSlipId;
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public String getPaymentSlipId() {
        return paymentSlipId;
    }

    public void setPaymentSlipId(String paymentSlipId) {
        this.paymentSlipId = paymentSlipId;
    }
}
