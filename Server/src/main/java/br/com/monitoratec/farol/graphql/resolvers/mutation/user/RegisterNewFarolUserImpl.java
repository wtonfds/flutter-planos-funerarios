package br.com.monitoratec.farol.graphql.resolvers.mutation.user;

import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.dtos.permission.PermissionTypeDTO;
import br.com.monitoratec.farol.graphql.model.input.login.LoginViaEmailInput;
import br.com.monitoratec.farol.graphql.model.responses.user.CommonResponseWithFarolUserInformation;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.sql.model.permission.Permission;
import br.com.monitoratec.farol.sql.model.user.FarolUser;
import br.com.monitoratec.farol.sql.repository.permission.PermissionRepository;
import br.com.monitoratec.farol.sql.repository.user.FarolUserRepository;
import br.com.monitoratec.farol.utils.data.CpfUtils;
import br.com.monitoratec.farol.utils.password.PasswordUtils;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RegisterNewFarolUserImpl extends BaseResolver implements GraphQLMutationResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterNewClientImpl.class);

    private final FarolUserRepository farolUserRepository;
    private final PasswordUtils passwordUtils;
    private final PermissionRepository permissionRepository;

    public RegisterNewFarolUserImpl(FarolUserRepository farolUserRepository, PasswordUtils passwordUtils, PermissionRepository permissionRepository) {
        this.farolUserRepository = farolUserRepository;
        this.passwordUtils = passwordUtils;
        this.permissionRepository = permissionRepository;
    }

    public CommonResponseWithFarolUserInformation registerNewFarolUser(
            LoginViaEmailInput loginViaEmailData,
            String cpf,
            String name,
            String telephone,
            List<PermissionTypeDTO> permissionTypeDTOArrayList
    ) {
        super.logRequest(LOGGER, this);

        FarolUser farolUser = new FarolUser();
        farolUser.setName(name);
        farolUser.setTelephone(telephone);
        if (CpfUtils.isValidCpf(cpf)) {
            farolUser.setCPF(CpfUtils.getOnlyNumbers(cpf));
        } else {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.INVALID_CPF);
        }

        List<String> userPermissionInputList = permissionTypeDTOArrayList.stream().map(Enum::name).collect(Collectors.toList());
        List<Permission> permissionList = permissionRepository.findAll();

        List<Permission> userPermissionList = permissionList.stream().filter(x -> userPermissionInputList.contains(x.getName())).collect(Collectors.toList());
        final String email = loginViaEmailData.getEmail().stringValue();

        farolUser.setPermissionList(userPermissionList);

        Optional<FarolUser> farolUserOptionalOptional = farolUserRepository.findByEmailIgnoreCaseAndActiveTrue(email);
        if (farolUserOptionalOptional.isPresent()) {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.EMAIL_ALREADY_USED);
        }

        farolUser.setEmail(email);
        farolUser.setPasswordHash(passwordUtils.hashPassword(loginViaEmailData.getPassword()));

        farolUser.setActive(true);
        Long maxAgentNumber = farolUserRepository.maxAgentNumber();
        if (maxAgentNumber == null) {
            maxAgentNumber = 0L;
        }
        farolUser.setAgentNumber(maxAgentNumber + 1L);

        farolUser = farolUserRepository.save(farolUser);

        return new CommonResponseWithFarolUserInformation(StatusCodes.Success.User.REGISTERED_NEW_CUSTOMER, farolUser);
    }
}
