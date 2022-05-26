package de.worldoneo.spijetapi.event;

import de.worldoneo.spijetapi.scheduler.SpiScheduler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EventManager {
    private final Map<Class<? extends SpiEvent>, List<RegisteredListener>> registeredEvents = new HashMap<>();

    /**
     * Registers all of listeners functions annotated with {@link SpiHandler}.
     * As this function uses reflection it should not be called often.
     *
     * @param listener The object to register the listeners from.
     */
    public void registerListener(Object listener) {
        for (Method method : listener.getClass().getMethods()) {
            Class<?> type;
            if (method.getAnnotation(SpiHandler.class) == null
                    || method.getParameterCount() != 1
                    || !SpiEvent.class.isAssignableFrom((type = method.getParameterTypes()[0]))) continue;

            method.setAccessible(true);
            Class<? extends SpiEvent> event = type.asSubclass(SpiEvent.class);

            registeredEvents.putIfAbsent(event, new LinkedList<>());
            registeredEvents.get(event).add(new RegisteredListener(listener, method));
        }
    }

    /**
     * Fires the {@link SpiEvent} for every registered listener.
     * If {@link SpiEvent#isAsync()} is true, the event is called
     * in another thread.
     *
     * @param event The event to fire.
     */
    public void runEvent(SpiEvent event) {
        if (event.isAsync()) SpiScheduler.getInstance().runAsync(() -> runEvent0(event));
        else runEvent0(event);
    }

    private void runEvent0(SpiEvent event) {
        if (!registeredEvents.containsKey(event.getClass())) return;

        registeredEvents.get(event.getClass()).forEach(listener -> {
            try {
                listener.getMethod().invoke(listener.getObj(), event);
            } catch (InvocationTargetException | IllegalAccessException exception) {
                exception.printStackTrace();
            }
        });
    }
}
