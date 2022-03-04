package br.com.monitoratec.farol.graphql.resolvers.mutation.plan;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.dtos.payment.PaymentTypeDTO;
import br.com.monitoratec.farol.graphql.model.dtos.user.ClientTypeDTO;
import br.com.monitoratec.farol.graphql.model.input.common.AddressInput;
import br.com.monitoratec.farol.graphql.model.input.payment.CardInput;
import br.com.monitoratec.farol.graphql.model.input.user.ClientDependentsInput;
import br.com.monitoratec.farol.graphql.model.responses.plan.CommonResponseWithSubscriptionInformation;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.plan.PlanService;
import br.com.monitoratec.farol.service.plan.PlanSubscriptionService;
import br.com.monitoratec.farol.sql.model.location.Address;
import br.com.monitoratec.farol.sql.model.plan.Plan;
import br.com.monitoratec.farol.sql.model.plan.SubscribedPlan;
import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.repository.plan.PlanRepository;
import br.com.monitoratec.farol.sql.repository.subscribedPlan.SubscribedPlanRepository;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
import br.com.monitoratec.farol.utils.password.PasswordUtils;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Component
public class UpgradeToNewPlanImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpgradeToNewPlanImpl.class);

    private final PlanRepository planRepository;
    private final ClientRepository clientRepository;
    private final SubscribedPlanRepository subscribedPlanRepository;
    private final PlanSubscriptionService planSubscriptionService;
    private final PlanService planService;

    public UpgradeToNewPlanImpl(PlanRepository planRepository, ClientRepository clientRepository, SubscribedPlanRepository subscribedPlanRepository, PlanSubscriptionService planSubscriptionService, PlanService planService) {
        this.planRepository = planRepository;
        this.clientRepository = clientRepository;
        this.subscribedPlanRepository = subscribedPlanRepository;
        this.planSubscriptionService = planSubscriptionService;
        this.planService = planService;
    }

    public CompletableFuture<CommonResponseWithSubscriptionInformation> upgradeToNewPlan(
            Long planId,
            List<ClientDependentsInput> dependentsInput,
            AddressInput addressInput,
            CardInput cardInput,
            PaymentTypeDTO paymentType,
            Integer paymentSlipDate,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);
        CompletableFuture<CommonResponseWithSubscriptionInformation> responsePromise = TimedOutHandledPromiser.genPromise();
        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    final long clientId = cachedTrustedToken.userCachedInfo.entityID;

                    Optional<Client> clientOptional = clientRepository.findByIdAndActiveTrue(clientId);
                    if (clientOptional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }

                    Optional<Plan> planOptional = planRepository.findById(planId);
                    if (planOptional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.PLAN_NOT_FOUND);
                    }

                    Client currentClient = clientOptional.get();
                    Plan currentPlan = planService.buildPlanWithAgeRanges(planOptional.get());

                    Client oldHolder = currentClient.getHolder();
                    Optional<SubscribedPlan> oldHolderSubscribedPlan = subscribedPlanRepository.findByBeneficiaryIdAndActiveIsTrue(oldHolder.getId());

                    currentClient.setHolder(null);

                    if (currentClient.getClientType().equals(ClientTypeDTO.GUEST.name()) || currentClient.getClientType().equals(ClientTypeDTO.HOLDER.name())) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_IS_NOT_DEPENDENT);
                    }

                    currentClient.setClientType(ClientTypeDTO.HOLDER.name());
                    currentClient.setAuthorized(false);
                    currentClient.setAuthCode(new PasswordUtils().generateRandomAuthToken());

                    if (!currentClient.isAlive()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.USER_IS_DEAD);
                    }

                    final List<Client> dependents = new ArrayList<>();
                    final LocalDateTime now = LocalDateTime.now();

                    for (ClientDependentsInput entry : dependentsInput) {
                        Client dependent = new Client();
                        dependent.setHolder(currentClient);
                        dependent.setClientType(ClientTypeDTO.EXTRA_DEPENDENT.name());
                        dependent.setAlive(true);
                        dependent.setBirthDay(entry.getBirthday().getDate());
                        dependent.setName(entry.getName());
                        dependent.setDeleted(false);
                        dependent.setCreatedAt(now);
                        dependent.setGracePeriod(addDays(currentPlan.getGracePeriodExtraDependents()));
                        dependents.add(dependent);
                    }

                    List<Client> subscribedPlanUsers = new ArrayList<>();
                    subscribedPlanUsers.add(currentClient);
                    subscribedPlanUsers.addAll(dependents);

                    final double planPrice = planService.calculatePrice(
                            subscribedPlanUsers,
                            currentPlan.getUpgradePriceTable().getAgeRanges(),
                            currentPlan.getDependentPriceTable().getAgeRanges()
                    );

                    SubscribedPlan subscribedPlan = new SubscribedPlan();
                    subscribedPlan.setActive(true);
                    subscribedPlan.setBeneficiary(currentClient);
                    subscribedPlan.setAdhesionContract(currentPlan.getAdhesionContract());
                    subscribedPlan.setClientNumber(new Random().nextLong()); // TODO must check how this number will be generated
                    subscribedPlan.setDefault(false); // Inadimplencia
                    subscribedPlan.setLastPayment(now);
                    subscribedPlan.setPaymentDay(paymentSlipDate);
                    subscribedPlan.setPaymentType(paymentType.name());

                    try {
                        LocalDate gracePeriod = LocalDate.now().plusDays(currentPlan.getGracePeriod());
                        subscribedPlan.setGracePeriod(gracePeriod);
                    } catch (Exception e) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.INVALID_GRACE_DAYS_PERIOD);
                    }

                    subscribedPlan.setLuckNumber(0);
                    subscribedPlan.setValue(planPrice);
                    subscribedPlan.setSubscribedIn(LocalDateTime.now());

                    Address address = addressInput.toModel(subscribedPlan.getAddress());

                    subscribedPlan.setPlan(currentPlan);

                    // if should update old holder plan price
                    if (oldHolderSubscribedPlan.isPresent()) {
                        planSubscriptionService.calculateSubscribedPlanPriceAndUpdate(
                                oldHolder,
                                currentPlan.getPlanPriceTable().getAgeRanges(),
                                currentPlan.getDependentPriceTable().getAgeRanges(),
                                oldHolderSubscribedPlan.get());
                    }
                    subscribedPlan.setPlan(planService.buildPlanWithAgeRanges(subscribedPlan.getPlan()));

                    planSubscriptionService.subscribe(subscribedPlan, dependents, currentClient, cardInput, address);

                    CommonResponseWithSubscriptionInformation commonResponseWithSubscriptionInformation =
                            new CommonResponseWithSubscriptionInformation(StatusCodes.Success.Plan.SUBSCRIBED_TO_PLAN, subscribedPlan);
                    responsePromise.complete(commonResponseWithSubscriptionInformation);
                });
        return responsePromise;
    }

    private LocalDate addDays(long days) {
        try {
            return LocalDate.now().plusDays(days);
        } catch (Exception e) {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.INVALID_GRACE_DAYS_PERIOD);
        }
    }
}
