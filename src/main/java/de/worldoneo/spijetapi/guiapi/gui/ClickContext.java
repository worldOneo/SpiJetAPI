package de.worldoneo.spijetapi.guiapi.gui;

import de.worldoneo.spijetapi.guiapi.GUIManager;
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
    private GUIManager<?> guiManager;
    private IGUI<?> gui;
    private int slot;
}
