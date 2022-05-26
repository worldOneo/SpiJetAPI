package de.worldoneo.spijetapi.guiapi.gui;

import de.worldoneo.spijetapi.guiapi.modifier.IModifier;
import de.worldoneo.spijetapi.guiapi.widgets.IMultipartWidget;
import de.worldoneo.spijetapi.guiapi.widgets.IWidget;
import org.bukkit.inventory.InventoryHolder;

public interface IGui {
    void clickEvent(ClickContext clickContext);

    /**
     * Defines the Title of the GUI.
     * This function might return an empty string.
     *
     * @return the Title of the IGUI.
     */
    default String getGuiTitle() {
        return "";
    }

    /**
     * Renders the GUI into an Inventory.
     *
     * @return the rendered Inventory.
     */
    InventoryHolder render();

    /**
     * Adds a {@link IWidget} to the GUI.
     * The user should not assume any relation between the
     * order the widgets are added and the order the widgets
     * are being rendered.
     *
     * @param widget the widget to add to the GUI.
     */
    void addWidget(IWidget widget);

    /**
     * Adds a {@link IMultipartWidget} to the GUI.
     * The user should not assume any relations between the
     * order the widgets are added and the order the widgets
     * are being rendered.
     *
     * @param multipartWidget the widget to add to the GUI.
     */
    void addWidget(IMultipartWidget multipartWidget);

    /**
     * Adds a {@link IModifier} to the GUI. Modifiers modify
     * GUI's in the last moment. Modifiers aren't tracked, meaning
     * that no click events can be used on them.
     *
     * @param modifier the {@link IModifier} to add to this GUI
     */
    void addModifier(IModifier modifier);
}
