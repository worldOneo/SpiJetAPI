package de.worldoneo.spijetapi.guiapi.widgets;

import de.worldoneo.spijetapi.guiapi.GUIManager;
import de.worldoneo.spijetapi.guiapi.InventoryGUIManager;
import de.worldoneo.spijetapi.guiapi.gui.IGUI;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.entity.Player;

@Accessors(chain = true)
@Setter
@Getter
public abstract class AbstractMultipartWidget implements IMultipartWidget {
    /**
     * @param igui the {@link IGUI} on which to add the {@link AbstractMultipartWidget}
     */
    public void addToGUI(IGUI<?> igui) {
        igui.addWidget(this);
    }


    /**
     * Opens the {@link IGUI} of this IMultipartWidget if and only if the igui is set
     *
     * @param player The {@link Player} to open the {@link IGUI} for.
     */
    protected void open(Player player) {
        open(player, InventoryGUIManager.getInstance());
    }

    protected void open(Player player, GUIManager<?> guiManager) {
        guiManager.render(player);
    }
}
