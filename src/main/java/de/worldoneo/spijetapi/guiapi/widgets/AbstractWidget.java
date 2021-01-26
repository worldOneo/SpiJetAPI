package de.worldoneo.spijetapi.guiapi.widgets;

import de.worldoneo.spijetapi.guiapi.GUIManager;
import de.worldoneo.spijetapi.guiapi.InventoryGUIManager;
import de.worldoneo.spijetapi.guiapi.gui.IGUI;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

@Accessors(chain = true)
@Getter
@Setter
public abstract class AbstractWidget<T extends Cancellable> implements IWidget<T> {
    private int slot;


    /**
     * This function is used to support {@link AbstractWidget#open(Player)}
     *
     * @param igui the GUI to add this Widget to
     */
    public void addToGUI(IGUI<T> igui) {
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

    protected <A extends Cancellable> void open(Player player, GUIManager<A> guiManager) {
        guiManager.render(player);
    }
}