package br.com.monitoratec.farol.graphql.resolvers.mutation.push;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.push.CommonResponseWithPushResult;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.push.PushService;
import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class SendPushNotificationImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendPushNotificationImpl.class);

    private final ClientRepository clientRepository;
    private final PushService pushService;

    public SendPushNotificationImpl(ClientRepository clientRepository, PushService pushService) {
        this.clientRepository = clientRepository;
        this.pushService = pushService;
    }

    public CompletableFuture<CommonResponseWithPushResult> sendPushNotification(
            List<Long> clientsIds,
            String message,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        if (message.isEmpty()) {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.Push.MESSAGE_IS_EMPTY);
        }

        CompletableFuture<CommonResponseWithPushResult> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    final int clientIdsSize = clientsIds.size();
                    final List<String> oneSignalPlayerIds = new ArrayList<>(clientIdsSize);

                    for (Long id : clientsIds) {
                        Client client = clientRepository.findByIdAndActiveTrueAndOneSignalPlayerIdNotNull(id)
                                .orElseThrow(() -> new ErrorOnProcessingRequestException(StatusCodes.Error.Push.USERS_NOT_FOUND));

                        oneSignalPlayerIds.add(client.getOneSignalPlayerId());
                    }

                    String response = pushService.sendNotificationOneSignal(oneSignalPlayerIds, message);
                    JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();

                    final CommonResponseWithPushResult pushResponse;

                    if (jsonResponse.get("errors") == null && jsonResponse.get("error") == null) {
                        pushResponse = new CommonResponseWithPushResult(StatusCodes.Success.Push.PUSH_SENT_SUCCESSFULLY, jsonResponse.toString());
                    } else {
                        pushResponse = new CommonResponseWithPushResult(StatusCodes.Error.Push.ERROR_SENDING_PUSH, jsonResponse.toString());
                    }
                    responsePromise.complete(pushResponse);

                });
        return responsePromise;
    }
}
