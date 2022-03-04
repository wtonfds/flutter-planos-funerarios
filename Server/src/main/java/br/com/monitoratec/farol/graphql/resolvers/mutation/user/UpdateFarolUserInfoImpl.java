package br.com.monitoratec.farol.graphql.resolvers.mutation.user;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.customTypes.Email;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.dtos.permission.PermissionTypeDTO;
import br.com.monitoratec.farol.graphql.model.input.login.PasswordUpdateInput;
import br.com.monitoratec.farol.graphql.model.responses.user.CommonResponseWithFarolUserInformation;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.sql.model.permission.Permission;
import br.com.monitoratec.farol.sql.model.user.FarolUser;
import br.com.monitoratec.farol.sql.repository.permission.PermissionRepository;
import br.com.monitoratec.farol.sql.repository.user.FarolUserRepository;
import br.com.monitoratec.farol.utils.data.CpfUtils;
import br.com.monitoratec.farol.utils.password.PasswordUtils;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class UpdateFarolUserInfoImpl extends BaseResolver implements GraphQLMutationResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateFarolUserInfoImpl.class);

    private final FarolUserRepository farolUserRepository;
    private final PasswordUtils passwordUtils;
    private final PermissionRepository permissionRepository;

    public UpdateFarolUserInfoImpl(FarolUserRepository farolUserRepository, PasswordUtils passwordUtils, PermissionRepository permissionRepository) {
        this.farolUserRepository = farolUserRepository;
        this.passwordUtils = passwordUtils;
        this.permissionRepository = permissionRepository;
    }

    public CompletableFuture<CommonResponseWithFarolUserInformation> updateFarolUserInfo(
            Email email,
            PasswordUpdateInput passwords,
            String name,
            String telephone,
            String cpf,
            List<PermissionTypeDTO> permissionTypeDTOArrayList,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithFarolUserInformation> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    // Validated access
                    Optional<FarolUser> farolUserOptional = farolUserRepository.findByIdAndActiveTrue(cachedTrustedToken.userCachedInfo.entityID);
                    if (farolUserOptional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }

                    List<String> userPermissionInputList = permissionTypeDTOArrayList.stream().map(Enum::name).collect(Collectors.toList());
                    List<Permission> permissions = permissionRepository.findAll();

                    List<Permission> userPermissionList = permissions.stream().filter(x -> userPermissionInputList.contains(x.getName())).collect(Collectors.toList());

                    FarolUser farolUser = farolUserOptional.get();
                    if (email != null) {
                        farolUser.setEmail(email.getEmailString());
                    }

                    farolUser.setPermissionList(userPermissionList);

                    if (passwords != null) {
                        if (!passwordUtils.doesPasswordMatch(farolUser.getPasswordHash(), passwords.getOldPassword())) {
                            throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.PASSWORD_DONT_MATCH);
                        }
                        farolUser.setPasswordHash(passwordUtils.hashPassword(passwords.getPassword()));
                    }

                    if (name != null) {
                        farolUser.setName(name);
                    }
                    if (telephone != null) {
                        farolUser.setTelephone(telephone);
                    }

                    if (cpf != null) {
                        if (CpfUtils.isValidCpf(cpf)) {
                            farolUser.setCPF(CpfUtils.getOnlyNumbers(cpf));
                        } else {
                            throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.INVALID_CPF);
                        }
                    }

                    farolUser = farolUserRepository.save(farolUser);

                    CommonResponseWithFarolUserInformation commonResponseWithFarolUserInformation = new CommonResponseWithFarolUserInformation(StatusCodes.Success.User.UPDATED_USER_SUCCESSFULLY, farolUser);
                    responsePromise.complete(commonResponseWithFarolUserInformation);
                });
        return responsePromise;
    }

}
