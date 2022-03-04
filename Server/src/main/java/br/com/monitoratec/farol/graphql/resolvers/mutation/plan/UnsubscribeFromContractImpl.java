package br.com.monitoratec.farol.graphql.resolvers.mutation.plan;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.dtos.user.ClientTypeDTO;
import br.com.monitoratec.farol.graphql.model.responses.plan.CommonResponseWithSubscriptionInformation;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.plan.PlanService;
import br.com.monitoratec.farol.service.plan.PlanSubscriptionService;
import br.com.monitoratec.farol.sql.model.plan.SubscribedPlan;
import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.repository.subscribedPlan.SubscribedPlanRepository;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class UnsubscribeFromContractImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(UnsubscribeFromContractImpl.class);

    private final SubscribedPlanRepository subscribedPlanRepository;
    private final ClientRepository clientRepository;
    private final PlanSubscriptionService planSubscriptionService;
    private final PlanService planService;

    public UnsubscribeFromContractImpl(SubscribedPlanRepository subscribedPlanRepository, ClientRepository clientRepository, PlanSubscriptionService planSubscriptionService, PlanService planService) {
        this.subscribedPlanRepository = subscribedPlanRepository;
        this.clientRepository = clientRepository;
        this.planSubscriptionService = planSubscriptionService;
        this.planService = planService;
    }

    public CompletableFuture<CommonResponseWithSubscriptionInformation> unsubscribeFromContract(
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithSubscriptionInformation> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {

                    Long userId = cachedTrustedToken.userCachedInfo.entityID;
                    Optional<SubscribedPlan> subscribedPlan = subscribedPlanRepository.findByBeneficiaryIdAndActiveIsTrue(userId);
                    if (subscribedPlan.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.SUBSCRIBED_PLAN_NOT_FOUND);
                    }

                    SubscribedPlan currentSubscribedPlan = subscribedPlan.get();

                    if (!canCancel(currentSubscribedPlan.getLastPayment())) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.CANNOT_CANCEL_PLAN);
                    }

                    currentSubscribedPlan.setCancelledIn(LocalDateTime.now());
                    currentSubscribedPlan.setActive(false);

                    Client holder = clientRepository.findByIdAndActiveTrue(userId).orElseThrow();

                    List<Client> dependents = clientRepository.findAllByHolder(holder);

                    for (Client dependent : dependents) {
                        if (!dependent.isActive()) {
                            dependent.setDeleted(true);
                        }
                        dependent.setHolder(null);
                        dependent.setClientType(ClientTypeDTO.GUEST.name());
                    }

                    holder.setClientType(ClientTypeDTO.GUEST.name());
                    dependents.add(holder);

                    final String reason = "User wants to cancel";

                    planSubscriptionService.unsubscribe(currentSubscribedPlan, dependents, holder, reason);
                    currentSubscribedPlan.setPlan(planService.buildPlanWithAgeRanges(currentSubscribedPlan.getPlan()));

                    CommonResponseWithSubscriptionInformation commonResponseWithSubscriptionInformation =
                            new CommonResponseWithSubscriptionInformation(StatusCodes.Success.Plan.UNSUBSCRIBED, currentSubscribedPlan);
                    responsePromise.complete(commonResponseWithSubscriptionInformation);
                });

        return responsePromise;
    }

    private boolean canCancel(LocalDateTime lastPayment) {
        return !lastPayment.plusDays(30).isBefore(LocalDateTime.now());
    }
}
