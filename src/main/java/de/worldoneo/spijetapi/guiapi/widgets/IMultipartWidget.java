package de.worldoneo.spijetapi.guiapi.widgets;

import de.worldoneo.spijetapi.guiapi.gui.ClickContext;
import de.worldoneo.spijetapi.utils.Pair;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface IMultipartWidget {
    List<Pair<ItemStack, Integer>> render();

    void clickEvent(ClickContext clickContext);
}
