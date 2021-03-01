package de.worldoneo.spijetapi.guiapi.gui;

import de.worldoneo.spijetapi.guiapi.widgets.IMultipartWidget;
import de.worldoneo.spijetapi.guiapi.widgets.IWidget;
import org.bukkit.inventory.Inventory;

public interface IGUI {
    void clickEvent(ClickContext e);

    String getGUITitle();

    Inventory render();

    void addWidget(IWidget widget);

    void addWidget(IMultipartWidget multipartWidget);
}
