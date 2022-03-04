package br.com.monitoratec.farol.graphql.model.responses.push;

import br.com.monitoratec.farol.graphql.model.dtos.push.PushPreferencesTypeDTO;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;

import java.util.List;

public class CommonResponseWithOneSignalPreferences {
    private final CommonResponse commonResponse;
    private final List<PushPreferencesTypeDTO> preferences;

    public CommonResponseWithOneSignalPreferences(CommonResponse commonResponse, List<PushPreferencesTypeDTO> pushPreferencesTypeDTO) {
        this.commonResponse = commonResponse;
        this.preferences = pushPreferencesTypeDTO;
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public List<PushPreferencesTypeDTO> getPreferences() {
        return preferences;
    }
}
