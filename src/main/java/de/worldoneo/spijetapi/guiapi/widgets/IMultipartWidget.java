package de.worldoneo.spijetapi.guiapi.widgets;

import de.worldoneo.spijetapi.utils.Pair;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface IMultipartWidget<T extends Cancellable> {
    List<Pair<ItemStack, Integer>> render();

    void clickEvent(T e);
}
