package de.worldoneo.spijetapi.guiapi.widgets;

import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public abstract class AbstractButton extends AbstractWidget {
    private final Consumer<InventoryClickEvent> inventoryClickEventConsumer;

    public AbstractButton(Consumer<InventoryClickEvent> inventoryClickEventConsumer) {
        this.inventoryClickEventConsumer = inventoryClickEventConsumer;
    }

    /**
     * Call the function which the {@link AbstractButton} is initialized with.
     *
     * @param e the {@link InventoryClickEvent} to call the function with.
     */
    @Override
    public void clickEvent(InventoryClickEvent e) {
        e.setCancelled(true);
        inventoryClickEventConsumer.accept(e);
    }
}
