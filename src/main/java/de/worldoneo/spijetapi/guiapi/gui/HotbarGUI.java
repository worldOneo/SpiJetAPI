package de.worldoneo.spijetapi.guiapi.gui;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class HotbarGUI extends GUI {

    public HotbarGUI() {
        super();
        setCancelClickDefault(false);
    }

    @Override
    public Inventory render() {
        pairWidgetHashMap.clear();
        pairMultipartWidgetHashMap.clear();
        Inventory inventory = Bukkit.createInventory(null, 36);
        renderOn(inventory);
        return inventory;
    }
}
