package br.com.monitoratec.farol.graphql.model.responses.accredited;

import br.com.monitoratec.farol.graphql.model.dtos.accredited.AccreditedDTO;
import br.com.monitoratec.farol.graphql.model.responses.base.BaseCommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.sql.model.accredited.Accredited;

public class CommonResponseWithAccreditedInformation extends BaseCommonResponse {
    private CommonResponse commonResponse;
    private AccreditedDTO genericAccredited;

    public CommonResponseWithAccreditedInformation(CommonResponse commonResponse, Accredited accredited) {
        this.commonResponse = commonResponse;
        this.genericAccredited = new AccreditedDTO(accredited);
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public AccreditedDTO getGenericAccredited() {
        return genericAccredited;
    }

    public void setGenericAccredited(AccreditedDTO genericAccredited) {
        this.genericAccredited = genericAccredited;
    }
}
