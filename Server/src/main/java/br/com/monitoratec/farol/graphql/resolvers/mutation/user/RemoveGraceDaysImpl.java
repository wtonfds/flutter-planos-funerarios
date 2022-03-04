package br.com.monitoratec.farol.graphql.resolvers.mutation.user;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.user.CommonResponseWithClientInformation;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
import br.com.monitoratec.farol.sql.repository.user.FarolUserRepository;
import br.com.monitoratec.farol.utils.data.CpfUtils;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class RemoveGraceDaysImpl extends BaseResolver implements GraphQLMutationResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoveGraceDaysImpl.class);

    private final ClientRepository clientRepository;
    private final FarolUserRepository farolUserRepository;

    public RemoveGraceDaysImpl(ClientRepository clientRepository, FarolUserRepository farolUserRepository) {
        this.clientRepository = clientRepository;
        this.farolUserRepository = farolUserRepository;
    }

    public CompletableFuture<CommonResponseWithClientInformation> removeGraceDays(
            String cpf,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithClientInformation> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {

                    if (farolUserRepository.findById(cachedTrustedToken.userCachedInfo.entityID).isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }

                    if (!CpfUtils.isValidCpf(cpf)) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.INVALID_CPF);
                    }

                    Optional<Client> optionalClient = clientRepository.findByCPF(CpfUtils.getOnlyNumbers(cpf));
                    if (optionalClient.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }

                    Client client = optionalClient.get();
                    client.setGracePeriod(LocalDate.now());

                    client = clientRepository.save(client);

                    CommonResponseWithClientInformation commonResponseWithClientInformation =
                            new CommonResponseWithClientInformation(StatusCodes.Success.User.USER_GRACE_DAYS_REMOVED, client);

                    responsePromise.complete(commonResponseWithClientInformation);
                });
        return responsePromise;
    }


}
