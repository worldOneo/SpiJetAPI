package de.worldoneo.spijetapi;

import de.worldoneo.spijetapi.event.EventManager;
import lombok.Getter;

public class SpiJetAPI {
    @Getter
    private static final EventManager eventManager = new EventManager();
}
