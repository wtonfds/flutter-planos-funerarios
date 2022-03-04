package br.com.monitoratec.farol.graphql.resolvers.mutation.user;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.user.CommonResponseWithFarolUserInformation;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.sql.model.user.FarolUser;
import br.com.monitoratec.farol.sql.repository.user.FarolUserRepository;
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
public class DisableFarolUserImpl extends BaseResolver implements GraphQLMutationResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(DisableFarolUserImpl.class);

    private final FarolUserRepository farolUserRepository;

    public DisableFarolUserImpl(FarolUserRepository farolUserRepository) {
        this.farolUserRepository = farolUserRepository;
    }

    public CompletableFuture<CommonResponseWithFarolUserInformation> disableFarolUser(
            Long id,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithFarolUserInformation> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    // Validated access
                    Optional<FarolUser> farolUserOptional = farolUserRepository.findByIdWithPermissionsAndActiveTrue(id);
                    if (farolUserOptional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }

                    FarolUser farolUser = farolUserOptional.get();

                    farolUser.setActive(false);

                    farolUser = farolUserRepository.save(farolUser);

                    CommonResponseWithFarolUserInformation commonResponseWithFarolUserInformation = new CommonResponseWithFarolUserInformation(StatusCodes.Success.User.USER_DISABLED_SUCCESSFULLY, farolUser);
                    responsePromise.complete(commonResponseWithFarolUserInformation);
                });
        return responsePromise;
    }

}
