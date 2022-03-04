package br.com.monitoratec.farol.graphql.model.responses.user;

import br.com.monitoratec.farol.graphql.model.dtos.user.FarolUserDTO;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.PaginationInfo;
import br.com.monitoratec.farol.sql.model.user.FarolUser;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class CommonResponseWithFarolUsers {

    private CommonResponse commonResponse;
    private PaginationInfo paginationInfo;
    private List<FarolUserDTO> farolUsers;

    public CommonResponseWithFarolUsers(CommonResponse commonResponse, Page<FarolUser> farolUsersPage) {
        this.commonResponse = commonResponse;
        this.paginationInfo = new PaginationInfo(farolUsersPage);
        this.farolUsers = farolUsersPage.getContent().stream().map(FarolUserDTO::new).collect(Collectors.toList());
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

    public List<FarolUserDTO> getFarolUsers() {
        return farolUsers;
    }

    public void setFarolUsers(List<FarolUserDTO> farolUsers) {
        this.farolUsers = farolUsers;
    }
}
