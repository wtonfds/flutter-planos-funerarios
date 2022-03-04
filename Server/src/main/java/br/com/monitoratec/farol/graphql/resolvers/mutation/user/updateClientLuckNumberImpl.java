package br.com.monitoratec.farol.graphql.resolvers.mutation.user;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.user.CommonResponseWithClientInformation;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
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
public class updateClientLuckNumberImpl extends BaseResolver implements GraphQLMutationResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(updateClientLuckNumberImpl.class);

    private final ClientRepository clientRepository;
    private final SubscribedPlanRepository subscribedPlanRepository;

    public updateClientLuckNumberImpl(ClientRepository clientRepository, SubscribedPlanRepository subscribedPlanRepository) {
        this.clientRepository = clientRepository;
        this.subscribedPlanRepository = subscribedPlanRepository;
}

    public CompletableFuture<CommonResponseWithClientInformation> updateClientLuckNumber(
            Long id,
            String luckNumber,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithClientInformation> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    // Validated access
                    Optional<Client> clientOptional = clientRepository.findById(id);
                    if (clientOptional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }

                    Client client = clientOptional.get();

                    client.setLuckNumber(luckNumber);
                    
                    Optional<SubscribedPlan> optionalSubscribedPlan = subscribedPlanRepository.findByBeneficiaryIdAndActiveIsTrue(client.getId());
                    if (optionalSubscribedPlan.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.SUBSCRIBED_PLAN_NOT_FOUND);
                    }

                    SubscribedPlan subscribedPlan = optionalSubscribedPlan.get();

                    subscribedPlan.setLuckNumber(Long.parseLong(luckNumber));

                    client = clientRepository.save(client);
                    subscribedPlanRepository.save(subscribedPlan);

                    CommonResponseWithClientInformation commonResponseWithClientInformation = new CommonResponseWithClientInformation(StatusCodes.Success.User.GOT_USER, client);
                    responsePromise.complete(commonResponseWithClientInformation);
                });
        return responsePromise;
    }
}
