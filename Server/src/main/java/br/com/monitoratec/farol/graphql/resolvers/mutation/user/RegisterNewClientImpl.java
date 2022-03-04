package br.com.monitoratec.farol.graphql.resolvers.mutation.user;

import br.com.monitoratec.farol.graphql.customTypes.Date;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.dtos.user.GenderDTO;
import br.com.monitoratec.farol.graphql.model.dtos.user.ClientTypeDTO;
import br.com.monitoratec.farol.graphql.model.input.login.LoginViaEmailInput;
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

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class RegisterNewClientImpl extends BaseResolver implements GraphQLMutationResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterNewClientImpl.class);

    private final ClientRepository clientRepository;
    private final PasswordUtils passwordUtils;

    public RegisterNewClientImpl(ClientRepository clientRepository, PasswordUtils passwordUtils) {
        this.clientRepository = clientRepository;
        this.passwordUtils = passwordUtils;
    }

    public CommonResponseWithClientInformation registerNewClient(
            LoginViaEmailInput loginViaEmailData,
            String cpf,
            String name,
            String telephone,
            Date birthDay,
            String rg,
            GenderDTO gender
    ) {
        super.logRequest(LOGGER, this);

        if (!CpfUtils.isValidCpf(cpf)) {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.INVALID_CPF);
        }

        String formatedCpf = CpfUtils.getOnlyNumbers(cpf);

        Optional<Client> userOptional = clientRepository.findByCPF(formatedCpf);
        Client client;
        if(userOptional.isEmpty()){
            client = new Client();
        } else {
            client = userOptional.get();
            if(!client.isDeleted()){
                throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.CPF_ALREADY_IN_USE);
            }
        }

        client.setCPF(formatedCpf);

        client.setName(name);
        client.setTelephone(telephone);


        client.setAlive(true);

        final String email = loginViaEmailData.getEmail().stringValue();

        Optional<Client> customerOptional = clientRepository.findByEmailIgnoreCaseAndActiveTrue(email);
        if (customerOptional.isPresent()) {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.EMAIL_ALREADY_USED);
        }

        client.setEmail(email);
        client.setPasswordHash(passwordUtils.hashPassword(loginViaEmailData.getPassword()));

        client.setActive(true);
        client.setDeleted(false);

        client.setBirthDay(birthDay.getDate());
        client.setCreatedAt(LocalDateTime.now());
        client.setClientType(ClientTypeDTO.GUEST.name()); // When registered, should be guest. After a subscribed plan, changes to HOLDER
        client.setRG(rg);
        client.setGender(gender.name());

        client = clientRepository.save(client);

        return new CommonResponseWithClientInformation(StatusCodes.Success.User.REGISTERED_NEW_CUSTOMER, client);
    }
}
