package br.com.monitoratec.farol.graphql.resolvers.mutation.login;

import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.common.JustCommonResponse;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
import br.com.monitoratec.farol.utils.data.CpfUtils;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CheckCodeToConfirmLoginImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckCodeToConfirmLoginImpl.class);

    private final ClientRepository clientRepository;

    public CheckCodeToConfirmLoginImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public JustCommonResponse checkCodeToConfirmLogin(String cpf, String smsCode) {
        super.logRequest(LOGGER, this);

        if (!CpfUtils.isValidCpf(cpf)) {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.INVALID_CPF);
        }

        Optional<Client> inactiveDependentOptional = clientRepository.findByCPFAndActiveFalse(CpfUtils.getOnlyNumbers(cpf));

        if (inactiveDependentOptional.isEmpty()) {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
        }

        Client dependent = inactiveDependentOptional.get();

        if (dependent.getSmsCode() == null) {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.SMS_CODE_NOT_REGISTERED);
        }

        if (dependent.getSmsCode().equals(smsCode)) {
            dependent.setSmsCode(null);
            clientRepository.save(dependent);
            return new JustCommonResponse(StatusCodes.Success.User.SMS_CONFIRMED);
        } else {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.INVALID_SMS_CODE);
        }
    }
}
