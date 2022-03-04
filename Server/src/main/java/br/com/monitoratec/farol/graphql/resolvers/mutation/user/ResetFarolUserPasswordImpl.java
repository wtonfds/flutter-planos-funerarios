package br.com.monitoratec.farol.graphql.resolvers.mutation.user;

import br.com.monitoratec.farol.graphql.model.input.user.ResetFarolUserPasswordInput;
import br.com.monitoratec.farol.graphql.model.responses.common.JustCommonResponse;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.service.user.FarolUserService;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static br.com.monitoratec.farol.utils.responses.StatusCodes.Success.User.PASSWORD_RESET;

@Component
public class ResetFarolUserPasswordImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResetFarolUserPasswordImpl.class);

    private final FarolUserService service;

    public ResetFarolUserPasswordImpl(FarolUserService service) {
        this.service = service;
    }

    public JustCommonResponse resetFarolUserPassword(ResetFarolUserPasswordInput input) {
        super.logRequest(LOGGER, this);

        service.resetPassword(input);

        return new JustCommonResponse(PASSWORD_RESET);
    }
}
