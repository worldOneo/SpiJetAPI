package de.worldoneo.spijetapi.guiapi;

import de.worldoneo.spijetapi.SpigotSpiJetAPI;
import de.worldoneo.spijetapi.guiapi.gui.ClickContext;
import de.worldoneo.spijetapi.guiapi.gui.IGui;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;

public class HotbarGuiManager implements GuiManager<PlayerInteractEvent> {
    @Getter
    private static final HotbarGuiManager instance = new HotbarGuiManager();
    private final HashMap<Player, IGui> playerIGUIHashMap = new HashMap<>();

    private HotbarGuiManager() {
        SpigotSpiJetAPI instance = SpigotSpiJetAPI.getInstance();
        instance.getServer().getPluginManager().registerEvents(new HotbarGuiListener(), instance);
    }

    @Override
    public void open(IGui gui, Player player) {
        InventoryHolder holder = gui.render();
        player.getInventory().setContents(holder.getInventory().getContents());
        player.updateInventory();
        playerIGUIHashMap.put(player, gui);
    }

    @Override
    public void render(Player player) {
        IGui gui = playerIGUIHashMap.get(player);
        if (gui == null) return;
        open(gui, player);
    }

    @Override
    public void handle(PlayerInteractEvent e) {
        PlayerInventory inventory = e.getPlayer().getInventory();
        int heldItemSlot = inventory.getHeldItemSlot();
        handle(e.getPlayer(), e,
                heldItemSlot, inventory.getItem(heldItemSlot));
    }

    public void handle(Player player, Cancellable event, int slot, ItemStack itemStack) {
        IGui gui = playerIGUIHashMap.get(player);
        if (gui == null) return;
        ClickContext clickContext = new ClickContext(itemStack, player
                , false, this, gui, slot);
        gui.clickEvent(clickContext);
        if (clickContext.isCancelled()) event.setCancelled(true);
    }

    public void removePlayer(Player player) {
        playerIGUIHashMap.remove(player);
    }

    public void preventDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (!playerIGUIHashMap.containsKey(player)) return;
        PlayerInventory inventory = player.getInventory();
        int heldItemSlot = inventory.getHeldItemSlot();
        handle(player, event, heldItemSlot, event.getItemDrop().getItemStack());
    }

    public void preventMove(InventoryClickEvent event) {
        InventoryType type;
        Player whoClicked = (Player) event.getWhoClicked();
        if (!playerIGUIHashMap.containsKey(whoClicked)
                || event.getClickedInventory() == null
                || ((type = event.getClickedInventory().getType()) != InventoryType.PLAYER
                && type != InventoryType.CREATIVE)
        ) return;
        handle(whoClicked, event, event.getSlot(), event.getCurrentItem());
    }
}
