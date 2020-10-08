package de.worldoneo.spijetapi.guiapi.widgets;

import de.worldoneo.spijetapi.guiapi.GUIManager;
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
     * The {@link IGUI} which this widget is on.
     *
     * @param igui the {@link IGUI} of this widget <br /><b>THIS FUNCTION IS NOT RECOMMENDED AS YOU HAVE TO ADD THE WIDGET MANUEL TO THE {@link IGUI}<br />
     * USE {@link AbstractMultipartWidget#addToGUI(IGUI)} INSTEAD</b>
     * @return the {@link IGUI} of this Widget
     */
    private IGUI igui;

    /**
     * Adds this {@link IMultipartWidget} to the {@link IGUI} and also set the own variable to enable <code>open(Player player);</code>.
     *
     * @param igui the {@link IGUI} on which to add the {@link AbstractMultipartWidget}
     */
    public void addToGUI(IGUI igui) {
        igui.addWidget(this);
        this.igui = igui;
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
