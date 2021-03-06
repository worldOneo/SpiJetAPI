package de.worldoneo.spijetapi.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A method annotated with @SpiHandler, will be registered in an {@link EventManager}
 * with the method {@link EventManager#registerListener(Object)}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SpiHandler {
}
