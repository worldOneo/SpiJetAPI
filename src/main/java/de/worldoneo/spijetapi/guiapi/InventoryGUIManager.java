package de.worldoneo.spijetapi.guiapi;

import de.worldoneo.spijetapi.SpigotSpiJetAPI;
import de.worldoneo.spijetapi.guiapi.gui.ClickContext;
import de.worldoneo.spijetapi.guiapi.gui.IGUI;
import de.worldoneo.spijetapi.utils.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class InventoryGUIManager implements GUIManager<InventoryClickEvent> {
    public static final List<ChatColor> ID_COLORS = Arrays.stream(ChatColor.values())
            .filter(ChatColor::isColor)
            .collect(Collectors.toList());

    private static final InventoryGUIManager instance = new InventoryGUIManager();
    private final HashMap<Player, Pair<String, IGUI>> playerIGUIMap = new HashMap<>();

    private InventoryGUIManager() {
        SpigotSpiJetAPI spigotSpiJetAPI = SpigotSpiJetAPI.getInstance();
        spigotSpiJetAPI.getServer().getPluginManager().registerEvents(new InventoryGUIListener(), spigotSpiJetAPI);
    }

    public static InventoryGUIManager getInstance() {
        return instance;
    }

    @Override
    public void open(IGUI igui, Player player) {
        Inventory inventory = igui.render();
        String title = igui.getGUITitle();
        title = title + generateID();
        Inventory renamedInventory = inventory.getType() == InventoryType.CHEST
                ? Bukkit.createInventory(null, inventory.getSize(), title)
                : Bukkit.createInventory(null, inventory.getType(), title);
        renamedInventory.setContents(inventory.getContents());
        playerIGUIMap.put(player, new Pair<>(title, igui));
        Bukkit.getScheduler().runTask(SpigotSpiJetAPI.getInstance(),
                () -> player.openInventory(renamedInventory));
    }

    public void removePlayer(Player pLayer) {
        playerIGUIMap.remove(pLayer);
    }

    @Override
    public void render(Player player) {
        IGUI igui = playerIGUIMap.getOrDefault(player, new Pair<>(null, null)).getValue();
        if (igui == null) return;
        open(igui, player);
    }

    public void handle(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Pair<String, IGUI> pair = playerIGUIMap.get(player);

        if (pair == null) return;

        String title = pair.getKey();
        IGUI iGUI = pair.getValue();

        if (!title.equals(e.getView().getTitle())) return;

        ClickContext cc = new ClickContext(e.getCurrentItem(), player,
                false, this, iGUI, e.getSlot());

        iGUI.clickEvent(cc);
        if (cc.isCancelled()) e.setCancelled(true);
    }

    private String generateID() {
        Random random = new Random();
        return ID_COLORS.get(random.nextInt(ID_COLORS.size())).toString() + " " +
                ID_COLORS.get(random.nextInt(ID_COLORS.size()));
    }
}
