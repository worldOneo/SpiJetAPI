package de.worldoneo.spijetapi.guiapi.gui;

import de.worldoneo.spijetapi.guiapi.InventoryGuiManager;
import de.worldoneo.spijetapi.guiapi.modifier.IModifier;
import de.worldoneo.spijetapi.guiapi.widgets.IMultipartWidget;
import de.worldoneo.spijetapi.guiapi.widgets.IWidget;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

@Accessors(chain = true)
public class InventoryGui implements IGui {
    protected List<IWidget> widgets = new ArrayList<>();
    protected List<IMultipartWidget> multipartWidgets = new ArrayList<>();
    protected List<IModifier> modifiers = new LinkedList<>();
    protected HashMap<Integer, IWidget> pairWidgetHashMap = new HashMap<>();
    protected HashMap<Integer, IMultipartWidget> pairMultipartWidgetHashMap = new HashMap<>();
    @Setter
    private String guiTitle = "Made by SpiJetAPI";
    @Getter
    @Setter
    private InventoryType inventoryType = InventoryType.CHEST;
    @Getter
    @Setter
    private Consumer<ClickContext> defaultClickHandler = c -> c.setCancelled(true);
    @Getter
    @Setter
    private int size = 9;

    /**
     * Opens the GUI for the player
     *
     * @param player to open this GUI for
     */
    public void open(Player player) {
        InventoryGuiManager.getInstance().open(this, player);
    }

    /**
     * @return returns the rendered Inventory from all widgets
     */
    @Override
    public RawGui render() {
        pairMultipartWidgetHashMap.clear();
        pairWidgetHashMap.clear();
        RawGui gui = getInventoryType() == InventoryType.CHEST
                ? new RawGui(this, getSize(), getGuiTitle())
                : new RawGui(this, getInventoryType(), getGuiTitle());
        renderOn(gui.getInventory());
        return gui;
    }

    protected void renderOn(Inventory inventory) {
        widgets.forEach(widget -> {
            ItemStack itemStack = widget.render();
            int slot = widget.getSlot();
            pairWidgetHashMap.put(slot, widget);
            inventory.setItem(slot, itemStack);
        });

        multipartWidgets.forEach(multipartWidget ->
                multipartWidget.render().forEach(p -> {
                    pairMultipartWidgetHashMap.put(p.getValue(), multipartWidget);
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
    public void clickEvent(ClickContext clickContext) {
        if (clickContext.getItemStack() == null) return;
        int key = clickContext.getSlot();
        IWidget widget = pairWidgetHashMap.get(key);
        IMultipartWidget multipartWidget = pairMultipartWidgetHashMap.get(key);
        if (widget != null) widget.clickEvent(clickContext);
        if (widget == null && multipartWidget != null) multipartWidget.clickEvent(clickContext);
        if (widget == null && multipartWidget == null) defaultClickHandler.accept(clickContext);
    }

    @Override
    public void addModifier(IModifier modifier) {
        modifiers.add(modifier);
    }

    /**
     * @deprecated use {@link #getGuiTitle()} instead
     * @return the title of this GUI
     */
    public String getGUITitle() {
        return this.guiTitle;
    }

    @Override
    public String getGuiTitle() {
        return guiTitle;
    }
}
