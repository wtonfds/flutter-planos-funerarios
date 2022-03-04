package br.com.monitoratec.farol.graphql.resolvers.mutation.user;

import br.com.monitoratec.farol.graphql.customTypes.Date;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.dtos.user.ClientRecoveryPasswordTypeDTO;
import br.com.monitoratec.farol.graphql.model.responses.common.JustCommonResponse;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.service.sms.SmsService;
import br.com.monitoratec.farol.service.user.ClientService;
import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
import br.com.monitoratec.farol.utils.data.DataUtils;
import br.com.monitoratec.farol.utils.password.PasswordUtils;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class ResetClientPasswordImpl extends BaseResolver implements GraphQLMutationResolver {

    private final SmsService smsService;

    private final PasswordUtils passwordUtils;

    private final ClientRepository clientRepository;

    private final ClientService clientService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ResetClientPasswordImpl.class);

    public ResetClientPasswordImpl(
            SmsService smsService,
            ClientRepository clientRepository,
            PasswordUtils passwordUtils,
            ClientService clientService) {
        this.smsService = smsService;
        this.clientRepository = clientRepository;
        this.passwordUtils = passwordUtils;
        this.clientService = clientService;
    }

    public JustCommonResponse resetClientPassword(String clientCpf, String email, Date birthday, ClientRecoveryPasswordTypeDTO recoveryPasswordType) {
        super.logRequest(LOGGER, this);

        Client client = clientRepository.findByCPF(clientCpf)
                .orElseThrow(() -> new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND));

        if (!client.getEmail().equals(email)) {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.EMAIL_OR_BIRTHDAY_DONT_MATCH);
        }

        if (!birthday.getDate().isEqual(client.getBirthDay())) {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.EMAIL_OR_BIRTHDAY_DONT_MATCH);
        }

        switch (recoveryPasswordType) {
            case SMS:
                return this.recoverBySMS(client);
            case EMAIL:
                return this.recoverByEmail(client);
            default:
                throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.METHOD_NOT_YET_IMPLEMENTED);
        }
    }

    private JustCommonResponse recoverBySMS(Client client) {
        final String telephone = client.getTelephone();
        final String telephoneNumber = DataUtils.onlyNumber(telephone);

        if (telephone.isEmpty() || telephoneNumber.isBlank()) {
            return new JustCommonResponse(StatusCodes.Error.User.IMPOSSIBLE_TO_RECOVER_PASSWORD);
        }

        final String password = passwordUtils.generateRandom();
        String smsRecoverCode = "Vida Plano: Essa é sua senha temporária: " + password;
        smsService.send(telephoneNumber, smsRecoverCode);
        return this.setClientTemporaryPassword(client, password);
    }

    private JustCommonResponse setClientTemporaryPassword(Client client, String password) {
        client.setTemporaryPassword(true);
        client.setPasswordHash(passwordUtils.hashPassword(password));
        clientRepository.save(client);
        return new JustCommonResponse(StatusCodes.Success.User.TEMPORARY_PASSWORD_RESET);
    }

    private JustCommonResponse recoverByEmail(Client client) {
        final String password = passwordUtils.generateRandom();
        clientService.sendTemporaryPasswordByEmail(client, password);
        return this.setClientTemporaryPassword(client, password);
    }
}
