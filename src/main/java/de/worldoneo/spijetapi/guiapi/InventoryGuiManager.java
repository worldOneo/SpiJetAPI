package de.worldoneo.spijetapi.guiapi;

import de.worldoneo.spijetapi.SpigotSpiJetAPI;
import de.worldoneo.spijetapi.guiapi.gui.ClickContext;
import de.worldoneo.spijetapi.guiapi.gui.IGui;
import de.worldoneo.spijetapi.guiapi.gui.RawGui;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

import javax.annotation.Nullable;

public class InventoryGuiManager implements IGuiManager<InventoryClickEvent> {
    private static final InventoryGuiManager instance = new InventoryGuiManager();

    private InventoryGuiManager() {
        SpigotSpiJetAPI spigotSpiJetAPI = SpigotSpiJetAPI.getInstance();
        spigotSpiJetAPI.getServer().getPluginManager().registerEvents(new InventoryGuiListener(), spigotSpiJetAPI);
    }

    public static InventoryGuiManager getInstance() {
        return instance;
    }

    @Override
    public void open(IGui igui, Player player) {
        InventoryHolder gui = igui.render();
        Bukkit.getScheduler().runTask(SpigotSpiJetAPI.getInstance(),
                () -> player.openInventory(gui.getInventory()));
    }

    @Nullable
    public static IGui getIGui(Player player) {
        InventoryHolder holder = player
                .getOpenInventory()
                .getTopInventory()
                .getHolder();
        if (!(holder instanceof RawGui)) return null;
        return ((RawGui) holder).getGui();
    }

    @Override
    public void render(Player player) {
        IGui gui = getIGui(player);
        if(gui == null) return;
        open(gui, player);
    }

    public void handle(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        InventoryHolder holder = e.getInventory().getHolder();
        if (!(holder instanceof RawGui)) return;

        IGui gui = ((RawGui) holder).getGui();
        ClickContext clickContext = new ClickContext(e.getCurrentItem(), player,
                false, this, gui, e.getSlot(), e);

        gui.clickEvent(clickContext);
        if (clickContext.isCancelled()) e.setCancelled(true);
    }
}
