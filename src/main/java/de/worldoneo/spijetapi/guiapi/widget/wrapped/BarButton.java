package de.worldoneo.spijetapi.guiapi.widget.wrapped;

import de.worldoneo.spijetapi.guiapi.widget.Button;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.function.Consumer;

public class BarButton extends Button<PlayerInteractEvent> {
    public BarButton(Consumer<PlayerInteractEvent> clickEventConsumer) {
        super(clickEventConsumer);
    }
}
