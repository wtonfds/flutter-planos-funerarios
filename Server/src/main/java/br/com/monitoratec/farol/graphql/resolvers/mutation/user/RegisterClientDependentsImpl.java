package br.com.monitoratec.farol.graphql.resolvers.mutation.user;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.dtos.payment.PaymentTypeDTO;
import br.com.monitoratec.farol.graphql.model.dtos.user.ClientTypeDTO;
import br.com.monitoratec.farol.graphql.model.input.user.ClientDependentsInput;
import br.com.monitoratec.farol.graphql.model.responses.user.CommonResponseWithClientDependentsInformation;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.document.DocumentService;
import br.com.monitoratec.farol.service.log.LogService;
import br.com.monitoratec.farol.service.payment.PaymentService;
import br.com.monitoratec.farol.service.plan.PlanService;
import br.com.monitoratec.farol.service.plan.PlanSubscriptionService;
import br.com.monitoratec.farol.sql.model.plan.Plan;
import br.com.monitoratec.farol.sql.model.plan.SubscribedPlan;
import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.repository.subscribedPlan.SubscribedPlanRepository;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
import br.com.monitoratec.farol.utils.data.AgeUtils;
import br.com.monitoratec.farol.utils.data.CpfUtils;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class RegisterClientDependentsImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterClientDependentsImpl.class);

    private final ClientRepository clientRepository;
    private final SubscribedPlanRepository subscribedPlanRepository;
    private final LogService logService;
    private final DocumentService documentService;
    private final PlanService planService;
    private final PlanSubscriptionService planSubscriptionService;
    private final PaymentService paymentService;

    public RegisterClientDependentsImpl(ClientRepository clientRepository, SubscribedPlanRepository subscribedPlanRepository,
                                        PlanService planService, PlanSubscriptionService planSubscriptionService,
                                        PaymentService paymentService, LogService logService, DocumentService documentService) {
        this.clientRepository = clientRepository;
        this.subscribedPlanRepository = subscribedPlanRepository;
        this.planService = planService;
        this.planSubscriptionService = planSubscriptionService;
        this.paymentService = paymentService;
        this.logService = logService;
        this.documentService = documentService;
    }

    public CompletableFuture<CommonResponseWithClientDependentsInformation> registerClientDependents(
            List<ClientDependentsInput> clientDependentsInputArray,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);
        CompletableFuture<CommonResponseWithClientDependentsInformation> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {

                    Optional<Client> clientOptional = clientRepository.findByIdAndActiveTrue(cachedTrustedToken.userCachedInfo.entityID);
                    if (clientOptional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }

                    final Client holder = clientOptional.get();

                    // Holder can register dependents only if has an active plan
                    Optional<SubscribedPlan> currentSubscribedPlan = subscribedPlanRepository.findByBeneficiaryIdAndActiveIsTrue(holder.getId());
                    if (currentSubscribedPlan.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.INVALID_SUBSCRIBED_PLAN);
                    }

                    SubscribedPlan subscribedPlan = currentSubscribedPlan.get();

                    double currentSubscribedPlanPrice = subscribedPlan.getValue();

                    Plan currentPlan = planService.buildPlanWithAgeRanges(subscribedPlan.getPlan());

                    final List<Client> dependents = new ArrayList<>();
                    final LocalDateTime now = LocalDateTime.now();

                    for (ClientDependentsInput entry : clientDependentsInputArray) {
                        Optional<Client> optionalDependent = clientRepository.findByCPF(CpfUtils.getOnlyNumbers(entry.getCpf()));
                        Client dependent;

                        // Check if dependent exists, if not, create one or try to use him in this plan
                        if (optionalDependent.isEmpty()) {
                            dependent = new Client();
                        } else { // Check if the dependent can be dependent of this plan
                            dependent = optionalDependent.get();
                            if (!dependent.getClientType().equals(ClientTypeDTO.GUEST.name())) {
                                throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_SHOULD_BE_GUEST);
                            }
                            if (dependent.getHolder() != null) {
                                throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_SHOULD_NOT_HAVE_HOLDER);
                            }
                            if (!dependent.isAlive()) {
                                throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_IS_ALREADY_DEAD);
                            }
                        }

                        dependent.setHolder(holder);

                        if (entry.getClientType().equals(ClientTypeDTO.CHILD) && AgeUtils.getAge(entry.getBirthday().getDate()) >= 24) {
                            throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.CHILD_AGE_INVALID);
                        }

                        dependent.setClientType(entry.getClientType().name());
                        dependent.setAlive(true);

                        if (CpfUtils.isValidCpf(entry.getCpf())) {
                            dependent.setCPF(CpfUtils.getOnlyNumbers(entry.getCpf()));
                        } else {
                            throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.INVALID_CPF);
                        }

                        // should add the grace days to the adding dependent
                        if (entry.getClientType().equals(ClientTypeDTO.EXTRA_DEPENDENT)) {
                            dependent.setGracePeriod(addDays(subscribedPlan.getPlan().getGracePeriodExtraDependents()));
                            dependent.setAddedAfterAnticipation(true);
                        } else {
                            dependent.setGracePeriod(addDays(subscribedPlan.getPlan().getGracePeriod()));
                        }
                        if (entry.getDocumentsInput() != null) {
                            dependent.setDocumentsInput(entry.getDocumentsInput());
                        }
                        dependent.setRG(entry.getRg());
                        dependent.setTelephone(entry.getTelephone());
                        dependent.setBirthDay(entry.getBirthday().getDate());
                        dependent.setName(entry.getName());
                        dependent.setCreatedAt(now);
                        dependent.setDeleted(false);
                        dependent.setGender(entry.getGender().name());
                        dependents.add(dependent);
                    }

                    documentService.saveDependentsDocuments(dependents);

                    List<Client> extraDependents = dependents.stream().filter(dependent -> dependent.getClientType().equals(ClientTypeDTO.EXTRA_DEPENDENT.name())).collect(Collectors.toList());

                    if (!extraDependents.isEmpty()){
                        currentSubscribedPlanPrice =
                                planService
                                    .calculatePrice(
                                            extraDependents, currentPlan.getPlanPriceTable().getAgeRanges(),
                                        currentPlan.getDependentPriceTable().getAgeRanges()
                                    );
                        subscribedPlan.setValue(currentSubscribedPlanPrice);
                        subscribedPlan.setAnticipationHaveDependent(true);
                        subscribedPlan.setAnticipationLastPayment(now);

                        paymentService.resubscribe(subscribedPlan, holder, dependents);
                    } else {
                        planSubscriptionService.addDependents(subscribedPlan, dependents, holder);
                    }

                    CommonResponseWithClientDependentsInformation commonResponseWithClientDependentsInformation =
                            new CommonResponseWithClientDependentsInformation(StatusCodes.Success.User.REGISTERED_DEPENDENTS, dependents);

                    responsePromise.complete(commonResponseWithClientDependentsInformation);
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
