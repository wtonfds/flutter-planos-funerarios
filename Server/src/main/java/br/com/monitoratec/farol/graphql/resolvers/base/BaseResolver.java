package br.com.monitoratec.farol.graphql.resolvers.base;

import br.com.monitoratec.farol.auth.exceptions.NonAuthorizedException;
import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.config.CustomGraphQLContext;
import br.com.monitoratec.farol.redis.model.CachedTrustedToken;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BaseResolver {

    public BindedResponseStep validateUserPromise(DataFetchingEnvironment dataFetchingEnvironment) {
        CustomGraphQLContext context = dataFetchingEnvironment.getContext();
        CompletableFuture<CachedTrustedToken> authPromise = context.getInfoPromise();

        return new BindedResponseStep(authPromise);
    }

    public static class BindedResponseStep {
        private CompletableFuture<CachedTrustedToken> authPromise;

        public BindedResponseStep(CompletableFuture<CachedTrustedToken> authPromise) {
            this.authPromise = authPromise;
        }

        public ResolvingAuthStep bindToResponsePromise(CompletableFuture<?> promise) {
            return new ResolvingAuthStep(this.authPromise, promise);
        }
    }

    public static class ResolvingAuthStep {
        private CompletableFuture<CachedTrustedToken> authPromise;
        private CompletableFuture<?> responsePromise;

        private ResolvingAuthStep(CompletableFuture<CachedTrustedToken> authPromise, CompletableFuture<?> responsePromise) {
            this.authPromise = authPromise;
            this.responsePromise = responsePromise;
        }

        public ExceptionsWrappedCompletableFuture anyOf(List<AccessingEntity> allowedEntities) {
            ExceptionsWrappedCompletableFuture wrappedPromise = new ExceptionsWrappedCompletableFuture(this.responsePromise);

            authPromise = authPromise.exceptionally(throwable -> {
                this.responsePromise.completeExceptionally(throwable);
                return null;
            });

            authPromise.thenAccept(cachedUserInfo -> {
                if (this.checkIfIsAny(allowedEntities, cachedUserInfo.userCachedInfo.accessingEntity)) {
                    wrappedPromise.complete(cachedUserInfo);
                } else {
                    // User is not of an allowed accessing type
                    responsePromise.completeExceptionally(new NonAuthorizedException());
                }
            });

            return wrappedPromise;
        }

        private boolean checkIfIsAny(List<AccessingEntity> allowedEntities, AccessingEntity isEntity) {
            return allowedEntities.contains(isEntity);
        }

    }

    protected void logRequest(Logger logger, Object caller) {
        if (logger.isInfoEnabled()) {
            String operation;
            if (caller instanceof GraphQLQueryResolver) {
                operation = "Query";
            } else if (caller instanceof GraphQLMutationResolver) {
                operation = "Mutation";
            } else {
                operation = "";
            }

            logger.info("Received {} [{}]", operation, Thread.currentThread().getStackTrace()[2].getMethodName());
        }
    }
}
