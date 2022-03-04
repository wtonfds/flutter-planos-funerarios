package br.com.monitoratec.farol.graphql.resolvers.mutation.plan;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.input.common.AddressInput;
import br.com.monitoratec.farol.graphql.model.responses.plan.CommonResponseWithSubscriptionInformation;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.plan.PlanService;
import br.com.monitoratec.farol.sql.model.plan.SubscribedPlan;
import br.com.monitoratec.farol.sql.repository.location.AddressRepository;
import br.com.monitoratec.farol.sql.repository.subscribedPlan.SubscribedPlanRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class UpdatePlanSubscriptionAddressImpl extends BaseResolver implements GraphQLMutationResolver {

    private static Logger LOGGER = LoggerFactory.getLogger(UpdatePlanSubscriptionAddressImpl.class);

    private final SubscribedPlanRepository subscribedPlanRepository;
    private final AddressRepository addressRepository;
    private final PlanService planService;

    public UpdatePlanSubscriptionAddressImpl(SubscribedPlanRepository subscribedPlanRepository, AddressRepository addressRepository, PlanService planService) {
        this.subscribedPlanRepository = subscribedPlanRepository;
        this.addressRepository = addressRepository;
        this.planService = planService;
    }

    public CompletableFuture<CommonResponseWithSubscriptionInformation> updatePlanSubscriptionAddress(
            AddressInput addressInput,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithSubscriptionInformation> responsePromise = TimedOutHandledPromiser.genPromise();
        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    final long clientId = cachedTrustedToken.userCachedInfo.entityID;

                    Optional<SubscribedPlan> subscribedPlanOptional = subscribedPlanRepository.findByBeneficiaryIdAndActiveIsTrue(clientId);
                    if (subscribedPlanOptional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.SUBSCRIBED_PLAN_NOT_FOUND);
                    }

                    SubscribedPlan subscribedPlan = subscribedPlanOptional.get();

                    subscribedPlan.setAddress(addressRepository.save(addressInput.toModel(subscribedPlan.getAddress())));

                    subscribedPlanRepository.save(subscribedPlan);

                    subscribedPlan.setPlan(planService.buildPlanWithAgeRanges(subscribedPlan.getPlan()));

                    CommonResponseWithSubscriptionInformation commonResponseWithSubscriptionInformation =
                            new CommonResponseWithSubscriptionInformation(StatusCodes.Success.Plan.UPDATED_ADDRESS, subscribedPlan);
                    responsePromise.complete(commonResponseWithSubscriptionInformation);
                });
        return responsePromise;
    }
}
