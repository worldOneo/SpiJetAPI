package de.worldoneo.spijetapi.guiapi;

import de.worldoneo.spijetapi.SpigotSpiJetAPI;
import de.worldoneo.spijetapi.guiapi.gui.IGUI;
import de.worldoneo.spijetapi.utils.Pair;
import de.worldoneo.spijetapi.utils.SpiJetUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Random;

public class InventoryGUIManager implements GUIManager<InventoryClickEvent> {
    private static final InventoryGUIManager instance = new InventoryGUIManager();
    private final HashMap<Player, Pair<String, IGUI<InventoryClickEvent>>> playerIGUIMap = new HashMap<>();

    private InventoryGUIManager() {
        SpigotSpiJetAPI spigotSpiJetAPI = SpigotSpiJetAPI.getInstance();
        spigotSpiJetAPI.getServer().getPluginManager().registerEvents(new OnInventoryClickListener(), spigotSpiJetAPI);
    }

    public static InventoryGUIManager getInstance() {
        return instance;
    }

    public void open(IGUI<InventoryClickEvent> igui, Player player) {
        Inventory inventory = igui.render();
        String title = igui.getGUITitle();
        title = title+generateID();
        Inventory renamedInventory = inventory.getType() == InventoryType.CHEST
                ? Bukkit.createInventory(null, inventory.getSize(), title)
                : Bukkit.createInventory(null, inventory.getType(), title);
        renamedInventory.setContents(inventory.getContents());
        playerIGUIMap.put(player, new Pair<>(title, igui));
        player.openInventory(renamedInventory);
    }

    @Override
    public void render(Player player) {
        IGUI<InventoryClickEvent> igui = playerIGUIMap.getOrDefault(player, new Pair<>(null, null)).getValue();
        if (igui == null) return;
        open(igui, player);
    }

    public void handle(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Pair<String, IGUI<InventoryClickEvent>> pair = playerIGUIMap.get(player);
        if (pair == null) {
            return;
        }
        String title = pair.getKey();
        IGUI<InventoryClickEvent> iGUI = pair.getValue();

        if (!title.equals(e.getView().getTitle())) return;
        iGUI.clickEvent(e);
    }

    private String generateID() {
        char[] chars = "1234567890abcdeflnokm".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            stringBuilder.append("&").append(chars[random.nextInt(chars.length)]);
        }
        return SpiJetUtils.colorize(stringBuilder.toString());
    }
}
