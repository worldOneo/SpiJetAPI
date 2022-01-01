package de.worldoneo.spijetapi.guiapi;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class HotbarGuiListener implements Listener {
    @EventHandler
    public void onInteractEvent(PlayerInteractEvent e) {
        HotbarGuiManager.getInstance().handle(e);
    }

    @EventHandler
    public void onItemDropEvent(PlayerDropItemEvent event) {
        HotbarGuiManager.getInstance().preventDrop(event);
    }

    @EventHandler
    public void onItemDropEvent(InventoryClickEvent event) {
        HotbarGuiManager.getInstance().preventMove(event);
    }

    @EventHandler
    public void onQuitEvent(PlayerQuitEvent event) {
        HotbarGuiManager.getInstance().removePlayer(event.getPlayer());
    }
}
