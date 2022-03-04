package br.com.monitoratec.farol.graphql.model.responses.plan;

import br.com.monitoratec.farol.graphql.model.dtos.plan.PaymentMonthDTO;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.sql.model.payment.PaymentMonth;

import java.util.List;
import java.util.stream.Collectors;

public class CommonResponseWithPaymentMonths {
    private CommonResponse commonResponse;
    private List<PaymentMonthDTO> paymentMonthList;

    public CommonResponseWithPaymentMonths(CommonResponse commonResponse, List<PaymentMonth> paymentMonthList) {
        this.commonResponse = commonResponse;
        this.paymentMonthList = paymentMonthList.stream().map(PaymentMonthDTO::new).collect(Collectors.toList());
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public List<PaymentMonthDTO> getPaymentMonthList() {
        return paymentMonthList;
    }

    public void setPaymentMonthList(List<PaymentMonthDTO> paymentMonthList) {
        this.paymentMonthList = paymentMonthList;
    }
}
