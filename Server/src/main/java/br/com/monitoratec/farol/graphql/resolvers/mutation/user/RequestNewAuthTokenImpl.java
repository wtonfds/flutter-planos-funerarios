package br.com.monitoratec.farol.graphql.resolvers.mutation.user;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.sms.SmsService;
import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
import br.com.monitoratec.farol.utils.data.DataUtils;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class RequestNewAuthTokenImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestNewAuthTokenImpl.class);

    private final ClientRepository clientRepository;
    private final SmsService smsService;
    private final String smsBody;

    public RequestNewAuthTokenImpl(
            ClientRepository clientRepository,
            SmsService smsService,
            @Value("${sms.auth_token_sms_body}") String smsBody
    ) {
        this.clientRepository = clientRepository;
        this.smsService = smsService;
        this.smsBody = smsBody;
    }

    public CompletableFuture<CommonResponse> requestNewAuthToken(
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponse> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    Long clientId = cachedTrustedToken.userCachedInfo.entityID;
                    Optional<Client> clientOptional = clientRepository.findByIdAndActiveTrue(clientId);

                    if (clientOptional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }
                    Client holder = clientOptional.get();
                    if (holder.getAuthorized() == null || holder.getAuthorized()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_DOES_NOT_HAVE_AUTH_TOKEN);
                    }

                    smsService.send(DataUtils.onlyNumber(holder.getTelephone()), smsBody.replace("<authToken>", holder.getAuthCode()));

                    CommonResponse commonResponse = StatusCodes.Success.User.GOT_USER;
                    responsePromise.complete(commonResponse);
                });
        return responsePromise;
    }
}
