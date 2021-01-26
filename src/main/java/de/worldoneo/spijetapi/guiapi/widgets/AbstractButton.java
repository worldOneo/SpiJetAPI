package de.worldoneo.spijetapi.guiapi.widgets;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

@Setter
@Getter
public abstract class AbstractButton<T extends Cancellable> extends AbstractWidget<T> {
    private final Consumer<T> clickEventConsumer;

    public AbstractButton(Consumer<T> clickEventConsumer) {
        this.clickEventConsumer = clickEventConsumer;
    }

    /**
     * Call the function which the {@link AbstractButton} is initialized with.
     *
     * @param e the {@link InventoryClickEvent} to call the function with.
     */
    @Override
    public void clickEvent(T e) {
        e.setCancelled(true);
        clickEventConsumer.accept(e);
    }
}
