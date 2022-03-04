package br.com.monitoratec.farol.graphql.resolvers.mutation.user;

import br.com.monitoratec.farol.graphql.customTypes.Email;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.user.CommonResponseWithTrialUserInformation;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.sql.model.user.TrialUser;
import br.com.monitoratec.farol.sql.repository.user.TrialUserRepository;
import br.com.monitoratec.farol.utils.data.EmailUtils;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RegisterNewTrialUserImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterNewTrialUserImpl.class);

    private final TrialUserRepository trialUserRepository;

    public RegisterNewTrialUserImpl(TrialUserRepository trialUserRepository) {
        this.trialUserRepository = trialUserRepository;
    }

    public CommonResponseWithTrialUserInformation registerNewTrialUser(String name, Email email) {
        super.logRequest(LOGGER, this);

        TrialUser trialUser = new TrialUser();

        if (!EmailUtils.isEmailValid(email.getEmailString())) {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.INVALID_EMAIL);
        }

        trialUser.setEmail(email.getEmailString());
        trialUser.setName(name);
        trialUser.setCreatedAt(LocalDateTime.now());
        trialUserRepository.save(trialUser);

        LOGGER.info("Saved trial user with email: {}", email);

        return new CommonResponseWithTrialUserInformation(StatusCodes.Success.User.SAVED_TRIAL_USER_SUCCESSFULLY, trialUser);
    }
}
