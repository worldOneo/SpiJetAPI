package de.worldoneo.spijetapi.guiapi;

import de.worldoneo.spijetapi.guiapi.gui.IGui;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public interface GuiManager<T extends Cancellable> {
    void open(IGui gui, Player player);

    void render(Player player);

    void handle(T e);
}
