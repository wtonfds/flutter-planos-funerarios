package br.com.monitoratec.farol.graphql.resolvers.query.user;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.user.CommonResponseWithUserInformation;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.sql.model.user.SystemUser;
import br.com.monitoratec.farol.sql.repository.user.SystemUserRepository;
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
public class GetCurrentLoggedUserImpl extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetCurrentLoggedUserImpl.class);

    private final SystemUserRepository systemUserRepository;

    public GetCurrentLoggedUserImpl(SystemUserRepository systemUserRepository) {
        this.systemUserRepository = systemUserRepository;
    }

    public CompletableFuture<CommonResponseWithUserInformation> getCurrentLoggedUser(DataFetchingEnvironment dataFetchingEnvironment) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithUserInformation> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    // Validated access
                    Optional<SystemUser> systemUserOptional = systemUserRepository.findByIdAndActiveTrue(cachedTrustedToken.userCachedInfo.entityID);
                    if (systemUserOptional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }

                    CommonResponseWithUserInformation commonResponseWithUserInformation = new CommonResponseWithUserInformation(StatusCodes.Success.User.GOT_USER, systemUserOptional.get());
                    responsePromise.complete(commonResponseWithUserInformation);
                });
        return responsePromise;
    }
}
