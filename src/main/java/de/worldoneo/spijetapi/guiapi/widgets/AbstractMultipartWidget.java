package de.worldoneo.spijetapi.guiapi.widgets;

import de.worldoneo.spijetapi.guiapi.GuiManager;
import de.worldoneo.spijetapi.guiapi.InventoryGuiManager;
import de.worldoneo.spijetapi.guiapi.gui.IGui;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.entity.Player;

@Accessors(chain = true)
@Setter
@Getter
public abstract class AbstractMultipartWidget implements IMultipartWidget {
    /**
     * @param igui the {@link IGui} on which to add the {@link AbstractMultipartWidget}
     * @deprecated use {@link IGui#addWidget} instead
     */
    @Deprecated
    public void addToGUI(IGui igui) {
        igui.addWidget(this);
    }


    /**
     * Opens the {@link IGui} of this IMultipartWidget if and only if the igui is set
     *
     * @param player The {@link Player} to open the {@link IGui} for.
     */
    protected void open(Player player) {
        open(player, InventoryGuiManager.getInstance());
    }

    protected void open(Player player, GuiManager<?> guiManager) {
        guiManager.render(player);
    }
}
