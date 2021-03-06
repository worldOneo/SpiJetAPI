package de.worldoneo.spijetapi.guiapi.gui;

import de.worldoneo.spijetapi.guiapi.modifier.IModifier;
import de.worldoneo.spijetapi.guiapi.widgets.IMultipartWidget;
import de.worldoneo.spijetapi.guiapi.widgets.IWidget;
import org.bukkit.inventory.Inventory;

public interface IGUI {
    void clickEvent(ClickContext e);

    /**
     * Defines the Title of the IGUI.
     * This function might return an empty string.
     *
     * @return the Title of the IGUI.
     */
    default String getGUITitle() {
        return "";
    }

    /**
     * Renders the IGUI into an Inventory.
     *
     * @return the rendered Inventory.
     */
    Inventory render();

    /**
     * Adds a {@link IWidget} to the IGUI.
     * The user should not assume any relation between the
     * order the widgets are added and the order the widgets
     * are being rendered.
     *
     * @param widget the widget to add to the IGUI.
     */
    void addWidget(IWidget widget);

    /**
     * Adds a {@link IMultipartWidget} to the IGUI.
     * The user should not assume any relations between the
     * order the widgets are added and the order the widgets
     * are being rendered.
     *
     * @param multipartWidget the widget to add to the IGUI.
     */
    void addWidget(IMultipartWidget multipartWidget);

    /**
     * Adds a {@link IModifier} to the IGUI. Modifiers modify
     * IGUI's in the last moment. Modifiers arent tracked, meaning
     * that no click events can be used on them.
     *
     * @param modifier the {@link IModifier} to add to this IGUI
     */
    void addModifier(IModifier modifier);
}
