package de.worldOneo.spiJetAPI.guiAPI.widgets;

import de.worldOneo.spiJetAPI.utils.Pair;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface IMultipartWidget {
    List<Pair<ItemStack, Integer>> render();

    void clickEvent(InventoryClickEvent e);
}
