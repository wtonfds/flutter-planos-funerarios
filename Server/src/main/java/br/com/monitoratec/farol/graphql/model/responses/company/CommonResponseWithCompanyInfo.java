package br.com.monitoratec.farol.graphql.model.responses.company;

import br.com.monitoratec.farol.graphql.model.dtos.company.CompanyDTO;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.sql.model.company.Company;

public class CommonResponseWithCompanyInfo {
    private CommonResponse commonResponse;
    private CompanyDTO companyDTO;

    public CommonResponseWithCompanyInfo(CommonResponse commonResponse, Company company) {
        this.commonResponse = commonResponse;
        this.companyDTO = new CompanyDTO(company);
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public CompanyDTO getCompanyDTO() {
        return companyDTO;
    }

    public void setCompanyDTO(CompanyDTO companyDTO) {
        this.companyDTO = companyDTO;
    }
}
