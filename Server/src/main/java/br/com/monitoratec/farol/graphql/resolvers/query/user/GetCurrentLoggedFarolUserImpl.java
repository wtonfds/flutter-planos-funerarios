package br.com.monitoratec.farol.graphql.resolvers.query.user;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.user.CommonResponseWithFarolUserInformation;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.sql.model.user.FarolUser;
import br.com.monitoratec.farol.sql.repository.user.FarolUserRepository;
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
public class GetCurrentLoggedFarolUserImpl extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetCurrentLoggedFarolUserImpl.class);

    private final FarolUserRepository farolUserRepository;

    public GetCurrentLoggedFarolUserImpl(FarolUserRepository farolUserRepository) {
        this.farolUserRepository = farolUserRepository;
    }

    public CompletableFuture<CommonResponseWithFarolUserInformation> getCurrentLoggedFarolUser(DataFetchingEnvironment dataFetchingEnvironment) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithFarolUserInformation> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    // Validated access
                    Optional<FarolUser> farolUserOptional = farolUserRepository.findByIdWithPermissionsAndActiveTrue(cachedTrustedToken.userCachedInfo.entityID);
                    if (farolUserOptional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }

                    CommonResponseWithFarolUserInformation commonResponseWithFarolUserInformation = new CommonResponseWithFarolUserInformation(StatusCodes.Success.User.GOT_USER, farolUserOptional.get());
                    responsePromise.complete(commonResponseWithFarolUserInformation);
                });
        return responsePromise;
    }
}
