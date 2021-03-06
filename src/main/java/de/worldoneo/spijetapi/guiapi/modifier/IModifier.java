package de.worldoneo.spijetapi.guiapi.modifier;

import org.bukkit.inventory.Inventory;

@FunctionalInterface
public interface IModifier {
    void accept(Inventory inventory);
}
