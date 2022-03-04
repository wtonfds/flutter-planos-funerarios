package br.com.monitoratec.farol.graphql.resolvers.mutation.user;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.user.CommonResponseWithClientInformation;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.sql.model.user.Client;
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
public class SendAuthTokenImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendAuthTokenImpl.class);

    private final ClientRepository clientRepository;

    public SendAuthTokenImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public CompletableFuture<CommonResponseWithClientInformation> sendAuthToken(
        String authToken,
        DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithClientInformation> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    Long clientId = cachedTrustedToken.userCachedInfo.entityID;
                    Optional<Client> clientOptional = clientRepository.findByIdAndActiveTrue(clientId);

                    if (clientOptional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }
                    Client holder = clientOptional.get();

                    if (!authToken.equals(holder.getAuthCode())){
                       throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.AUTH_TOKEN_NOT_VALID);
                    }

                    holder.setAuthorized(true);
                    clientRepository.save(holder);

                    CommonResponseWithClientInformation commonResponse =
                            new CommonResponseWithClientInformation(StatusCodes.Success.User.UPDATED_USER_SUCCESSFULLY, holder);

                    responsePromise.complete(commonResponse);
                });
        return responsePromise;
    }
}
