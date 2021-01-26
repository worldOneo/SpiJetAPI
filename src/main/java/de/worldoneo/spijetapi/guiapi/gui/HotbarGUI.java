package de.worldoneo.spijetapi.guiapi.gui;

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
    private final List<IWidget<PlayerInteractEvent>> widgets = new ArrayList<>();
    private final List<IMultipartWidget<PlayerInteractEvent>> multipartWidgets = new ArrayList<>();
    private final HashMap<Pair<ItemStack, Integer>, IWidget<PlayerInteractEvent>> pairWidgetHashMap = new HashMap<>();
    private final HashMap<Pair<ItemStack, Integer>, IMultipartWidget<PlayerInteractEvent>> pairMultipartWidgetHashMap = new HashMap<>();

    @Override
    public void addWidget(IWidget<PlayerInteractEvent> widget) {
        widgets.add(widget);
    }

    @Override
    public void clickEvent(PlayerInteractEvent e) {
        PlayerInventory inventory = e.getPlayer().getInventory();
        int slot = inventory.getHeldItemSlot();
        ItemStack itemStack = inventory.getItem(slot);
        Pair<ItemStack, Integer> key = new Pair<>(itemStack, slot);
        IWidget<PlayerInteractEvent> widget = pairWidgetHashMap.get(key);
        IMultipartWidget<PlayerInteractEvent> multipartWidget = pairMultipartWidgetHashMap.get(key);
        if (widget != null) widget.clickEvent(e);
        if (multipartWidget != null) multipartWidget.clickEvent(e);
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
        for (IWidget<PlayerInteractEvent> widget : widgets) {
            Pair<ItemStack, Integer> pair = new Pair<>(widget.render(), widget.getSlot());
            inventory.setItem(pair.getValue(), pair.getKey());
            pairWidgetHashMap.put(pair, widget);
        }

        for (IMultipartWidget<PlayerInteractEvent> multipartWidget : multipartWidgets) {
            multipartWidget.render().forEach((p) -> {
                pairMultipartWidgetHashMap.put(p, multipartWidget);
                inventory.setItem(p.getValue(), p.getKey());
            });
        }
        return inventory;
    }

    @Override
    public void addWidget(IMultipartWidget<PlayerInteractEvent> multipartWidget) {
        multipartWidgets.add(multipartWidget);
    }
}
