package br.com.monitoratec.farol.graphql.model.responses.user;

import br.com.monitoratec.farol.graphql.model.dtos.user.TrialUserDTO;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.sql.model.user.TrialUser;

public class CommonResponseWithTrialUserInformation {
    private CommonResponse commonResponse;
    private TrialUserDTO trialUser;

    public CommonResponseWithTrialUserInformation(CommonResponse commonResponse, TrialUser trialUser) {
        this.commonResponse = commonResponse;
        this.trialUser = new TrialUserDTO(trialUser);
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public TrialUserDTO getTrialUser() {
        return trialUser;
    }

    public void setTrialUser(TrialUserDTO trialUser) {
        this.trialUser = trialUser;
    }
}
