package de.worldoneo.spijetapi.guiapi;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class HotbarGUIListener implements Listener {
    @EventHandler
    public void onInteractEvent(PlayerInteractEvent event) {
        HotbarGUIManager.getInstance().handle(event);
    }

    @EventHandler
    public void onItemDropEvent(PlayerDropItemEvent event) {
        HotbarGUIManager.getInstance().preventDrop(event);
    }

    @EventHandler
    public void onItemDropEvent(InventoryClickEvent event) {
        HotbarGUIManager.getInstance().preventMove(event);
    }

    @EventHandler
    public void onQuitEvent(PlayerQuitEvent event) {
        HotbarGUIManager.getInstance().removePlayer(event.getPlayer());
    }
}
