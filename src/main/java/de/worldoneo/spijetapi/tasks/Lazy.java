package de.worldoneo.spijetapi.tasks;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

/**
 * Lazy provides lazy evaluation capabilities for suppliers.
 * To avoid evaluation, if not needed, {@link Lazy} may be used.
 * Once evaluated the result is cached. So the supplier is evaluated at most once.
 * Lazy may be configured to be thread-safe by using the {@link #safe()}
 * or {@link #unsafe()} methods.
 *
 * @param <T> the return type of the supplier
 */
public class Lazy<T> implements Supplier<T> {
    private final Object lock = new Object();
    private final Supplier<T> supplier;
    @Getter
    private boolean threadSafe;
    @Getter
    private boolean evaluated;
    private T value;
    @Setter
    private ExecutorService executorService;

    public Lazy(@NotNull Supplier<T> supplier, @NotNull ExecutorService executorService, boolean threadSafe) {
        this.supplier = supplier;
        this.executorService = executorService;
        this.threadSafe = threadSafe;
    }

    public Lazy(@NotNull Supplier<T> supplier, @NotNull ExecutorService executorService) {
        this(supplier, executorService, true);
    }

    public Lazy(@NotNull Supplier<T> supplier) {
        this(supplier, HeavyTaskPool.commonThreadPool());
    }

    /**
     * Creates a new {@link Lazy} instance for this supplier
     *
     * @param get the supplier which supplies the value once evaluated
     * @param <T> the return type of the supplier
     * @return the new Lazy instance
     */
    @Contract("_ -> new")
    public static <T> Lazy<T> of(Supplier<T> get) {
        return new Lazy<>(get);
    }

    /**
     * Disables the thread safety of this lazy.
     *
     * @return this
     */
    @Contract("-> this")
    public Lazy<T> unsafe() {
        threadSafe = false;
        return this;
    }

    /**
     * Enables the thread-safety of this lazy
     *
     * @return this
     */
    @Contract("-> this")
    public Lazy<T> safe() {
        threadSafe = true;
        return this;
    }

    /**
     * Evaluates the supplier or returns the previous evaluated result.
     *
     * @return the computed value
     */
    public T get() {
        return eval();
    }

    /**
     * Evaluates the supplier or returns the previous evaluated result asynchronous
     * using the provided {@link ExecutorService}
     *
     * @return the new completable future representing the running task
     */
    @Contract("-> new")
    public CompletableFuture<T> pull() {
        return CompletableFuture.supplyAsync(this::eval, executorService);
    }

    private T eval() {
        if (evaluated) {
            return value;
        }
        if (!threadSafe) {
            value = supplier.get();
            evaluated = true;
            return value;
        }
        synchronized (lock) {
            if (!evaluated) {
                value = supplier.get();
                evaluated = true;
            }
        }
        return value;
    }
}
