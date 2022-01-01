package de.worldoneo.spijetapi.guiapi.gui;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class RawGui implements InventoryHolder {
    @Getter
    private final IGui gui;
    private final Inventory inventory;

    public RawGui(IGui gui, int size) {
        this.gui = gui;
        this.inventory = Bukkit.createInventory(this, size);
    }

    public RawGui(IGui gui, int size, String title) {
        this.gui = gui;
        this.inventory = Bukkit.createInventory(this, size, title);
    }

    public RawGui(IGui gui, InventoryType type, String title) {
        this.gui = gui;
        this.inventory = Bukkit.createInventory(this, type, title);
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
