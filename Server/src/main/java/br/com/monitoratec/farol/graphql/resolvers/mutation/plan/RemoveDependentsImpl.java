package br.com.monitoratec.farol.graphql.resolvers.mutation.plan;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.dtos.payment.PaymentTypeDTO;
import br.com.monitoratec.farol.graphql.model.dtos.user.ClientTypeDTO;
import br.com.monitoratec.farol.graphql.model.responses.plan.CommonResponseWithDeletedDependents;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.payment.PaymentService;
import br.com.monitoratec.farol.service.plan.PlanService;
import br.com.monitoratec.farol.service.plan.PlanSubscriptionService;
import br.com.monitoratec.farol.sql.model.plan.Plan;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class RemoveDependentsImpl extends BaseResolver implements GraphQLMutationResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoveDependentsImpl.class);

    private final ClientRepository clientRepository;
    private final PlanSubscriptionService service;
    private final PlanService planService;
    private final SubscribedPlanRepository subscribedPlanRepository;
    private final PaymentService paymentService;

    public RemoveDependentsImpl(ClientRepository clientRepository, PlanSubscriptionService service, PlanService planService, SubscribedPlanRepository subscribedPlanRepository, PaymentService paymentService) {
        this.clientRepository = clientRepository;
        this.service = service;
        this.planService = planService;
        this.subscribedPlanRepository = subscribedPlanRepository;
        this.paymentService = paymentService;
    }

    public CompletableFuture<CommonResponseWithDeletedDependents> removeDependents(
            List<Long> dependents,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithDeletedDependents> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {

                    List<Client> updatedDependetsArray = new ArrayList<>();

                    // this search is for log operation
                    Long holderId = cachedTrustedToken.userCachedInfo.entityID;
                    Optional<Client> optionalHolder = clientRepository.findById(holderId);
                    if (optionalHolder.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }

                    Client holder = optionalHolder.get();

                    Optional<SubscribedPlan> optionalSubscribedPlan = subscribedPlanRepository.findByBeneficiaryIdAndActiveIsTrue(holder.getId());
                    if (optionalSubscribedPlan.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.SUBSCRIBED_PLAN_NOT_FOUND);
                    }

                    SubscribedPlan currentSubscribedPlan = optionalSubscribedPlan.get();
                    Plan currentPlan = planService.buildPlanWithAgeRanges(currentSubscribedPlan.getPlan());

                    double currentSubscribedPlanValue = currentSubscribedPlan.getValue();

                    List<Client> dependentsArray = new ArrayList<>();

                    // Retrieve all dependents from database
                    for (Long id : dependents) {
                        Optional<Client> dependent = clientRepository.findById(id);
                        if (dependent.isPresent()) {
                            dependentsArray.add(dependent.get());
                        } else {
                            throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                        }
                    }

                    currentSubscribedPlanValue -= planService.calculatePrice(dependentsArray, currentPlan.getPlanPriceTable().getAgeRanges(), currentPlan.getDependentPriceTable().getAgeRanges());
                    currentSubscribedPlanValue = currentSubscribedPlanValue < 0 ? 0 : currentSubscribedPlanValue;
                    currentSubscribedPlan.setValue(currentSubscribedPlanValue);

                    if(currentSubscribedPlan.getPaymentType().equals(PaymentTypeDTO.CREDIT_CARD.name())) {
                        paymentService.cancelCreditCardSubscription("Alterado valor do plano", holder);
                        currentSubscribedPlan.setWaitingForLastPaymentDate(true);
                    }

                    // And update them
                    for (Client dependent : dependentsArray) {
                        dependent.setHolder(null);
                        dependent.setClientType(ClientTypeDTO.GUEST.name());
                        if (!dependent.isActive()) {
                            dependent.setDeleted(true);
                        }
                        updatedDependetsArray.add(dependent);

                    }

                    // Needed for logging
                    final String holderCpf = holder.getCPF();
                    final String holderName = holder.getName();

                    service.removeDependents(updatedDependetsArray, holderName, holderCpf, currentSubscribedPlan);

                    CommonResponseWithDeletedDependents commonResponseWithDeletedDependents =
                            new CommonResponseWithDeletedDependents(StatusCodes.Success.Plan.DELETED_DEPENDENTS_SUCCESSFULLY, updatedDependetsArray);
                    responsePromise.complete(commonResponseWithDeletedDependents);
                });

        return responsePromise;
    }
}
