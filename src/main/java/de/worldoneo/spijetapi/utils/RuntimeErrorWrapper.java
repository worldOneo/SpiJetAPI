package de.worldoneo.spijetapi.utils;

import lombok.experimental.UtilityClass;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

@UtilityClass
public class RuntimeErrorWrapper {
    @FunctionalInterface
    public interface ThrowingFunction<R, A, E extends Throwable> {
        R apply(A t) throws E;
    }

    /**
     * Wraps a function which throws into an Supplier which throws in runtime.
     * This can be used to run in an {@link CompletableFuture} and handle the error gracefully.
     *
     * @param func     The function to wrap
     * @param argument The argument to pass into the function
     * @param <R>      The return type of the function
     * @param <A>      The argument type of the function
     * @param <E>      The exception type of the function
     * @return A supplier which throws a RuntimeException if it throws any
     */
    public static <R, A, E extends Throwable> Supplier<R> tryOrThrow(ThrowingFunction<R, A, E> func, A argument) {
        return () -> {
            try {
                return func.apply(argument);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }

    /**
     * Wraps the function with <code>tryOrThrow(ThrowingFunction, Object, Executor)</code> and creates
     * a {@link CompletableFuture} with the wrapped function and the executor.
     *
     * @param func     The function to run async
     * @param argument The argument to pass into the function
     * @param executor The executor to run the function
     * @param <R>      The return type of the function
     * @param <A>      The argument type of the function
     * @param <E>      The exception type of the function
     * @return Returns a new CompletableFuture that is asynchronously completed
     * by the function with the value obtained by calling the given Supplier
     */
    public static <R, A, E extends Throwable> CompletableFuture<R> tryOrThrow(ThrowingFunction<R, A, E> func,
                                                                              A argument,
                                                                              Executor executor) {
        return CompletableFuture.supplyAsync(tryOrThrow(func, argument), executor);
    }
}
