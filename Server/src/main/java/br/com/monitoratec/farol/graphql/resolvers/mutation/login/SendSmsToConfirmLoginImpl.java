package br.com.monitoratec.farol.graphql.resolvers.mutation.login;

import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.common.JustCommonResponse;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.service.sms.SmsService;
import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
import br.com.monitoratec.farol.utils.code.SmsTokenUtils;
import br.com.monitoratec.farol.utils.data.CpfUtils;
import br.com.monitoratec.farol.utils.password.PasswordUtils;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SendSmsToConfirmLoginImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendSmsToConfirmLoginImpl.class);

    private final SmsService smsService;
    private final ClientRepository clientRepository;

    public SendSmsToConfirmLoginImpl(SmsService smsService, ClientRepository clientRepository) {
        this.smsService = smsService;
        this.clientRepository = clientRepository;
    }

    public JustCommonResponse sendSmsToConfirmLogin(String cpf) {
        super.logRequest(LOGGER, this);
        final String message = "Vida Plano: Este é seu código de confirmação: ";

        if (!CpfUtils.isValidCpf(cpf)) {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.INVALID_CPF);
        }

        Optional<Client> inactiveDependentOptional = clientRepository.findByCPFAndActiveFalse(CpfUtils.getOnlyNumbers(cpf));

        // If dependent is active or is not in database
        if (inactiveDependentOptional.isEmpty()) {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
        }

        Client dependent = inactiveDependentOptional.get();

        Optional<Client> optionalHolder = clientRepository.findByIdAndActiveTrue(dependent.getHolder().getId());

        if (optionalHolder.isEmpty()) {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
        }

        final String phone = optionalHolder.get().getTelephone();
        final String confirmationCode = new PasswordUtils().generateRandomAuthToken();

        dependent.setSmsCode(confirmationCode);

        clientRepository.save(dependent);

        smsService.send(phone, message + confirmationCode);

        return new JustCommonResponse(StatusCodes.Success.User.SMS_SENT);
    }

}
