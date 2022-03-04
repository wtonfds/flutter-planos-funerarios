package br.com.monitoratec.farol.graphql.resolvers.query.push;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.dtos.push.PushPreferencesTypeDTO;
import br.com.monitoratec.farol.graphql.model.responses.push.CommonResponseWithOneSignalPreferences;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.sql.model.push.Preferences;
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
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class GetClientOneSignalPreferences extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetClientOneSignalPreferences.class);

    private final ClientRepository clientRepository;

    public GetClientOneSignalPreferences(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public CompletableFuture<CommonResponseWithOneSignalPreferences> getClientOneSignalPreferences(
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);
        CompletableFuture<CommonResponseWithOneSignalPreferences> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {

                    Optional<Client> optionalClient = clientRepository.findById(cachedTrustedToken.userCachedInfo.entityID);
                    if (optionalClient.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }

                    List<Preferences> preferences = clientRepository.findOneSignalPreferences(optionalClient.get().getId());

                    List<PushPreferencesTypeDTO> preferencesTypeDTOS = new ArrayList<>();
                    for (Preferences p : preferences) {
                        preferencesTypeDTOS.add(PushPreferencesTypeDTO.valueOf(p.getName()));
                    }

                    CommonResponseWithOneSignalPreferences response = new CommonResponseWithOneSignalPreferences(StatusCodes.Success.User.GOT_ONE_SIGNAL_PREFERENCES_SUCCESSFULLY, preferencesTypeDTOS);
                    responsePromise.complete(response);
                });
        return responsePromise;
    }
}
