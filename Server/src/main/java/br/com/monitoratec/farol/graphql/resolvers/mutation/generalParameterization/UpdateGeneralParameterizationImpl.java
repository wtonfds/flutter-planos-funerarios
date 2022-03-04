package br.com.monitoratec.farol.graphql.resolvers.mutation.generalParameterization;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.model.input.generalParameterization.GeneralParameterizationInfo;
import br.com.monitoratec.farol.graphql.model.responses.generalParameterization.CommonResponseWithGeneralParameterization;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.sql.model.generalParameterization.GeneralParameterization;
import br.com.monitoratec.farol.sql.repository.generalParameterization.GeneralParameterizationRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Component
public class UpdateGeneralParameterizationImpl extends BaseResolver implements GraphQLMutationResolver {

    private static Logger LOGGER = LoggerFactory.getLogger(UpdateGeneralParameterizationImpl.class);

    private final GeneralParameterizationRepository generalParameterizationRepository;

    public UpdateGeneralParameterizationImpl(GeneralParameterizationRepository generalParameterizationRepository){
        this.generalParameterizationRepository = generalParameterizationRepository;
    }

    public CompletableFuture<CommonResponseWithGeneralParameterization> updateGeneralParameterization (
            GeneralParameterizationInfo generalParameterizationInfo,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithGeneralParameterization> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    GeneralParameterization generalParameterization = generalParameterizationRepository.findAll().get(0);

                    if (generalParameterizationInfo.getLoyaltyCard() != null) {
                        generalParameterization.setLoyaltyCard(generalParameterizationInfo.getLoyaltyCard());
                    }

                    if (generalParameterizationInfo.getLoyaltyCardNumberRule() != null) {
                        generalParameterization.setLoyaltyCardNumberRule(generalParameterizationInfo.getLoyaltyCardNumberRule());
                    }

                    if (generalParameterizationInfo.getTem() != null) {
                        generalParameterization.setTem(generalParameterizationInfo.getTem());
                    }

                    if (generalParameterizationInfo.getAccreditedCouponDueDate() != null) {
                        generalParameterization.setAccreditedCouponDueDate(generalParameterizationInfo.getAccreditedCouponDueDate().getDate());
                    }

                    if (generalParameterizationInfo.getAccreditedLoginWithCNPJ() != null) {
                        generalParameterization.setAccreditedLoginWithCNPJ(generalParameterizationInfo.getAccreditedLoginWithCNPJ());
                    }

                    if (generalParameterizationInfo.getLotteryAutoDisclosure() != null) {
                        generalParameterization.setLotteryAutoDisclosure(generalParameterizationInfo.getLotteryAutoDisclosure());
                    }

                    if (generalParameterizationInfo.getLotteryURL() != null) {
                        generalParameterization.setLotteryURL(generalParameterizationInfo.getLotteryURL());
                    }

                    if (generalParameterizationInfo.getSLA() != null) {
                        generalParameterization.setSLA(generalParameterizationInfo.getSLA());
                    }

                    if (generalParameterizationInfo.getNfIss() != null) {
                        generalParameterization.setNfIss(generalParameterizationInfo.getNfIss());
                    }

                    if (generalParameterizationInfo.getNfGissURL() != null) {
                        generalParameterization.setNfGissURL(generalParameterizationInfo.getNfGissURL());
                    }

                    if (generalParameterizationInfo.getNfGissUser() != null) {
                        generalParameterization.setNfGissUser(generalParameterizationInfo.getNfGissUser());
                    }

                    if (generalParameterizationInfo.getNfGissPassword() != null) {
                        generalParameterization.setNfGissPassword(generalParameterizationInfo.getNfGissPassword());
                    }

                    if (generalParameterizationInfo.getNfGissDueDate() != null) {
                        generalParameterization.setNfGissDueDate(generalParameterizationInfo.getNfGissDueDate());
                    }

                    if (generalParameterizationInfo.getFarolTelephone() != null) {
                        generalParameterization.setFarolTelephone(generalParameterizationInfo.getFarolTelephone());
                    }

                    if (generalParameterizationInfo.getTimeToBlockAccount() != null) {
                        generalParameterization.setTimeToBlockAccount(generalParameterizationInfo.getTimeToBlockAccount());
                    }

                    if (generalParameterizationInfo.getTimeToUpdateFinancialData() != null) {
                        generalParameterization.setTimeToUpdateFinancialData(generalParameterizationInfo.getTimeToUpdateFinancialData());
                    }

                    if (generalParameterizationInfo.getFarolEmail() != null) {
                        final String email = generalParameterizationInfo.getFarolEmail().stringValue();
                        generalParameterization.setFarolEmail(email);
                    }

                    if (generalParameterizationInfo.getFuneralAssistancePhone() != null) {
                        generalParameterization.setFuneralAssistancePhone(generalParameterizationInfo.getFuneralAssistancePhone());
                    }

                    if (generalParameterizationInfo.getAliquot() != null) {
                        generalParameterization.setAliquot(generalParameterizationInfo.getAliquot());
                    }

                    if (generalParameterizationInfo.getPlanBenefits() != null) {
                        generalParameterization.setPlanBenefits(generalParameterizationInfo.getPlanBenefits());
                    }

                    if (generalParameterizationInfo.getPlanBenefitsDetails() != null) {
                        generalParameterization.setPlanBenefitsDetails(generalParameterizationInfo.getPlanBenefitsDetails());
                    }

                    generalParameterization = generalParameterizationRepository.save(generalParameterization);

                    CommonResponseWithGeneralParameterization commonResponseWithGeneralParameterization = new CommonResponseWithGeneralParameterization(StatusCodes.Success.GeneralParameterization.GOT_GENERAL_PARAMETERIZATION, generalParameterization);
                    responsePromise.complete(commonResponseWithGeneralParameterization);
                });
        return responsePromise;
    }
}
