package de.worldoneo.spijetapi.guiapi.gui;

import de.worldoneo.spijetapi.guiapi.InventoryGUIManager;
import de.worldoneo.spijetapi.guiapi.widgets.IMultipartWidget;
import de.worldoneo.spijetapi.guiapi.widgets.IWidget;
import de.worldoneo.spijetapi.utils.Pair;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Accessors(chain = true)
@Getter
@Setter
public class GUI implements IGUI<InventoryClickEvent> {
    private String GUITitle = "Made by GUIAPI";
    private List<IWidget> widgets = new ArrayList<>();
    private List<IMultipartWidget> multipartWidgets = new ArrayList<>();
    private InventoryType inventoryType = InventoryType.CHEST;
    private boolean cancelClickDefault = true;
    private int size = 9;
    private HashMap<Pair<ItemStack, Integer>, IWidget> pairWidgetHashMap = new HashMap<>();
    private HashMap<Pair<ItemStack, Integer>, IMultipartWidget> pairMultipartWidgetHashMap = new HashMap<>();

    /**
     * Opens the GUI for the player
     *
     * @param player to open this GUI for
     */
    public void open(Player player) {
        InventoryGUIManager.getInstance().open(this, player);
    }

    /**
     * @return returns the rendered Inventory from all widgets
     */
    @Override
    public Inventory render() {
        pairMultipartWidgetHashMap.clear();
        pairWidgetHashMap.clear();
        Inventory inventory = getInventoryType() == InventoryType.CHEST
                ? Bukkit.createInventory(null, getSize(), getGUITitle())
                : Bukkit.createInventory(null, getInventoryType(), getGUITitle());
        getWidgets().forEach(widget -> {
            ItemStack itemStack = widget.render();
            int slot = widget.getSlot();
            pairWidgetHashMap.put(new Pair<>(itemStack, slot), widget);
            inventory.setItem(slot, itemStack);
        });

        multipartWidgets.forEach(multipartWidget ->
                multipartWidget.render().forEach(p -> {
                    pairMultipartWidgetHashMap.put(p, multipartWidget);
                    inventory.setItem(p.getValue(), p.getKey());
                }));
        return inventory;
    }

    /**
     * @param widget add a widget to this gui
     */
    @Override
    public void addWidget(IWidget widget) {
        widgets.add(widget);
    }

    /**
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
        if (cancelClickDefault) e.setCancelled(true);
        Pair<ItemStack, Integer> pair = new Pair<>(e.getCurrentItem(), e.getSlot());
        IWidget widget = pairWidgetHashMap.get(pair);
        IMultipartWidget multipartWidget = pairMultipartWidgetHashMap.get(pair);
        ClickContext clickContext = new ClickContext(e.getCurrentItem(), (Player) e.getWhoClicked(), false,
                InventoryGUIManager.getInstance(), this, e.getSlot());
        if (multipartWidget != null) {
            multipartWidget.clickEvent(clickContext);
        }
        if (widget != null) {
            widget.clickEvent(clickContext);
        }
    }
}
