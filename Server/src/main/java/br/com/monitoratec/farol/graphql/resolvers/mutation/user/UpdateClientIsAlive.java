package br.com.monitoratec.farol.graphql.resolvers.mutation.user;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.user.CommonResponseWithClientInformation;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
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

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class UpdateClientIsAlive extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateClientIsAlive.class);

    private final ClientRepository clientRepository;
    private final SubscribedPlanRepository subscribedPlanRepository;
    private final PlanSubscriptionService planSubscriptionService;

    public UpdateClientIsAlive(ClientRepository clientRepository, SubscribedPlanRepository subscribedPlanRepository, PlanSubscriptionService planSubscriptionService) {
        this.clientRepository = clientRepository;
        this.subscribedPlanRepository = subscribedPlanRepository;
        this.planSubscriptionService = planSubscriptionService;
    }

    public CompletableFuture<CommonResponseWithClientInformation> updateClientIsAlive(Long id, DataFetchingEnvironment dataFetchingEnvironment) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithClientInformation> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {

                    Optional<Client> clientOptional = clientRepository.findByIdAndActiveTrue(id);
                    if (clientOptional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }
                    if (!clientOptional.get().isAlive()) { // already dead
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_IS_ALREADY_DEAD);
                    }

                    Client client = clientOptional.get();
                    Optional<SubscribedPlan> subscribedPlan = subscribedPlanRepository.findByBeneficiaryIdAndActiveIsTrue(client.getId());

                    // User dont have an active plan
                    if (subscribedPlan.isEmpty()) {
                        client.setAlive(false);
                        client.setActive(false);
                        client.setDeleted(true);
                        clientRepository.save(client);
                    } else {
                        planSubscriptionService.cancelContractByDeath(subscribedPlan.get());
                    }

                    CommonResponseWithClientInformation commonResponseWithClientInformation = new CommonResponseWithClientInformation(StatusCodes.Success.User.UPDATED_USER_SET_DEAD, client);
                    responsePromise.complete(commonResponseWithClientInformation);
                });
        return responsePromise;
    }
}
