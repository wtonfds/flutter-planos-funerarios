package br.com.monitoratec.farol.graphql.resolvers.query.user;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.user.CommonResponseWithListClients;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class ListHoldersImpl extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListHoldersImpl.class);

    private ClientRepository clientRepository;

    public ListHoldersImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public CompletableFuture<CommonResponseWithListClients> listHolders(
            Long id,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithListClients> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    List<Client> clientList;
                    if (id == null) {
                        clientList = clientRepository.findAllHolders();
                    } else {
                        Client client = clientRepository.findByIdAndActiveTrue(id)
                                .orElseThrow(() -> new ErrorOnProcessingRequestException(StatusCodes.Error.Push.USERS_NOT_FOUND));
                        clientList = new ArrayList<>();
                        clientList.add(client);
                    }
                    responsePromise.complete(new CommonResponseWithListClients(StatusCodes.Success.User.GOT_CLIENT_SUCCESS, clientList));
                });
        return responsePromise;
    }
}
