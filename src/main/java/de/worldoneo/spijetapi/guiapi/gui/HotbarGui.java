package de.worldoneo.spijetapi.guiapi.gui;

import de.worldoneo.spijetapi.guiapi.HotbarGuiManager;
import org.bukkit.entity.Player;

public class HotbarGui extends InventoryGui {

    public HotbarGui() {
        super();
        setDefaultClickHandler(c -> {
        });
    }

    @Override
    public RawGui render() {
        pairWidgetHashMap.clear();
        pairMultipartWidgetHashMap.clear();
        RawGui rawGui = new RawGui(this, Gui.PLAYER_INVENTORY_SLOTS);
        renderOn(rawGui.getInventory());
        return rawGui;
    }

    @Override
    public void open(Player player) {
        HotbarGuiManager.getInstance().open(this, player);
    }
}
