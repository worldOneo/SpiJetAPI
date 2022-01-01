package de.worldoneo.spijetapi.guiapi.widget;

import de.worldoneo.spijetapi.guiapi.gui.ClickContext;
import de.worldoneo.spijetapi.guiapi.widgets.AbstractWidget;
import de.worldoneo.spijetapi.utils.ItemStackBuilder;
import de.worldoneo.spijetapi.utils.SpigotUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Accessors(chain = true)
@Getter
@Setter
public class PlaceHolder extends AbstractWidget {
    /**
     * The {@link ItemStack} of this {@link PlaceHolder}
     *
     * @param itemStack the material to render this placeholder
     * @return the current material
     */
    private ItemStack itemStack = new ItemStack(Material.STONE);

    /**
     * Render this widget
     *
     * @return the rendered widget as {@link ItemStack}
     */
    @Override
    public ItemStack render() {
        return new ItemStackBuilder(itemStack).setDisplayName(" ").build();
    }

    /**
     * This event is always just cancelled.
     */
    @Override
    public void clickEvent(ClickContext e) {
        e.cancel();
    }

    /**
     * Gets the material of the current ItemStack
     *
     * @return the material of the current ItemStack
     */
    public Material getMaterial() {
        return itemStack.getType();
    }

    /**
     * Sets the ItemStack to a new empty named ItemStack
     *
     * @param material the material of the new ItemStack
     * @return this
     */
    public PlaceHolder setMaterial(Material material) {
        this.itemStack = SpigotUtils.createNamedItemStack(material, " ");
        return this;
    }
}
