package de.worldoneo.spijetapi.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The SpiEvent class is used to handle/create events for the {@link EventManager}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpiEvent {
    private boolean async = true;
}
