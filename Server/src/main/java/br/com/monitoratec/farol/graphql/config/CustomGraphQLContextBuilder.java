package br.com.monitoratec.farol.graphql.config;

import br.com.monitoratec.farol.auth.managers.AuthManager;
import graphql.servlet.context.GraphQLContext;
import graphql.servlet.context.GraphQLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;

public class CustomGraphQLContextBuilder implements GraphQLContextBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomGraphQLContextBuilder.class);

    private final AuthManager authManager;

    public CustomGraphQLContextBuilder(AuthManager authManager) {
        this.authManager = authManager;
    }

    @Override
    public GraphQLContext build(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String authorizationToken = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        return new CustomGraphQLContext(this.authManager, authorizationToken);
    }

    @Override
    public GraphQLContext build(Session session, HandshakeRequest handshakeRequest) {
        LOGGER.warn("Building session from handshake request (this is meant to be used through subscriptions...) and subscriptions are not enabled...");
        return new CustomGraphQLContext(this.authManager, null);
    }

    @Override
    public GraphQLContext build() {
        LOGGER.warn(".buildGeneric method called without params???");
        return new CustomGraphQLContext(this.authManager, null);
    }
}
