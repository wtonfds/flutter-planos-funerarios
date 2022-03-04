package br.com.monitoratec.farol.graphql.resolvers.mutation.company;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.model.input.company.CompanyInfo;
import br.com.monitoratec.farol.graphql.model.responses.company.CommonResponseWithCompanyInfo;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.sql.model.company.Company;
import br.com.monitoratec.farol.sql.repository.company.CompanyRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Component
public class UpdateCompanyInformationImpl extends BaseResolver implements GraphQLMutationResolver {
    private static Logger LOGGER = LoggerFactory.getLogger(UpdateCompanyInformationImpl.class);

    private final CompanyRepository companyRepository;

    public UpdateCompanyInformationImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public CompletableFuture<CommonResponseWithCompanyInfo> updateCompanyInfo(
            CompanyInfo input,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithCompanyInfo> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    Company company = companyRepository.getCompany();

                    if (input.getCity() != null) company.setCity(input.getCity());

                    if (input.getCnpj() != null) company.setCnpj(input.getCnpj());

                    if (input.getComplement() != null) company.setComplement(input.getComplement());

                    if (input.getCountry() != null) company.setCountry(input.getCountry());

                    if (input.getMunicipalRegistry() != null)
                        company.setMunicipalRegistry(input.getMunicipalRegistry());

                    if (input.getName() != null) company.setName(input.getName());

                    if (input.getNeighborhood() != null) company.setNeighborhood(input.getNeighborhood());

                    if (input.getNumber() != null) company.setNumber(input.getNumber());

                    if (input.getProvince() != null) company.setProvince(input.getProvince());

                    if (input.getStreet() != null) company.setStreet(input.getStreet());

                    if (input.getTelephone() != null) company.setTelephone(input.getTelephone());

                    if (input.getType() != null) company.setType(input.getType());

                    if (input.getWebSite() != null) company.setWebSite(input.getWebSite());

                    if (input.getZipCode() != null) company.setZipCode(input.getZipCode());

                    if (input.getNfDiscrimination() != null) company.setNfDiscrimination(input.getNfDiscrimination());

                    if (input.getNfItemCode() != null) company.setNfItemCode(input.getNfItemCode());

                    if (input.getNfMunicipalTaxCode() != null)
                        company.setNfMunicipalTaxCode(input.getNfMunicipalTaxCode());

                    company = companyRepository.save(company);

                    CommonResponseWithCompanyInfo commonResponseWithCompanyInfo = new CommonResponseWithCompanyInfo(StatusCodes.Success.Company.SAVED_COMPANY_INFO_SUCCESSFULLY, company);

                    responsePromise.complete(commonResponseWithCompanyInfo);

                });
        return responsePromise;
    }
}
