package br.com.monitoratec.farol.graphql.model.responses.lotteryNumbers;

import br.com.monitoratec.farol.graphql.model.dtos.lotteryNumbers.LotteryNumbersDTO;
import br.com.monitoratec.farol.graphql.model.responses.base.BaseCommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.PaginationInfo;
import br.com.monitoratec.farol.sql.model.lotteryNumber.LotteryNumber;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class CommonResponseWithLotteryNumbers extends BaseCommonResponse {

    private CommonResponse commonResponse;
    private PaginationInfo paginationInfo;
    private List<LotteryNumbersDTO> genericLotteryNumbers;

    public CommonResponseWithLotteryNumbers(CommonResponse commonResponse, Page<LotteryNumber> lotteryNumberPage) {
        this.commonResponse = commonResponse;
        this.paginationInfo = new PaginationInfo(lotteryNumberPage);
        this.genericLotteryNumbers = lotteryNumberPage.getContent().stream().map(LotteryNumbersDTO::new).collect(Collectors.toList());
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public PaginationInfo getPaginationInfo() {
        return paginationInfo;
    }

    public void setPaginationInfo(PaginationInfo paginationInfo) {
        this.paginationInfo = paginationInfo;
    }

    public List<LotteryNumbersDTO> getLotteryNumbers() {
        return genericLotteryNumbers;
    }

    public void setLotteryNumbers(List<LotteryNumbersDTO> genericLotteryNumbers) {
        this.genericLotteryNumbers = genericLotteryNumbers;
    }
}
