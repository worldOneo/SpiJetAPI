package de.worldoneo.spijetapi.utils.function;


@FunctionalInterface
public interface ThrowingSupplier<R, E extends Throwable> {
    R get() throws E;
}
