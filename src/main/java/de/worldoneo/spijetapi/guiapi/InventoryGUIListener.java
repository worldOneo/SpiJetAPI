package de.worldoneo.spijetapi.guiapi;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class InventoryGUIListener implements Listener {
    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        InventoryGUIManager.getInstance().handle(e);
    }

    @EventHandler
    public void onQuitEvent(PlayerQuitEvent event) {
        InventoryGUIManager.getInstance().removePlayer(event.getPlayer());
    }
}
