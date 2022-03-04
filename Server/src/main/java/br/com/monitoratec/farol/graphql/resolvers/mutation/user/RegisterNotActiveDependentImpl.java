package br.com.monitoratec.farol.graphql.resolvers.mutation.user;

import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.input.user.ClientDependentUpdateInput;
import br.com.monitoratec.farol.graphql.model.responses.user.CommonResponseWithClientInformation;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
import br.com.monitoratec.farol.utils.data.CpfUtils;
import br.com.monitoratec.farol.utils.password.PasswordUtils;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RegisterNotActiveDependentImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterNotActiveDependentImpl.class);

    private final ClientRepository clientRepository;

    public RegisterNotActiveDependentImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public CommonResponseWithClientInformation updateDependentInformation(String cpf, ClientDependentUpdateInput input) {
        super.logRequest(LOGGER, this);

        Optional<Client> optionalClient = clientRepository.findByCPF(CpfUtils.getOnlyNumbers(cpf));

        if (optionalClient.isEmpty()) {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
        }

        if (optionalClient.get().isActive()) {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.DEPENDENT_SHOULD_NOT_BE_ACTIVE);
        }

        Client client = optionalClient.get();

        client.setTelephone(input.getTelephone());
        client.setName(input.getName());
        client.setEmail(input.getEmail());
        client.setRG(input.getRg());
        client.setActive(true);
        client.setPasswordHash(new PasswordUtils().hashPassword(input.getPasswordHash()));

        if (input.getGender() != null) {
            client.setGender(input.getGender().name());
        }

        client = clientRepository.save(client);

        return new CommonResponseWithClientInformation(StatusCodes.Success.User.UPDATED_DEPENDENT, client);
    }
}
