package br.com.monitoratec.farol.graphql.model.responses.accredited;

import br.com.monitoratec.farol.graphql.model.dtos.accredited.AccreditedDTO;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.PaginationInfo;
import br.com.monitoratec.farol.sql.model.accredited.Accredited;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class CommonResponseWithAccreditedUsers {

    private CommonResponse commonResponse;
    private PaginationInfo paginationInfo;
    private List<AccreditedDTO> accreditedUsers;

    public CommonResponseWithAccreditedUsers(CommonResponse commonResponse, Page<Accredited> accreditedUsersPage) {
        this.commonResponse = commonResponse;
        this.paginationInfo = new PaginationInfo(accreditedUsersPage);
        this.accreditedUsers = accreditedUsersPage.getContent().stream().map(AccreditedDTO::new).collect(Collectors.toList());
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

    public List<AccreditedDTO> getAccreditedUsers() {
        return accreditedUsers;
    }

    public void setAccreditedUsers(List<AccreditedDTO> accreditedUsers) {
        this.accreditedUsers = accreditedUsers;
    }
}
