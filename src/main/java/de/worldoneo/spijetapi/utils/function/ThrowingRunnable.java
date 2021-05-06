package de.worldoneo.spijetapi.utils.function;

@FunctionalInterface
public interface ThrowingRunnable<E extends Throwable> {
    void run() throws E;
}