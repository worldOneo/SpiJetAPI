package de.worldoneo.spijetapi.guiapi.widget;

import de.worldoneo.spijetapi.guiapi.widgets.AbstractMultipartWidget;
import de.worldoneo.spijetapi.utils.Pair;
import de.worldoneo.spijetapi.utils.SpiJetUtils;
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
     * The material this {@link MultipartPlaceHolder} is made up of
     *
     * @param material set the {@link Material} of this {@link MultipartPlaceHolder}
     * @return get the {@link Material}
     */
    private Material material;

    /**
     * Render this {@link MultipartPlaceHolder}
     *
     * @return returns the rendered {@link MultipartPlaceHolder}
     */
    @Override
    public List<Pair<ItemStack, Integer>> render() {
        ItemStack itemStack = SpiJetUtils.createNamedItemStack(material, " ");
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
}
