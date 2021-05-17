package de.worldoneo.spijetapi.guiapi.widgets;

import de.worldoneo.spijetapi.guiapi.gui.ClickContext;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface IWidget {
    /**
     * Render this IWidget
     *
     * @return the rendered IWidget as {@link ItemStack}
     */
    ItemStack render();

    /**
     * This function is called when the widget is clicked.
     *
     * @param clickContext the {@link InventoryClickEvent} which caused this function call
     */
    void clickEvent(ClickContext clickContext);

    /**
     * @return the slot of this IWidget
     */
    int getSlot();
}
