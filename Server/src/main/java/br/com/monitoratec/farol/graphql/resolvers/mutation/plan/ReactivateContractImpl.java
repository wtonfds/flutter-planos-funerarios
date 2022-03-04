package br.com.monitoratec.farol.graphql.resolvers.mutation.plan;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.dtos.log.ClientActionTypeDTO;
import br.com.monitoratec.farol.graphql.model.dtos.user.ClientTypeDTO;
import br.com.monitoratec.farol.graphql.model.responses.plan.CommonResponseWithSubscriptionInformation;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.log.LogService;
import br.com.monitoratec.farol.service.plan.PlanService;
import br.com.monitoratec.farol.service.plan.PlanSubscriptionService;
import br.com.monitoratec.farol.service.user.ClientService;
import br.com.monitoratec.farol.sql.model.log.LogSubscribedPlan;
import br.com.monitoratec.farol.sql.model.plan.Plan;
import br.com.monitoratec.farol.sql.model.plan.SubscribedPlan;
import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.model.user.FarolUser;
import br.com.monitoratec.farol.sql.repository.log.LogSubscribedPlanRepository;
import br.com.monitoratec.farol.sql.repository.subscribedPlan.SubscribedPlanRepository;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
import br.com.monitoratec.farol.sql.repository.user.FarolUserRepository;
import br.com.monitoratec.farol.utils.data.AgeUtils;
import br.com.monitoratec.farol.utils.log.LogMessageBuilder;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class ReactivateContractImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReactivateContractImpl.class);

    private final SubscribedPlanRepository subscribedPlanRepository;
    private final ClientRepository clientRepository;
    private final LogSubscribedPlanRepository logSubscribedPlanRepository;
    private final FarolUserRepository farolUserRepository;
    private final PlanService planService;
    private final LogService logService;
    private final ClientService clientService;
    private final PlanSubscriptionService planSubscriptionService;

    public ReactivateContractImpl(SubscribedPlanRepository subscribedPlanRepository, ClientRepository clientRepository,
                                  LogSubscribedPlanRepository logSubscribedPlanRepository, FarolUserRepository farolUserRepository,
                                  PlanService planService, LogService logService, ClientService clientService,
                                  PlanSubscriptionService planSubscriptionService) {
        this.subscribedPlanRepository = subscribedPlanRepository;
        this.clientRepository = clientRepository;
        this.logSubscribedPlanRepository = logSubscribedPlanRepository;
        this.farolUserRepository = farolUserRepository;
        this.planService = planService;
        this.logService = logService;
        this.clientService = clientService;
        this.planSubscriptionService = planSubscriptionService;
    }

    public CompletableFuture<CommonResponseWithSubscriptionInformation> reactivateContract(
            long subscribedPlanId,
            String commentary,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithSubscriptionInformation> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {

                    Optional<FarolUser> farolUser = farolUserRepository.findById(cachedTrustedToken.userCachedInfo.entityID);
                    if (farolUser.isEmpty()) { // not logged as farol user
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }

                    Optional<SubscribedPlan> subscribedPlan = subscribedPlanRepository.findById(subscribedPlanId);
                    if (subscribedPlan.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.SUBSCRIBED_PLAN_NOT_FOUND);
                    }

                    SubscribedPlan currentSubscribedPlan = subscribedPlan.get();
                    long clientId = currentSubscribedPlan.getBeneficiary().getId();

                    Optional<Client> client = clientRepository.findById(clientId);
                    if (client.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }

                    // Check if user is dead.
                    Client currentClient = client.get();
                    if (!currentClient.isAlive()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.USER_IS_DEAD);
                    }

                    // Check if user already have an active contract
                    if (subscribedPlanRepository.findByBeneficiaryIdAndActiveIsTrue(clientId).isPresent()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.USER_ALREADY_HAVE_ACTIVE_CONTRACT);
                    }

                    // Checks if contract is already active
                    if (currentSubscribedPlan.isActive()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.SUBSCRIPTION_ALREADY_ACTIVE);
                    }

                    currentSubscribedPlan.setActive(true);

                    if (commentary == null || commentary.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.COMMENTARY_IS_EMPTY);
                    }

                    Plan plan = planService.buildPlanWithAgeRanges(currentSubscribedPlan.getPlan());
                    currentSubscribedPlan.setPlan(planService.buildPlanWithAgeRanges(currentSubscribedPlan.getPlan()));

                    List<Client> dependentsToBeReactivated = clientService.getDependentsForReactivation(currentClient);

                    currentClient.setClientType(ClientTypeDTO.HOLDER.name());

                    // Updated plan value
                    List<Client> subscribedPlanUsers = new ArrayList<>();
                    subscribedPlanUsers.add(currentClient);

                    // Checks if a child is more than 24 years
                    // Used basic loop to avoid ConcurrentModificationException
                    for (int i = 0; i < dependentsToBeReactivated.size(); i++) {
                        Client dependent = dependentsToBeReactivated.get(i);
                        if (dependent.getClientType().equals(ClientTypeDTO.CHILD.name()) && AgeUtils.getAge(dependent.getBirthDay()) >= 24) {
                            dependentsToBeReactivated.remove(dependent);
                            planSubscriptionService.disableChildAndNotify(dependent);
                        }
                    }

                    subscribedPlanUsers.addAll(dependentsToBeReactivated);

                    double subscribedPlanValue = planService.calculatePrice(subscribedPlanUsers, plan.getPlanPriceTable().getAgeRanges(),plan.getDependentPriceTable().getAgeRanges());

                    currentSubscribedPlan.setValue(subscribedPlanValue);

                    // Must set this to now, otherwise it will be cancelled again
                    currentSubscribedPlan.setLastPayment(LocalDateTime.now());

                    currentSubscribedPlan.setDefault(false);

                    planSubscriptionService.reactivateContract(currentSubscribedPlan, subscribedPlanUsers, currentClient);

                    // specific logging for reactivated contracts.
                    registerLog(commentary, farolUser.get(), currentSubscribedPlan);

                    // user history logging.
                    final String logMessage = LogMessageBuilder.buildReactivatedContractMessage(currentClient.getName(),
                            currentClient.getCPF(), commentary, currentSubscribedPlan.getPlan().getId());
                    logService.logClientHistory(ClientActionTypeDTO.GOT_CONTRACT_REACTIVATED, logMessage, currentClient.getCPF(), currentClient.getName());

                    CommonResponseWithSubscriptionInformation commonResponseWithSubscriptionInformation =
                            new CommonResponseWithSubscriptionInformation(StatusCodes.Success.Plan.CONTRACT_REACTIVATED, currentSubscribedPlan);
                    responsePromise.complete(commonResponseWithSubscriptionInformation);
                });
        return responsePromise;
    }

    // Logging for contract reactivation.
    private void registerLog(String commentary, FarolUser farolUser, SubscribedPlan subscribedPlan) {
        LogSubscribedPlan log = new LogSubscribedPlan();
        log.setCommentary(commentary);
        log.setSubscribedPlan(subscribedPlan);
        log.setReactivationDate(LocalDateTime.now());
        log.setUser(farolUser);

        logSubscribedPlanRepository.save(log);
    }
}
