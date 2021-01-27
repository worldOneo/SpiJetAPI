package de.worldoneo.spijetapi.guiapi.widgets;

import de.worldoneo.spijetapi.guiapi.gui.ClickContext;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

@Setter
@Getter
public abstract class AbstractButton extends AbstractWidget {
    private final Consumer<ClickContext> clickEventConsumer;

    public AbstractButton(Consumer<ClickContext> clickEventConsumer) {
        this.clickEventConsumer = clickEventConsumer;
    }

    /**
     * Call the function which the {@link AbstractButton} is initialized with.
     *
     * @param e the {@link InventoryClickEvent} to call the function with.
     */
    @Override
    public void clickEvent(ClickContext e) {
        e.setCancelled(true);
        clickEventConsumer.accept(e);
    }
}
