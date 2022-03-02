package de.worldoneo.spijetapi.guiapi;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryGuiListener implements Listener {
    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        InventoryGuiManager.getInstance().handle(event);
    }
}
