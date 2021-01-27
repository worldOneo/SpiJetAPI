package de.worldoneo.spijetapi.guiapi.gui;

import de.worldoneo.spijetapi.guiapi.HotbarGUIManager;
import de.worldoneo.spijetapi.guiapi.widgets.IMultipartWidget;
import de.worldoneo.spijetapi.guiapi.widgets.IWidget;
import de.worldoneo.spijetapi.utils.Pair;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HotbarGUI implements IGUI<PlayerInteractEvent> {
    private final List<IWidget> widgets = new ArrayList<>();
    private final List<IMultipartWidget> multipartWidgets = new ArrayList<>();
    private final HashMap<Pair<ItemStack, Integer>, IWidget> pairWidgetHashMap = new HashMap<>();
    private final HashMap<Pair<ItemStack, Integer>, IMultipartWidget> pairMultipartWidgetHashMap = new HashMap<>();

    @Override
    public void addWidget(IWidget widget) {
        widgets.add(widget);
    }

    @Override
    public void clickEvent(PlayerInteractEvent e) {
        PlayerInventory inventory = e.getPlayer().getInventory();
        int slot = inventory.getHeldItemSlot();
        ItemStack itemStack = inventory.getItem(slot);
        Pair<ItemStack, Integer> key = new Pair<>(itemStack, slot);
        IWidget widget = pairWidgetHashMap.get(key);
        IMultipartWidget multipartWidget = pairMultipartWidgetHashMap.get(key);
        ClickContext clickContext = new ClickContext(itemStack, e.getPlayer(), false,
                HotbarGUIManager.getInstance(), this, slot);
        if (widget != null) widget.clickEvent(clickContext);
        if (multipartWidget != null) multipartWidget.clickEvent(clickContext);
    }

    @Override
    public String getGUITitle() {
        return "";
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

    @Override
    public void addWidget(IMultipartWidget multipartWidget) {
        multipartWidgets.add(multipartWidget);
    }
}
