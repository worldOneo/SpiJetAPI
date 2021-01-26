package de.worldoneo.spijetapi.guiapi;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class OnPlayerInteractListener implements Listener {
    @EventHandler
    public void onInteractEvent(PlayerInteractEvent e) {
        HotbarGUIManager.getInstance().handle(e);
    }

    @EventHandler
    public void onItemDropEvent(PlayerDropItemEvent event) {
        HotbarGUIManager.getInstance().preventDrop(event);
    }

    @EventHandler
    public void onItemDropEvent(InventoryClickEvent event) {
        HotbarGUIManager.getInstance().preventMove(event);
    }
}
