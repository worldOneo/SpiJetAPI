package de.worldOneo.spiJetAPI.guiAPI.widgets;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface IWidget {
    /**
     * Render thi IWidget
     *
     * @return the rendered IWidget as {@link ItemStack}
     */
    ItemStack render();

    /**
     * This function is called when the widget is clicked.
     *
     * @param e the {@link InventoryClickEvent} which caused this function call
     */
    void clickEvent(InventoryClickEvent e);

    /**
     * @return the slot of this IWidget
     */
    int getSlot();
}
