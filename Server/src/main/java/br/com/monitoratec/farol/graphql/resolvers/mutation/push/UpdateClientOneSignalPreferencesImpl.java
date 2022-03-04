package br.com.monitoratec.farol.graphql.resolvers.mutation.push;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.dtos.push.PushPreferencesTypeDTO;
import br.com.monitoratec.farol.graphql.model.responses.push.CommonResponseWithOneSignalPreferences;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.sql.model.push.Preferences;
import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.repository.push.PreferencesRepository;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class UpdateClientOneSignalPreferencesImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateClientOneSignalPreferencesImpl.class);

    private final ClientRepository clientRepository;
    private final PreferencesRepository preferencesRepository;

    public UpdateClientOneSignalPreferencesImpl(ClientRepository clientRepository, PreferencesRepository preferencesRepository) {
        this.clientRepository = clientRepository;
        this.preferencesRepository = preferencesRepository;
    }

    public CompletableFuture<CommonResponseWithOneSignalPreferences> updateOneSignalPreferences(
            List<PushPreferencesTypeDTO> preferences,
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

                    List<String> oneSignalPreferences = preferences.stream().map(Enum::name).collect(Collectors.toList());
                    List<Preferences> permissions = preferencesRepository.findAll();

                    List<Preferences> userPermissionList = permissions.stream().filter(x -> oneSignalPreferences.contains(x.getName())).collect(Collectors.toList());

                    Client client = optionalClient.get();
                    client.setOneSignalPreferenceList(userPermissionList);

                    clientRepository.save(client);

                    LOGGER.info("Update user one signal preferences");

                    CommonResponseWithOneSignalPreferences response = new CommonResponseWithOneSignalPreferences(StatusCodes.Success.User.UPDATED_ONE_SIGNAL_PREFERENCES_SUCCESSFULLY, preferences);
                    responsePromise.complete(response);
                });
        return responsePromise;
    }

}
