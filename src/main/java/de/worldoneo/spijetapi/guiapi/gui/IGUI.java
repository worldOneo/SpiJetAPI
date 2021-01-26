package de.worldoneo.spijetapi.guiapi.gui;

import de.worldoneo.spijetapi.guiapi.widgets.IMultipartWidget;
import de.worldoneo.spijetapi.guiapi.widgets.IWidget;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.Inventory;

public interface IGUI<T extends Cancellable> {
    void clickEvent(T e);

    String getGUITitle();

    Inventory render();

    void addWidget(IWidget<T> widget);

    void addWidget(IMultipartWidget<T> multipartWidget);
}
