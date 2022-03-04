package br.com.monitoratec.farol.graphql.utils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class TimedOutHandledPromiser {
    private static final Long DEFAULT_TIMEOUT_VALUE_MILLIS = 300000L;

    public static <T> CompletableFuture<T> genPromise() {
        return new CompletableFuture<T>()
                .orTimeout(DEFAULT_TIMEOUT_VALUE_MILLIS, TimeUnit.MILLISECONDS);
    }
}
