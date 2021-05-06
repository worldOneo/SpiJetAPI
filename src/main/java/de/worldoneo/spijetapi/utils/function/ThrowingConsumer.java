package de.worldoneo.spijetapi.utils.function;

@FunctionalInterface
public interface ThrowingConsumer<A, E extends Throwable> {
    void accept(A arg) throws E;
}
