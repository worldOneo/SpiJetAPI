package de.worldoneo.spijetapi.guiapi.widgets;

import de.worldoneo.spijetapi.guiapi.GUIManager;
import de.worldoneo.spijetapi.guiapi.gui.IGUI;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.entity.Player;

@Accessors(chain = true)
@Getter
@Setter
public abstract class AbstractWidget implements IWidget {
    private int slot;
    private IGUI igui;


    /**
     * This function is used to support {@link AbstractWidget#open(Player)}
     *
     * @param igui the GUI to add this Widget to
     */
    public void addToGUI(IGUI igui) {
        igui.addWidget(this);
        this.setIgui(igui);
    }

    /**
     * Opens the {@link IGUI} of this IMultipartWidget if and only if the igui is set
     *
     * @param player The {@link Player} to open the {@link IGUI} for.
     */
    protected void open(Player player) {
        if (this.igui != null) {
            GUIManager.getInstance().open(this.igui, player);
        }
    }
}