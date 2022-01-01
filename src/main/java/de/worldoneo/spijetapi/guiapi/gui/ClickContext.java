package de.worldoneo.spijetapi.guiapi.gui;

import de.worldoneo.spijetapi.guiapi.IGuiManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

@Data
@AllArgsConstructor
public class ClickContext {
    private ItemStack itemStack;
    private Player player;
    private boolean cancelled;
    private IGuiManager<?> guiManager;
    private IGui gui;
    private int slot;
    private final Cancellable event;

    /**
     * Equal to setCancelled(true)
     */
    public void cancel() {
        setCancelled(true);
    }
}
