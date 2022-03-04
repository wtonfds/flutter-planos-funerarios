package br.com.monitoratec.farol.graphql.resolvers.query.plan;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.plan.CommonResponseWithSubscriptionInformation;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.plan.PlanService;
import br.com.monitoratec.farol.sql.model.plan.SubscribedPlan;
import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.repository.subscribedPlan.SubscribedPlanRepository;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class ListCurrentSubscribedPlanImpl extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListCurrentSubscribedPlanImpl.class);

    private SubscribedPlanRepository subscribedPlanRepository;
    private ClientRepository clientRepository;
    private final PlanService planService;

    public ListCurrentSubscribedPlanImpl(SubscribedPlanRepository subscribedPlanRepository, ClientRepository clientRepository, PlanService planService) {
        this.subscribedPlanRepository = subscribedPlanRepository;
        this.clientRepository = clientRepository;
        this.planService = planService;
    }

    public CompletableFuture<CommonResponseWithSubscriptionInformation> listCurrentSubscribedPlan(
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithSubscriptionInformation> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    Optional<Client> clientOptional = clientRepository.findByIdAndActiveTrue(cachedTrustedToken.userCachedInfo.entityID);
                    if (clientOptional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }

                    Client client = clientOptional.get();

                    // The subscribed plan is not linked with dependents. If user is a dependent, we should pick
                    // the holder id from it.

                    final Client beneficiary;
                    if (client.getHolder() == null) {
                        beneficiary = client;
                    } else {
                        beneficiary = client.getHolder();
                    }

                    Optional<SubscribedPlan> subscribedPlan = subscribedPlanRepository.findByBeneficiaryIdAndActiveIsTrue(beneficiary.getId());
                    if (subscribedPlan.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.SUBSCRIBED_PLAN_NOT_FOUND);
                    }

                    SubscribedPlan currentSubscribedPlan = subscribedPlan.get();
                    List<Client> allClients = clientRepository.findAllByHolder(beneficiary);
                    allClients.add(beneficiary);

                    currentSubscribedPlan.setPlan(planService.buildPlanWithAgeRanges(currentSubscribedPlan.getPlan()));

                    double totalValue = planService.calculatePrice(allClients,
                            currentSubscribedPlan.getPlan().getPlanPriceTable().getAgeRanges(),
                            currentSubscribedPlan.getPlan().getDependentPriceTable().getAgeRanges()
                    );
                    CommonResponseWithSubscriptionInformation commonResponseWithSubscriptionInformation =
                            new CommonResponseWithSubscriptionInformation(StatusCodes.Success.Plan.GOT_SUBSCRIBED_PLAN, currentSubscribedPlan, totalValue);
                    responsePromise.complete(commonResponseWithSubscriptionInformation);
                });
        return responsePromise;
    }
}
