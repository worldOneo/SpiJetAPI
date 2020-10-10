package de.worldoneo.spijetapi.guiapi.gui;

import de.worldoneo.spijetapi.guiapi.GUIManager;
import de.worldoneo.spijetapi.guiapi.widgets.AbstractMultipartWidget;
import de.worldoneo.spijetapi.guiapi.widgets.AbstractWidget;
import de.worldoneo.spijetapi.guiapi.widgets.IMultipartWidget;
import de.worldoneo.spijetapi.guiapi.widgets.IWidget;
import de.worldoneo.spijetapi.utils.Pair;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Accessors(chain = true)
@Getter
@Setter
public class GUI implements IGUI {
    private String GUITitle = "Made by GUIAPI";
    private List<IWidget> widgets = new ArrayList<>();
    private List<IMultipartWidget> multipartWidgets = new ArrayList<>();
    private int size = 9;
    private HashMap<Pair<ItemStack, Integer>, IWidget> pairWidgetHashMap = new HashMap<>();
    private HashMap<Pair<ItemStack, Integer>, IMultipartWidget> pairMultipartWidgetHashMap = new HashMap<>();

    /**
     * Opens the GUI for the player
     *
     * @param player to open this GUI for
     */
    public void open(Player player) {
        GUIManager.getInstance().open(this, player);
    }

    /**
     * @return returns the rendered Inventory from all widgets
     */
    @Override
    public Inventory render() {
        Inventory inventory = Bukkit.createInventory(null, getSize(), getGUITitle());
        getWidgets().forEach(widget -> {
            ItemStack itemStack = widget.render();
            int slot = widget.getSlot();
            pairWidgetHashMap.put(new Pair<>(itemStack, slot), widget);
            inventory.setItem(slot, itemStack);
        });

        getMultipartWidgets().forEach(multipartWidget ->
                multipartWidget.render().forEach(itemStackIntegerPair -> {
                    pairMultipartWidgetHashMap.put(itemStackIntegerPair, multipartWidget);
                    inventory.setItem(itemStackIntegerPair.getValue(), itemStackIntegerPair.getKey());
                })
        );
        return inventory;
    }

    /**
     * It's recommended to use {@link AbstractWidget#setIgui(IGUI)} that the widgets can use AbstractWidget#open(Player);
     *
     * @param widget add a widget to this gui
     */
    @Override
    public void addWidget(IWidget widget) {
        widgets.add(widget);
    }

    /**
     * It's recommended to use {@link AbstractMultipartWidget#setIgui(IGUI)} that the widgets can use AbstractMultipartWidget#open(Player);
     *
     * @param multipartWidget add a multipartWidget to this gui
     */
    @Override
    public void addWidget(IMultipartWidget multipartWidget) {
        multipartWidgets.add(multipartWidget);
    }


    @Override
    public void clickEvent(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) {
            return;
        }
        Pair<ItemStack, Integer> pair = new Pair<>(e.getCurrentItem(), e.getSlot());
        IWidget widget = pairWidgetHashMap.get(pair);
        IMultipartWidget multipartWidget = pairMultipartWidgetHashMap.get(pair);
        if (multipartWidget != null) {
            multipartWidget.clickEvent(e);
        }
        if (widget != null) {
            widget.clickEvent(e);
        }
    }
}
