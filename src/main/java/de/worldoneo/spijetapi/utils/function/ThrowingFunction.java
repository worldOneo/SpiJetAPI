package de.worldoneo.spijetapi.utils.function;

@FunctionalInterface
public interface ThrowingFunction<R, A, E extends Throwable> {
    R apply(A t) throws E;
}
