package br.com.monitoratec.farol.graphql.config;

import br.com.monitoratec.farol.auth.exceptions.AuthorizationTokenMissingException;
import br.com.monitoratec.farol.auth.exceptions.AuthorizationTokenUnrecognizedException;
import br.com.monitoratec.farol.auth.managers.AuthManager;
import br.com.monitoratec.farol.redis.model.CachedTrustedToken;
import graphql.servlet.context.GraphQLContext;
import org.dataloader.DataLoaderRegistry;

import javax.security.auth.Subject;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class CustomGraphQLContext implements GraphQLContext {
    private final AuthManager authManager;
    private final String authorizationToken;

    private CompletableFuture<CachedTrustedToken> userInfoPromise = null;

    public CustomGraphQLContext(AuthManager authManager, String authorizationToken) {
        this.authManager = authManager;
        this.authorizationToken = authorizationToken;
    }

    public synchronized CompletableFuture<CachedTrustedToken> getInfoPromise() {
        if (this.userInfoPromise == null) {
            this.userInfoPromise = new CompletableFuture<>();

            if (authorizationToken == null) {
                // User didn't provide a token
                this.userInfoPromise.completeExceptionally(new AuthorizationTokenMissingException());
                return this.userInfoPromise;
            }

            // Async try solve user token
            this.userInfoPromise = userInfoPromise.completeAsync(() -> {
                CachedTrustedToken cachedTrustedToken = authManager.solveAuthToken(authorizationToken);

                if (cachedTrustedToken == null) {
                    userInfoPromise.completeExceptionally(new AuthorizationTokenUnrecognizedException());
                    return null;
                }

                return cachedTrustedToken;
            });
        }
        return this.userInfoPromise;
    }

    @Override
    public Optional<Subject> getSubject() {
        return Optional.empty();
    }

    @Override
    public Optional<DataLoaderRegistry> getDataLoaderRegistry() {
        return Optional.empty();
    }
}
