package de.worldoneo.spijetapi.guiapi.widget.wrapped;

import de.worldoneo.spijetapi.guiapi.widget.BasicButton;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class InvBasicButton extends BasicButton<InventoryClickEvent> {
    public InvBasicButton(Consumer<InventoryClickEvent> clickEventConsumer) {
        super(clickEventConsumer);
    }
}
