package de.worldoneo.spijetapi.guiapi;

import de.worldoneo.spijetapi.SpigotSpiJetAPI;
import de.worldoneo.spijetapi.guiapi.gui.IGUI;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class HotbarGUIManager implements GUIManager<PlayerInteractEvent> {
    private final HashMap<Player, IGUI<PlayerInteractEvent>> playerIGUIHashMap = new HashMap<>();
    @Getter
    private static final HotbarGUIManager instance = new HotbarGUIManager();

    private HotbarGUIManager() {
        SpigotSpiJetAPI instance = SpigotSpiJetAPI.getInstance();
        instance.getServer().getPluginManager().registerEvents(new OnPlayerInteractListener(), instance);
    }

    @Override
    public void open(IGUI<PlayerInteractEvent> gui, Player player) {
        Inventory inventory = gui.render();
        player.getInventory().setContents(inventory.getContents());
        player.updateInventory();
        playerIGUIHashMap.put(player, gui);
    }

    @Override
    public void render(Player player) {
        IGUI<PlayerInteractEvent> gui = playerIGUIHashMap.get(player);
        if (gui == null) return;
        open(gui, player);
    }

    @Override
    public void handle(PlayerInteractEvent e) {
        IGUI<PlayerInteractEvent> gui = playerIGUIHashMap.get(e.getPlayer());
        if (gui == null) return;
        gui.clickEvent(e);
        e.getPlayer().updateInventory();
    }

    public void removePlayer(Player player) {
        playerIGUIHashMap.remove(player);
    }

    public void preventDrop(PlayerDropItemEvent event) {
        if (event.isCancelled()) return;
        event.setCancelled(playerIGUIHashMap.containsKey(event.getPlayer()));
    }

    public void preventMove(InventoryClickEvent event) {
        if (event.isCancelled()) return;
        event.setCancelled(playerIGUIHashMap.containsKey((Player) event.getWhoClicked()));
    }
}
