package de.worldoneo.spijetapi.guiapi.gui;

import de.worldoneo.spijetapi.guiapi.InventoryGUIManager;
import de.worldoneo.spijetapi.guiapi.modifier.IModifier;
import de.worldoneo.spijetapi.guiapi.widgets.IMultipartWidget;
import de.worldoneo.spijetapi.guiapi.widgets.IWidget;
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
    protected List<IWidget> widgets = new ArrayList<>();
    protected List<IMultipartWidget> multipartWidgets = new ArrayList<>();
    protected List<IModifier> modifiers = new LinkedList<>();
    protected HashMap<Integer, IWidget> pairWidgetHashMap = new HashMap<>();
    protected HashMap<Integer, IMultipartWidget> pairMultipartWidgetHashMap = new HashMap<>();
    private String guiTitle = "Made by GUIAPI";
    private InventoryType inventoryType = InventoryType.CHEST;
    private boolean cancelClickDefault = true;
    private int size = 9;

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
        this.pairMultipartWidgetHashMap.clear();
        this.pairWidgetHashMap.clear();
        Inventory inventory = getInventoryType() == InventoryType.CHEST
                ? Bukkit.createInventory(null, this.size, this.guiTitle)
                : Bukkit.createInventory(null, this.inventoryType, this.guiTitle);
        this.renderOn(inventory);
        return inventory;
    }

    public void renderOn(Inventory inventory) {
        this.getWidgets().forEach(widget -> {
            ItemStack itemStack = widget.render();
            int slot = widget.getSlot();
            this.pairWidgetHashMap.put(slot, widget);
            inventory.setItem(slot, itemStack);
        });

        this.multipartWidgets.forEach(multipartWidget ->
                multipartWidget.render().forEach(pair -> {
                    this.pairMultipartWidgetHashMap.put(pair.getValue(), multipartWidget);
                    inventory.setItem(pair.getValue(), pair.getKey());
                }));

        this.modifiers.forEach(m -> m.accept(inventory));
    }

    /**
     * @param widget add a widget to this gui
     */
    @Override
    public void addWidget(IWidget widget) {
        this.widgets.add(widget);
    }

    /**
     * @param multipartWidget add a multipartWidget to this gui
     */
    @Override
    public void addWidget(IMultipartWidget multipartWidget) {
        this.multipartWidgets.add(multipartWidget);
    }


    @Override
    public void clickEvent(ClickContext clickContext) {
        if (clickContext.getItemStack() == null) return;

        if (this.cancelClickDefault) clickContext.setCancelled(true);
        int key = clickContext.getSlot();
        IWidget widget = this.pairWidgetHashMap.get(key);
        IMultipartWidget multipartWidget = this.pairMultipartWidgetHashMap.get(key);
        if (widget != null) widget.clickEvent(clickContext);
        if (multipartWidget != null) multipartWidget.clickEvent(clickContext);
    }

    @Override
    public void addModifier(IModifier modifier) {
        this.modifiers.add(modifier);
    }
}
