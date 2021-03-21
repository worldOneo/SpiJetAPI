package de.worldoneo.spijetapi.guiapi.gui;

import de.worldoneo.spijetapi.guiapi.InventoryGUIManager;
import de.worldoneo.spijetapi.guiapi.modifier.IModifier;
import de.worldoneo.spijetapi.guiapi.widgets.IMultipartWidget;
import de.worldoneo.spijetapi.guiapi.widgets.IWidget;
import de.worldoneo.spijetapi.utils.Pair;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Accessors(chain = true)
@Getter
@Setter
public class GUI implements IGUI {
    private String GUITitle = "Made by GUIAPI";
    protected List<IWidget> widgets = new ArrayList<>();
    protected List<IMultipartWidget> multipartWidgets = new ArrayList<>();
    protected List<IModifier> modifiers = new LinkedList<>();
    private InventoryType inventoryType = InventoryType.CHEST;
    private boolean cancelClickDefault = true;
    private int size = 9;
    protected HashMap<Pair<ItemStack, Integer>, IWidget> pairWidgetHashMap = new HashMap<>();
    protected HashMap<Pair<ItemStack, Integer>, IMultipartWidget> pairMultipartWidgetHashMap = new HashMap<>();

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
        renderOn(inventory);
        return inventory;
    }

    public void renderOn(Inventory inventory) {
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

        modifiers.forEach(m -> m.accept(inventory));
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
    public void clickEvent(ClickContext e) {
        if (e.getItemStack() == null) {
            return;
        }

        if (cancelClickDefault) e.setCancelled(true);
        Pair<ItemStack, Integer> key = new Pair<>(e.getItemStack(), e.getSlot());
        IWidget widget = pairWidgetHashMap.get(key);
        IMultipartWidget multipartWidget = pairMultipartWidgetHashMap.get(key);
        if (widget != null) widget.clickEvent(e);
        if (multipartWidget != null) multipartWidget.clickEvent(e);
    }

    @Override
    public void addModifier(IModifier modifier) {
        modifiers.add(modifier);
    }
}
