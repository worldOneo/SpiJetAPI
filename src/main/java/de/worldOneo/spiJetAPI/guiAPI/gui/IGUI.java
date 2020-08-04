package de.worldOneo.spiJetAPI.guiAPI.gui;

import de.worldOneo.spiJetAPI.guiAPI.widgets.IMultipartWidget;
import de.worldOneo.spiJetAPI.guiAPI.widgets.IWidget;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public interface IGUI {
    void clickEvent(InventoryClickEvent e);

    String getGUITitle();

    Inventory render();

    void addWidget(IWidget widget);

    void addWidget(IMultipartWidget multipartWidget);
}
