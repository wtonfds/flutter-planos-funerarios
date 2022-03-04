package br.com.monitoratec.farol.graphql.resolvers.mutation.login;

import br.com.monitoratec.farol.auth.managers.AuthManager;
import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.input.login.LoginViaCPFInput;
import br.com.monitoratec.farol.graphql.model.responses.login.CommonResponseForLogin;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.sql.interfaces.user.LoggableViaCPF;
import br.com.monitoratec.farol.sql.model.plan.SubscribedPlan;
import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.model.user.FarolUser;
import br.com.monitoratec.farol.sql.model.user.SystemUser;
import br.com.monitoratec.farol.sql.repository.subscribedPlan.SubscribedPlanRepository;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
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

@Component
public class LoginImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginImpl.class);
    public static final long SESSION_DURATION_MILLIS = 1000L * 60 * 60 * 24 * 30;

    private final ClientRepository clientRepository;
    private final FarolUserRepository farolUserRepository;
    private final AuthManager authManager;
    private final PasswordUtils passwordUtils;
    private final SubscribedPlanRepository subscribedPlanRepository;

    public LoginImpl(ClientRepository clientRepository, FarolUserRepository farolUserRepository, AuthManager authManager, PasswordUtils passwordUtils, SubscribedPlanRepository subscribedPlanRepository) {
        this.clientRepository = clientRepository;
        this.farolUserRepository = farolUserRepository;
        this.authManager = authManager;
        this.passwordUtils = passwordUtils;
        this.subscribedPlanRepository = subscribedPlanRepository;
    }

    public CommonResponseForLogin login(
//            LoginViaEmailInput loginViaEmailInfo,
            LoginViaCPFInput loginViaCPFInput,
            AccessingEntity loggingInAs
    ) {
        super.logRequest(LOGGER, this);


        // Needs to be updated when the login is available via instagram
//        if(loginViaEmailInfo != null) {
//            Optional<Client> clientOptional = clientRepository.findByEmailIgnoreCaseAndActiveTrue(loginViaEmailInfo.getEmail().stringValue());
//            if (clientOptional.isPresent()) {
//                return this.loginViaEmail(clientOptional.get(), loginViaEmailInfo);
//            }
//        }
//        if(loginViaCPFInput != null){

        final String cpfWithOnlyNumbers = CpfUtils.getOnlyNumbers(loginViaCPFInput.getCpf()); // format the cpf to be only numbers
        switch (loggingInAs) {
            case FAROL_USER:
                Optional<FarolUser> farolUserOptional = farolUserRepository.findByCPFAndActiveTrue(cpfWithOnlyNumbers);
                if (farolUserOptional.isPresent()) {
                    return this.loginViaCPF(farolUserOptional.get(), loginViaCPFInput);
                }
                break;
            case CLIENT:
                Optional<Client> clientOptional = clientRepository.findByCPFAndActiveTrue(cpfWithOnlyNumbers);
                if (clientOptional.isPresent()) {
                    return this.loginViaCPF(clientOptional.get(), loginViaCPFInput);
                }
                break;
        }
//        }

        // If anyhow the login doesn't succeed
        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Login.LOGIN_INFO_INVALID);
    }

//    private CommonResponseForLogin loginViaEmail(SystemUser systemUser, LoginViaEmailInput loginViaEmailInfo) {
//        LoggableViaEmail systemUserViaEmail = (LoggableViaEmail) systemUser;
//
//        String password = loginViaEmailInfo.getPassword();
//
//        if (!passwordUtils.doesPasswordMatch(systemUserViaEmail.getPasswordHash(), password)) {
//            // The password doesn't match!
//            throw new ErrorOnProcessingRequestException(StatusCodes.Error.Login.LoginInfoInvalid);
//        }
//
//        // The password is right! ;)
//        String newToken = authManager.createNewSessionForUser(systemUser, SESSION_DURATION_MILLIS);
//        return new CommonResponseForLogin(StatusCodes.Success.Login.LogedIn, newToken, systemUser);
//    }

    private CommonResponseForLogin loginViaCPF(SystemUser systemUser, LoginViaCPFInput loginViaCPFInput) {
        LoggableViaCPF loggableViaCPF = (LoggableViaCPF) systemUser;

        String password = loginViaCPFInput.getPassword();


        if (!passwordUtils.doesPasswordMatch(loggableViaCPF.getPasswordHash(), password)) {
            // The password doesn't match!
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.Login.LOGIN_INFO_INVALID);
        }

        if (systemUser instanceof Client) {
            List<SubscribedPlan> defaultPlans = subscribedPlanRepository.findAllByBeneficiaryIdAndIsDefaultTrue(systemUser.getId());
            if (!defaultPlans.isEmpty()) {
                LOGGER.info("User " + systemUser.getName() + " with id " + systemUser.getId() + " tried to log in but was blocked because they are default");
                throw new ErrorOnProcessingRequestException(StatusCodes.Error.Login.CLIENT_IS_DEFAULT);
            }
        }

        // The password is right! ;)
        String newToken = authManager.createNewSessionForUser(systemUser, SESSION_DURATION_MILLIS);
        return new CommonResponseForLogin(StatusCodes.Success.Login.LOGGED_IN, newToken, systemUser);
    }

}
