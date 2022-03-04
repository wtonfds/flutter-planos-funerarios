package br.com.monitoratec.farol.graphql.resolvers.query.plan;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.model.responses.plan.CommonResponseWithAvailableAnticipationMonths;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.sql.model.plan.SubscribedPlan;
import br.com.monitoratec.farol.sql.repository.subscribedPlan.SubscribedPlanRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Component
public class GetAvailableAnticipationParcelsImpl extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetAvailableAnticipationParcelsImpl.class);
    SubscribedPlanRepository subscribedPlanRepository;

    public GetAvailableAnticipationParcelsImpl(SubscribedPlanRepository subscribedPlanRepository) {
        this.subscribedPlanRepository = subscribedPlanRepository;
    }

    public CompletableFuture<CommonResponseWithAvailableAnticipationMonths> getAvailableAnticipationParcels(
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithAvailableAnticipationMonths> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    Long holderId = cachedTrustedToken.userCachedInfo.entityID;
                    SubscribedPlan subscribedPlan = subscribedPlanRepository.findByBeneficiaryIdAndActiveIsTrue(holderId).orElseThrow();

                    LocalDate validUntil = subscribedPlan.getValidUntil();
                    LocalDateTime lastPayment = subscribedPlan.getLastPayment();

                    Long months = lastPayment.toLocalDate().until(validUntil, ChronoUnit.MONTHS);

                    CommonResponseWithAvailableAnticipationMonths commonResponseWithAvailableAnticipationMonths =
                            new CommonResponseWithAvailableAnticipationMonths(months, StatusCodes.Success.Plan.ANTICIPATION_MONTHS_AVAILABLE_RETRIEVED_SUCCESSFULLY);

                    responsePromise.complete(commonResponseWithAvailableAnticipationMonths);

                });
        return responsePromise;
    }
}
