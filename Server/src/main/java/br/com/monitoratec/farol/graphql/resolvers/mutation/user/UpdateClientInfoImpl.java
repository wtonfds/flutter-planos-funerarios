package br.com.monitoratec.farol.graphql.resolvers.mutation.user;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.customTypes.Date;
import br.com.monitoratec.farol.graphql.customTypes.Email;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.dtos.user.GenderDTO;
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
public class UpdateClientInfoImpl extends BaseResolver implements GraphQLMutationResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateClientInfoImpl.class);

    private final ClientRepository clientRepository;

    public UpdateClientInfoImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public CompletableFuture<CommonResponseWithClientInformation> updateClientInfo(
            Email email,
            String name,
            String telephone,
            Date birthDay,
            String rg,
            String oneSignalPlayerId,
            GenderDTO gender,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithClientInformation> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    // Validated access
                    Optional<Client> clientOptional = clientRepository.findByIdAndActiveTrue(cachedTrustedToken.userCachedInfo.entityID);
                    if (clientOptional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }

                    Client client = clientOptional.get();
                    if (email != null) {
                        client.setEmail(email.getEmailString());
                    }
                    if (name != null) {
                        client.setName(name);
                    }
                    if (telephone != null) {
                        client.setTelephone(telephone);
                    }
                    if (birthDay != null) {
                        client.setBirthDay(birthDay.getDate());
                    }
                    if (rg != null) {
                        client.setRG(rg);
                    }
                    if (oneSignalPlayerId != null) {
                        client.setOneSignalPlayerId(oneSignalPlayerId);
                    }
                    if (gender != null) {
                        client.setGender(gender.name());
                    }

                    client = clientRepository.save(client);

                    CommonResponseWithClientInformation commonResponseWithClientInformation = new CommonResponseWithClientInformation(StatusCodes.Success.User.GOT_USER, client);
                    responsePromise.complete(commonResponseWithClientInformation);
                });
        return responsePromise;
    }

}
