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
        HotbarGUIManager.getINSTANCE().handle(event);
    }

    @EventHandler
    public void onItemDropEvent(PlayerDropItemEvent event) {
        HotbarGUIManager.getINSTANCE().preventDrop(event);
    }

    @EventHandler
    public void onItemDropEvent(InventoryClickEvent event) {
        HotbarGUIManager.getINSTANCE().preventMove(event);
    }

    @EventHandler
    public void onQuitEvent(PlayerQuitEvent event) {
        HotbarGUIManager.getINSTANCE().removePlayer(event.getPlayer());
    }
}
