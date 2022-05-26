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
    public void open(IGui iGui, Player player) {
        InventoryHolder gui = iGui.render();
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

    public void handle(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        InventoryHolder holder = event.getInventory().getHolder();
        if (!(holder instanceof RawGui)) return;

        IGui gui = ((RawGui) holder).getGui();
        ClickContext clickContext = new ClickContext(event.getCurrentItem(), player,
                false, this, gui, event.getSlot(), event);

        gui.clickEvent(clickContext);
        if (clickContext.isCancelled()) event.setCancelled(true);
    }
}
