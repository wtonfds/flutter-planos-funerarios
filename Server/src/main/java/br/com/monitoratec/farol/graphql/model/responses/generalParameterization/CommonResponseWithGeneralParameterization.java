package br.com.monitoratec.farol.graphql.model.responses.generalParameterization;

import br.com.monitoratec.farol.graphql.model.dtos.generalParameterization.GeneralParameterizationDTO;
import br.com.monitoratec.farol.graphql.model.responses.base.BaseCommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.sql.model.generalParameterization.GeneralParameterization;

public class CommonResponseWithGeneralParameterization extends BaseCommonResponse {
    private CommonResponse commonResponse;
    private GeneralParameterizationDTO generalParameterizationDTO;

    public CommonResponseWithGeneralParameterization(CommonResponse commonResponse, GeneralParameterization generalParameterization) {
        this.commonResponse = commonResponse;
        this.generalParameterizationDTO = new GeneralParameterizationDTO(generalParameterization);
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public GeneralParameterizationDTO getGeneralParameterizationDTO() {
        return generalParameterizationDTO;
    }

    public void setGeneralParameterizationDTO(GeneralParameterizationDTO generalParameterizationDTO) {
        this.generalParameterizationDTO = generalParameterizationDTO;
    }
}
