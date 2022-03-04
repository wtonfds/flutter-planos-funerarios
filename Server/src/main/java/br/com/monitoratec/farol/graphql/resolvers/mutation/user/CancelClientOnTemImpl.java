package br.com.monitoratec.farol.graphql.resolvers.mutation.user;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.common.JustCommonResponse;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
import br.com.monitoratec.farol.sql.repository.user.FarolUserRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Component
public class CancelClientOnTemImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(CancelClientOnTemImpl.class);

    private final ClientRepository clientRepository;
    private final FarolUserRepository farolUserRepository;

    public CancelClientOnTemImpl(ClientRepository clientRepository, FarolUserRepository farolUserRepository) {
        this.clientRepository = clientRepository;
        this.farolUserRepository = farolUserRepository;
    }


    @Deprecated(since = "10/08/2020")
    public CompletableFuture<JustCommonResponse> cancelClientOnTem(
            Long id,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<JustCommonResponse> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    throw new ErrorOnProcessingRequestException(StatusCodes.Error.Tem.TEM_INTEGRATION_NOT_BEING_USED);
//                    Optional<FarolUser> farolUserOptional = farolUserRepository.findByIdAndActiveTrue(cachedTrustedToken.userCachedInfo.entityID);
//                    if (farolUserOptional.isEmpty()) {
//                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
//                    }
//
//                    Optional<Client> clientOptional = clientRepository.findById(id);
//                    if (clientOptional.isEmpty()) {
//                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
//                    }
//
//                    final String userToken = clientOptional.get().getTemUserToken();
//                    temService.changeTemStatus(userToken, TemStatusTypeDTO.CANCEL);
//                    responsePromise.complete(response);
                });
        return responsePromise;
    }
}
