package de.worldoneo.spijetapi.guiapi.widget;

import de.worldoneo.spijetapi.guiapi.widgets.AbstractMultipartWidget;
import de.worldoneo.spijetapi.utils.ItemStackBuilder;
import de.worldoneo.spijetapi.utils.Pair;
import de.worldoneo.spijetapi.utils.SpigotUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

@Accessors(chain = true)
@Getter
@Setter
public class MultipartPlaceHolder extends AbstractMultipartWidget {
    /**
     * The slots this {@link MultipartPlaceHolder} renders on
     *
     * @param slots set the slots to render this {@link MultipartPlaceHolder}
     * @return the slots of this {@link MultipartPlaceHolder}
     */
    private List<Integer> slots;

    /**
     * The {@link ItemStack} of this {@link PlaceHolder}
     *
     * @param itemStack the material to render this placeholder
     * @return the current material
     */
    private ItemStack itemStack = new ItemStack(Material.STONE);

    /**
     * Render this {@link MultipartPlaceHolder}
     *
     * @return returns the rendered {@link MultipartPlaceHolder}
     */
    @Override
    public List<Pair<ItemStack, Integer>> render() {
        ItemStack itemStack = new ItemStackBuilder(this.itemStack).setDisplayName(" ").build();
        return slots.stream()
                .map(integer -> new Pair<>(itemStack, integer))
                .collect(Collectors.toList());
    }

    /**
     * Just cancels the clickEvent
     */
    @Override
    public void clickEvent(InventoryClickEvent e) {
        e.setCancelled(true);
    }


    /**
     * Sets the ItemStack to a new empty named {@link ItemStack}
     *
     * @param material the material of the new {@link ItemStack}
     * @return this
     */
    public MultipartPlaceHolder setMaterial(Material material) {
        this.itemStack = SpigotUtils.createNamedItemStack(material, " ");
        return this;
    }

    /**
     * Gets the material of the current ItemStack
     *
     * @return the material of the current ItemStack
     */
    public Material getMaterial() {
        return itemStack.getType();
    }
}
