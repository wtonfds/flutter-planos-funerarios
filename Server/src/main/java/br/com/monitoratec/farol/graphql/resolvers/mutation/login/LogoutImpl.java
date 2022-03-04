package br.com.monitoratec.farol.graphql.resolvers.mutation.login;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.redis.repository.CachedTrustedTokenRepository;
import br.com.monitoratec.farol.sql.model.user.LoginSession;
import br.com.monitoratec.farol.sql.repository.user.LoginSessionRepository;
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
public class LogoutImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogoutImpl.class);

    private final LoginSessionRepository loginSessionRepository;
    private final CachedTrustedTokenRepository cachedTrustedTokenRepository;

    public LogoutImpl(LoginSessionRepository loginSessionRepository, CachedTrustedTokenRepository cachedTrustedTokenRepository) {
        this.loginSessionRepository = loginSessionRepository;
        this.cachedTrustedTokenRepository = cachedTrustedTokenRepository;
    }

    public CompletableFuture<CommonResponse> logout(DataFetchingEnvironment dataFetchingEnvironment) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponse> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    // Validated access

                    // Remove login session in case it exists
                    Optional<LoginSession> loginSessionOptional = loginSessionRepository.findByAuthToken(cachedTrustedToken.authToken);
                    if (loginSessionOptional.isPresent()) {
                        loginSessionRepository.delete(loginSessionOptional.get());
                    }

                    cachedTrustedTokenRepository.clear(cachedTrustedToken.authToken);

                    responsePromise.complete(StatusCodes.Success.Login.LOGGED_OUT);
                });
        return responsePromise;
    }
}
