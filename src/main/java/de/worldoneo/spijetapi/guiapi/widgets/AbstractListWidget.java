package de.worldoneo.spijetapi.guiapi.widgets;

import de.worldoneo.spijetapi.utils.Pair;
import de.worldoneo.spijetapi.utils.SpiJetUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

@Accessors(chain = true)
@Getter
@Setter
public abstract class AbstractListWidget extends AbstractMultipartWidget {
    /**
     * The slots the {@link AbstractListWidget} is rendered on.
     */
    private List<Integer> slots;

    /**
     * The {@link AbstractListWidget} of {@link ItemStack}s to list.
     */
    private List<ItemStack> itemStacks;

    /**
     * The slot of the back arrow. (last page)
     */
    private Integer backSlot;

    /**
     * The slot of the forward arrow. (next page)
     */
    private Integer forwardSlot;

    /**
     * The Function which is called when an <b>other</b> item than the back and for arrows are clicked
     *
     * @param callback the function which is called with an {@link ItemStack}
     * @return the function which is called as callback
     */
    private Consumer<ItemStack> callback;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private int index;

    @RequiredArgsConstructor
    private enum MenuItems {
        BACK(SpiJetUtils.createNamedItemStack(Material.ARROW, "Back")),
        FORWARD(SpiJetUtils.createNamedItemStack(Material.ARROW, "Next"));
        @Getter
        private final ItemStack itemStack;

    }

    /**
     * Render the {@link AbstractListWidget}
     *
     * @return returns the rendered {@link AbstractListWidget}
     */
    @Override
    public List<Pair<ItemStack, Integer>> render() {
        List<Pair<ItemStack, Integer>> pairList = new ArrayList<>();
        int tempBackSlot = backSlot == null ? slots.get(0) : backSlot;
        int tempForwardSlot = forwardSlot == null ? slots.get(slots.size() - 1) : forwardSlot;
        pairList.add(new Pair<>(MenuItems.BACK.getItemStack(), tempBackSlot));
        pairList.add(new Pair<>(MenuItems.FORWARD.getItemStack(), tempForwardSlot));
        Iterator<Integer> iterator = slots.iterator();
        Iterator<ItemStack> itemStackIterator = itemStacks.stream().skip(index).limit(slots.size() - 2).iterator();
        while (iterator.hasNext() && itemStackIterator.hasNext()) {
            int slot = iterator.next();
            ItemStack itemStack = itemStackIterator.next();
            while ((slot == tempBackSlot || slot == tempForwardSlot) && iterator.hasNext()) {
                slot = iterator.next();
            }
            pairList.add(new Pair<>(itemStack, slot));
        }
        return pairList;
    }


    /**
     * @param e The {@link InventoryClickEvent} to handle
     */
    @Override
    public void clickEvent(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) {
            return;
        }
        e.setCancelled(true);
        ItemStack itemStack = e.getCurrentItem();
        if (itemStack.equals(MenuItems.BACK.getItemStack())) {
            index -= slots.size() - 2;
            index = index < 0 ? 0 : index;
            open((Player) e.getWhoClicked());
        } else if (itemStack.equals(MenuItems.FORWARD.getItemStack())) {
            int tmpIndex = index + slots.size() - 2;
            if (itemStacks.stream().skip(tmpIndex).count() != 0) {
                index = tmpIndex;
            }
            open((Player) e.getWhoClicked());
        } else if (callback != null) {
            callback.accept(itemStack);
        }
    }
}
