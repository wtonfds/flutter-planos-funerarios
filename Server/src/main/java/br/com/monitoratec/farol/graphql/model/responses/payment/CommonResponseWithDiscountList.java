package br.com.monitoratec.farol.graphql.model.responses.payment;

import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.sql.model.payment.PaymentDiscount;

import java.util.List;

public class CommonResponseWithDiscountList {
    private CommonResponse commonResponse;
    private List<PaymentDiscount> discounts;

    public CommonResponseWithDiscountList(CommonResponse commonResponse, List<PaymentDiscount> discounts) {
        this.commonResponse = commonResponse;
        this.discounts = discounts;
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public List<PaymentDiscount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<PaymentDiscount> discounts) {
        this.discounts = discounts;
    }
}
