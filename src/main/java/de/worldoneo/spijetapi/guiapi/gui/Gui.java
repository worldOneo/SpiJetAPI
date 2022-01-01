package de.worldoneo.spijetapi.guiapi.gui;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Gui {
    public final int CHEST_COLS_PER_ROW = 9;
    public final int MAX_CHEST_ROWS = 6;

    public static int rowsToSlots(int rows) {
        return rows * CHEST_COLS_PER_ROW;
    }

    public static InventoryGui inventory() {
        return inventory(rowsToSlots(MAX_CHEST_ROWS));
    }

    public static InventoryGui inventory(int size) {
        return inventory(size, "Made by SpiJetAPI");
    }

    public static InventoryGui inventory(int size, String title) {
        return new InventoryGui().setSize(size).setGUITitle(title);
    }

    public static HotbarGui hotbar() {
        return new HotbarGui();
    }
}
