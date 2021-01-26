package de.worldoneo.spijetapi.guiapi.widgets;

import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface IWidget<T extends Cancellable> {
    /**
     * Render this IWidget
     *
     * @return the rendered IWidget as {@link ItemStack}
     */
    ItemStack render();

    /**
     * This function is called when the widget is clicked.
     *
     * @param e the {@link InventoryClickEvent} which caused this function call
     */
    void clickEvent(T e);

    /**
     * @return the slot of this IWidget
     */
    int getSlot();
}
