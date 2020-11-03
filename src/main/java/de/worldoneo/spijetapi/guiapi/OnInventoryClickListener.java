package de.worldoneo.spijetapi.guiapi;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class OnInventoryClickListener implements Listener {
    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        GUIManager.getInstance().handle(e);
    }
    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent e) {
        GUIManager.getInstance().close(e);
    }
}
