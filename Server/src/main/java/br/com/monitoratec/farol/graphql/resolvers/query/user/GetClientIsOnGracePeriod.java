package br.com.monitoratec.farol.graphql.resolvers.query.user;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.user.CommonResponseWithClientGracePeriod;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class GetClientIsOnGracePeriod extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetClientIsOnGracePeriod.class);

    private final ClientRepository clientRepository;

    public GetClientIsOnGracePeriod(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public CompletableFuture<CommonResponseWithClientGracePeriod> getClientIsOnGracePeriod(DataFetchingEnvironment dataFetchingEnvironment) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithClientGracePeriod> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    Optional<Client> clientOptional = clientRepository.findById(cachedTrustedToken.userCachedInfo.entityID);

                    if (clientOptional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }

                    boolean isOnGracePeriod = isOnGracePeriod(clientOptional.get().getGracePeriod());
                    CommonResponseWithClientGracePeriod commonResponseWithClientGracePeriod =
                            new CommonResponseWithClientGracePeriod(StatusCodes.Success.User.GRACE_PERIOD_RETURNED_SUCCESSFULLY, isOnGracePeriod);

                    responsePromise.complete(commonResponseWithClientGracePeriod);
                });
        return responsePromise;
    }

    private boolean isOnGracePeriod(LocalDate gracePeriod) {
        if (gracePeriod == null) return true; // If no date is set, then it's on grace period.
        try {
            return !gracePeriod.isBefore(LocalDate.now());
        } catch (Exception ex) {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.INVALID_GRACE_DAYS_PERIOD);
        }
    }
}
