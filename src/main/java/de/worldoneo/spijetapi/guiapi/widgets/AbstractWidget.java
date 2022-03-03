package de.worldoneo.spijetapi.guiapi.widgets;

import de.worldoneo.spijetapi.guiapi.IGuiManager;
import de.worldoneo.spijetapi.guiapi.InventoryGuiManager;
import de.worldoneo.spijetapi.guiapi.gui.IGui;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.entity.Player;

@Accessors(chain = true)
@Getter
@Setter
public abstract class AbstractWidget implements IWidget {
    private int slot;

    /**
     * @param iGui the GUI to add this Widget to
     * @deprecated use {@link IGui#addWidget} instead
     */
    @Deprecated
    public void addToGUI(IGui iGui) {
        iGui.addWidget(this);
    }

    /**
     * Opens the {@link IGui} of this IMultipartWidget if and only if the iGui is set
     *
     * @param player The {@link Player} to open the {@link IGui} for.
     */
    protected void open(Player player) {
        open(player, InventoryGuiManager.getInstance());
    }

    protected void open(Player player, IGuiManager<?> guiManager) {
        guiManager.render(player);
    }
}