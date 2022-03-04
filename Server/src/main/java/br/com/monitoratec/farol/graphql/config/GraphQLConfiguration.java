package br.com.monitoratec.farol.graphql.config;

import br.com.monitoratec.farol.auth.managers.AuthManager;
import graphql.servlet.context.GraphQLContextBuilder;
import graphql.servlet.core.GraphQLErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphQLConfiguration {

    private final AuthManager authManager;

    public GraphQLConfiguration(AuthManager authManager) {
        this.authManager = authManager;
    }

    @Bean
    public GraphQLContextBuilder graphQLContextBuilder() {
        return new CustomGraphQLContextBuilder(authManager);
    }

    @Bean
    public GraphQLErrorHandler graphQLErrorHandler() {
        return new CustomGraphQLErrorHandler();
    }
}
