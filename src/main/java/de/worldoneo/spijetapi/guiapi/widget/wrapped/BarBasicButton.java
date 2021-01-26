package de.worldoneo.spijetapi.guiapi.widget.wrapped;

import de.worldoneo.spijetapi.guiapi.widget.BasicButton;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.function.Consumer;

public class BarBasicButton extends BasicButton<PlayerInteractEvent> {
    public BarBasicButton(Consumer<PlayerInteractEvent> clickEventConsumer) {
        super(clickEventConsumer);
    }
}
