package de.worldoneo.spijetapi.guiapi;

import de.worldoneo.spijetapi.guiapi.gui.IGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public interface GUIManager<T extends Cancellable> {
    void open(IGUI<T> gui, Player player);

    void render(Player player);

    void handle(T e);
}
