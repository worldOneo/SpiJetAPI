package de.worldoneo.spijetapi.utils;

import de.worldoneo.spijetapi.utils.function.ThrowingBiFunction;
import de.worldoneo.spijetapi.utils.function.ThrowingConsumer;
import de.worldoneo.spijetapi.utils.function.ThrowingFunction;
import de.worldoneo.spijetapi.utils.function.ThrowingSupplier;
import lombok.experimental.UtilityClass;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * The RuntimeErrorWrapper needs to be treated with care.
 * <p>
 * It has the power to let checked exceptions disappear from the compiler,
 * but not from reality. This should only be used with an
 * error handler. The main purpose is to easily use those checked
 * functions in {@link CompletableFuture}s and don't need to wrap them
 * everytime in a try-catch. Instead we can use the RuntimeErrorWrapper
 * to wrap them for us and then use those in {@link CompletableFuture}s
 * and gracefully deal with errors as intended.
 * </p>
 */
@UtilityClass
public class RuntimeErrorWrapper {
    /**
     * Wraps a function with a checked exception into an Supplier which throws an {@link RuntimeException}.
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
        return () -> wrap(func).apply(argument);
    }

    /**
     * Wraps a function with a checked exception into an Function which throws an {@link RuntimeException}.
     * This can be used to run in an {@link CompletableFuture} and handle the error gracefully.
     *
     * @param func The function to wrap
     * @param <R>  The return type of the function
     * @param <A>  The argument type of the function
     * @param <E>  The exception type of the function
     * @return A supplier which throws a RuntimeException if it throws any
     */
    public static <R, A, E extends Throwable> Function<A, R> wrap(ThrowingFunction<R, A, E> func) {
        return transform(func, ThrowingFunction::apply);
    }

    /**
     * Wraps a supplier with a checked exception into an supplier which throws an {@link RuntimeException}.
     * This can be used to run in an {@link CompletableFuture} and handle the error gracefully.
     *
     * @param supp The supplier to wrap
     * @param <R>  The return type of the function
     * @param <E>  The exception type of the function
     * @return A supplier which throws a RuntimeException if it throws any
     */
    public static <R, E extends Throwable> Supplier<R> wrap(ThrowingSupplier<R, E> supp) {
        Function<Void, R> ret = transform(supp, (a, b) -> a.get());
        return () -> ret.apply(null);
    }

    /**
     * Wraps a consumer with a checked exception into an supplier which throws an {@link RuntimeException}.
     * This can be used to run in an {@link CompletableFuture} and handle the error gracefully.
     *
     * @param cons The supplier to wrap
     * @param <A>  The argument type of the function
     * @param <E>  The exception type of the function
     * @return A supplier which throws a RuntimeException if it throws any
     */
    public static <A, E extends Throwable> Consumer<A> wrap(ThrowingConsumer<A, E> cons) {
        Function<A, Void> ret = transform(cons, (a, b) -> {
            a.accept(b);
            return null;
        });
        return ret::apply;
    }

    private static <A, R, T, E extends Throwable> Function<A, R>
    transform(T function, ThrowingBiFunction<T, A, R, E> user) {
        return (A a) -> {
            try {
                return user.apply(function, a);
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
