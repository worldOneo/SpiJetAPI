package de.worldoneo.spijetapi.guiapi.gui;

import de.worldoneo.spijetapi.guiapi.GuiManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Data
@AllArgsConstructor
public class ClickContext {
    private ItemStack itemStack;
    private Player player;
    private boolean cancelled;
    private GuiManager<?> guiManager;
    private IGui gui;
    private int slot;

    /**
     * Equal to setCancelled(true)
     */
    public void cancel() {
        setCancelled(true);
    }
}
