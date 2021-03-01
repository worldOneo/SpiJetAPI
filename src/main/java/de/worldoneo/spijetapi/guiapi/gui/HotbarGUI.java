package de.worldoneo.spijetapi.guiapi.gui;

import de.worldoneo.spijetapi.utils.Pair;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
        widgets.forEach(widget -> {
            Pair<ItemStack, Integer> pair = new Pair<>(widget.render(), widget.getSlot());
            inventory.setItem(pair.getValue(), pair.getKey());
            pairWidgetHashMap.put(pair, widget);
        });

        multipartWidgets.forEach(multipartWidget ->
                multipartWidget.render().forEach(p -> {
                    pairMultipartWidgetHashMap.put(p, multipartWidget);
                    inventory.setItem(p.getValue(), p.getKey());
                }));
        return inventory;
    }
}
