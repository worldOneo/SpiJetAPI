package de.worldOneo.spiJetAPI.guiAPI.widget;

import de.worldOneo.spiJetAPI.guiAPI.widgets.AbstractWidget;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Accessors(chain = true)
@Getter
@Setter
public class PlaceHolder extends AbstractWidget {
    /**
     * The {@link Material} of this {@link PlaceHolder}
     */
    private Material material;

    /**
     * Render this widget
     *
     * @return the rendered widget as {@link ItemStack}
     */
    @Override
    public ItemStack render() {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(" ");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     * This event is always just cancelled.
     */
    @Override
    public void clickEvent(InventoryClickEvent e) {
        e.setCancelled(true);
    }
}
