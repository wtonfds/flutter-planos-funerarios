package br.com.monitoratec.farol.graphql.resolvers.mutation.common;

import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.utils.password.PasswordUtils;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TryHashImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(TryHashImpl.class);

    private final PasswordUtils passwordUtils;

    public TryHashImpl(PasswordUtils passwordUtils) {
        this.passwordUtils = passwordUtils;
    }

    public String tryHash(String string) {
        super.logRequest(LOGGER, this);

        return passwordUtils.hashPassword(string);
    }
}
