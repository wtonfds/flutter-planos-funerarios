package br.com.monitoratec.farol.graphql.resolvers.query.user;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.user.CommonResponseWithClientInformation;
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
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class GetCurrentLoggedClientImpl extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetCurrentLoggedClientImpl.class);

    private final ClientRepository clientRepository;

    public GetCurrentLoggedClientImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public CompletableFuture<CommonResponseWithClientInformation> getCurrentLoggedClient(DataFetchingEnvironment dataFetchingEnvironment) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithClientInformation> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    // Validated access
                    Optional<Client> clientOptional = clientRepository.findByIdAndActiveTrue(cachedTrustedToken.userCachedInfo.entityID);
                    if (clientOptional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }

                    CommonResponseWithClientInformation commonResponseWithClientInformation = new CommonResponseWithClientInformation(StatusCodes.Success.User.GOT_USER, clientOptional.get());
                    responsePromise.complete(commonResponseWithClientInformation);
                });
        return responsePromise;
    }
}
