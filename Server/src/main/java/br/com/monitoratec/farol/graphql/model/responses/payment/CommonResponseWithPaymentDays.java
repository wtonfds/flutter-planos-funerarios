package br.com.monitoratec.farol.graphql.model.responses.payment;

import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;

import java.util.List;

public class CommonResponseWithPaymentDays {
    private CommonResponse commonResponse;
    private List<Integer> paymentDays;

    public CommonResponseWithPaymentDays(CommonResponse commonResponse, List<Integer> paymentDays) {
        this.commonResponse = commonResponse;
        this.paymentDays = paymentDays;
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public List<Integer> getPaymentDays() {
        return paymentDays;
    }

    public void setPaymentDays(List<Integer> paymentDays) {
        this.paymentDays = paymentDays;
    }
}
