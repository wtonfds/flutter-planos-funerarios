package br.com.monitoratec.farol.graphql.resolvers.mutation.user;

import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.common.JustCommonResponse;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.sql.model.user.FarolUser;
import br.com.monitoratec.farol.sql.repository.user.FarolUserRepository;
import br.com.monitoratec.farol.utils.password.PasswordUtils;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static br.com.monitoratec.farol.utils.responses.StatusCodes.Success.User.PASSWORD_RESET;

@Component
public class UpdateFarolUserPasswordImpl extends BaseResolver implements GraphQLMutationResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateFarolUserPasswordImpl.class);

    private final FarolUserRepository farolUserRepository;
    private final PasswordUtils passwordUtils;

    public UpdateFarolUserPasswordImpl(FarolUserRepository farolUserRepository, PasswordUtils passwordUtils) {
        this.farolUserRepository = farolUserRepository;
        this.passwordUtils = passwordUtils;
    }

    public JustCommonResponse updateFarolUserPassword(
            Long id,
            String password
    ) {
        super.logRequest(LOGGER, this);

        Optional<FarolUser> farolUserOptional = farolUserRepository.findByIdAndActiveTrue(id);

        if (farolUserOptional.isEmpty()) {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
        }

        FarolUser farolUser = farolUserOptional.get();

        if (password != null) {
            farolUser.setPasswordHash(passwordUtils.hashPassword(password));
            farolUser.setTemporaryPassword(false);
        } else {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.PASSWORD_REQUIRED_FIELD);
        }

        farolUserRepository.save(farolUser);

        return new JustCommonResponse(PASSWORD_RESET);
    }
}
