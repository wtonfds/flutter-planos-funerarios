package br.com.monitoratec.farol.graphql.resolvers.query.plan;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.user.CommonResponseWithClientDependentsInformation;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.sql.model.user.Client;
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
public class ListPlanDependentsImpl extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListPlanDependentsImpl.class);

    private ClientRepository clientRepository;

    public ListPlanDependentsImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public CompletableFuture<CommonResponseWithClientDependentsInformation> listPlanDependents(
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
                    Client holder = clientOptional.get();

                    List<Client> dependents = clientRepository.findAllByHolder(holder);

                    CommonResponseWithClientDependentsInformation commonResponseWithClientDependentsInformation =
                            new CommonResponseWithClientDependentsInformation(StatusCodes.Success.Plan.FOUND_DEPENDENTS_SUCCESSFULLY, dependents);
                    responsePromise.complete(commonResponseWithClientDependentsInformation);
                });
        return responsePromise;
    }
}
