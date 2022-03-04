package br.com.monitoratec.farol.graphql.resolvers.base;

import br.com.monitoratec.farol.redis.model.CachedTrustedToken;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

public class ExceptionsWrappedCompletableFuture extends CompletableFuture<CachedTrustedToken> {
    private CompletableFuture<?> responsePromise;

    public ExceptionsWrappedCompletableFuture(CompletableFuture<?> responsePromise) {
        this.responsePromise = responsePromise;
    }

    @Override
    public CompletableFuture<Void> thenAccept(Consumer<? super CachedTrustedToken> action) {
        CompletableFuture<Void> promise = super.thenAccept(action);
        promise = promise.exceptionally(throwable -> {
            if (throwable != null) {
                this.responsePromise.completeExceptionally(throwable.getCause());
            }

            return null;
        });
        return promise;
    }

    @Override
    public <U> CompletableFuture<U> thenApply(Function<? super CachedTrustedToken, ? extends U> fn) {
        // Not implemented, if needs to use, please intercept this call too.
        return null;
    }
}
