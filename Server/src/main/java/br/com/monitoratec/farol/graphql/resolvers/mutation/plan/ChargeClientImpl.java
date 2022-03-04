package br.com.monitoratec.farol.graphql.resolvers.mutation.plan;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.common.JustCommonResponse;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.mail.MailService;
import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
import br.com.monitoratec.farol.sql.repository.user.FarolUserRepository;
import br.com.monitoratec.farol.utils.data.EmailUtils;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class ChargeClientImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChargeClientImpl.class);

    private final ClientRepository clientRepository;
    private final FarolUserRepository farolUserRepository;
    private final MailService mailService;
    private final String mailFrom;
    private final String mailSubject;
    private final String mailBody;

    public ChargeClientImpl(ClientRepository clientRepository, FarolUserRepository farolUserRepository,
                            MailService mailService,
                            @Value("${mail.charge-client-from}") String mailFrom,
                            @Value("${mail.charge-client-subject}") String mailSubject,
                            @Value("${mail.charge-client-body}") String mailBody) {
        this.clientRepository = clientRepository;
        this.farolUserRepository = farolUserRepository;
        this.mailService = mailService;
        this.mailFrom = mailFrom;
        this.mailSubject = mailSubject;
        this.mailBody = mailBody;
    }

    public CompletableFuture<JustCommonResponse> chargeClient(
            Long clientId,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<JustCommonResponse> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {

                    if (farolUserRepository.findById(cachedTrustedToken.userCachedInfo.entityID).isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }

                    Optional<Client> optionalClient = clientRepository.findById(clientId);
                    if (optionalClient.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }

                    Client client = optionalClient.get();
                    final String clientEmail = client.getEmail();

                    if (clientEmail == null || !EmailUtils.isEmailValid(clientEmail)) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.INVALID_EMAIL);
                    }

                    final String newMailBody = mailBody.replace("<clientName>", client.getName());

                    mailService.send(mailFrom, clientEmail, mailSubject, newMailBody);
                    LOGGER.info("Email sent to: {} because the payment was not detected", clientEmail);

                    JustCommonResponse justCommonResponse = new JustCommonResponse(StatusCodes.Success.Plan.CHARGE_EMAIL_SENT_SUCCESSFULLY);
                    responsePromise.complete(justCommonResponse);

                });
        return responsePromise;
    }
}
