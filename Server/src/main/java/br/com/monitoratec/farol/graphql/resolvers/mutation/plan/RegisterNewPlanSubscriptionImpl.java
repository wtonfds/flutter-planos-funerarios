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
import br.com.monitoratec.farol.utils.payment.PaymentUtils;
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
public class RegisterNewPlanSubscriptionImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterNewPlanSubscriptionImpl.class);

    private final PlanRepository planRepository;
    private final ClientRepository clientRepository;
    private final SubscribedPlanRepository subscribedPlanRepository;
    private final PlanSubscriptionService planSubscriptionService;
    private final PlanService planService;

    public RegisterNewPlanSubscriptionImpl(PlanRepository planRepository, ClientRepository clientRepository, SubscribedPlanRepository subscribedPlanRepository, PlanSubscriptionService planSubscriptionService, PlanService planService) {
        this.planRepository = planRepository;
        this.clientRepository = clientRepository;
        this.subscribedPlanRepository = subscribedPlanRepository;
        this.planSubscriptionService = planSubscriptionService;
        this.planService = planService;
    }

    public CompletableFuture<CommonResponseWithSubscriptionInformation> registerNewPlanSubscription(
            long planId,
            PaymentTypeDTO paymentType,
            String rg,
            List<ClientDependentsInput> dependentsInput,
            AddressInput addressInput,
            CardInput cardInput,
            Integer paymentDay,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithSubscriptionInformation> responsePromise = TimedOutHandledPromiser.genPromise();
        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    final long clientId = cachedTrustedToken.userCachedInfo.entityID;

                    Optional<Plan> plan = planRepository.findById(planId);
                    if (plan.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.PLAN_NOT_FOUND);
                    }

                    Optional<Client> client = clientRepository.findById(clientId);
                    if (client.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }

                    Plan currentPlan = plan.get();
                    Client currentClient = client.get();

                    if (subscribedPlanRepository.findByBeneficiaryIdAndActiveIsTrue(clientId).isPresent()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.USER_IS_ALREADY_SUBSCRIBED);
                    }

                    if (!currentClient.isAlive()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.USER_IS_DEAD);
                    }

                    PasswordUtils passwordUtils = new PasswordUtils();
                    // Updates holder RG -- optional field so far
                    if (rg != null) {
                        currentClient.setRG(rg);
                    }
                    currentClient.setAuthorized(false);
                    currentClient.setAuthCode(passwordUtils.generateRandomAuthToken());
                    currentClient.setGracePeriod(addDays(currentPlan.getGracePeriod()));
                    currentClient.setClientType(ClientTypeDTO.HOLDER.name()); // Updates client status from GUEST -> HOLDER

                    final List<Client> dependents = new ArrayList<>();
                    final LocalDateTime now = LocalDateTime.now();

                    // Plan with age ranges
                    final Plan completePlan = planService.buildPlanWithAgeRanges(currentPlan);

                    if (dependentsInput != null) {
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
                    }

                    List<Client> subscribedPlanUsers = new ArrayList<>();
                    subscribedPlanUsers.add(currentClient);

                    if (dependents.size() > 0) {
                        subscribedPlanUsers.addAll(dependents);
                    }

                    double planPrice = planService.calculatePrice(subscribedPlanUsers, completePlan.getPlanPriceTable().getAgeRanges(), completePlan.getDependentPriceTable().getAgeRanges());

                    SubscribedPlan subscribedPlan = new SubscribedPlan();
                    subscribedPlan.setActive(true);
                    subscribedPlan.setBeneficiary(currentClient);
                    subscribedPlan.setPaymentType(paymentType.name());
                    subscribedPlan.setAdhesionContract(currentPlan.getAdhesionContract());
                    subscribedPlan.setClientNumber(new Random().nextLong()); // TODO must check how this number will be generated
                    subscribedPlan.setDefault(false); // Inadimplencia
                    subscribedPlan.setGracePeriod(currentClient.getGracePeriod()); // TODO check if this field is really necessary
                    subscribedPlan.setLuckNumber(0);
                    subscribedPlan.setValue(planPrice);
                    if (!paymentType.equals(PaymentTypeDTO.CREDIT_CARD)) {
                        // Only set payment day for debit card and payment slip
                        if (!PaymentUtils.isValidPaymentDay(paymentDay)) { // is not a valid day?
                            throw new ErrorOnProcessingRequestException(StatusCodes.Error.Payment.INVALID_PAYMENT_DAY);
                        }
                        subscribedPlan.setPaymentDay(paymentDay);
                    }
                    subscribedPlan.setSubscribedIn(now);
                    subscribedPlan.setLastPayment(now);
                    subscribedPlan.setPlan(completePlan);

                    Address address = addressInput.toModel(subscribedPlan.getAddress());

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
