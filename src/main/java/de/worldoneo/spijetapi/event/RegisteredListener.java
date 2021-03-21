package de.worldoneo.spijetapi.event;

import lombok.Data;

import java.lang.reflect.Method;

@Data
public class RegisteredListener {
    private final Object obj;
    private final Method method;
}
