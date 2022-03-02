package de.worldoneo.spijetapi.guiapi.gui;

import lombok.experimental.UtilityClass;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Range;

@UtilityClass
public class Gui {
    public final int CHEST_COLS_PER_ROW = 9;
    public final int MAX_CHEST_ROWS = 6;
    public final int SMALL_CHEST_SLOTS = 27;
    public final int BIG_CHEST_SLOTS = CHEST_COLS_PER_ROW * MAX_CHEST_ROWS;
    public final int PLAYER_INVENTORY_SLOTS = SMALL_CHEST_SLOTS + 9;

    /**
     * Converts the amount of rows to slots
     *
     * @param rows the amount of rows
     * @return the total number of slots
     */
    public static int rowsToSlots(@Range(from = 1, to = 6) int rows) {
        return rows * CHEST_COLS_PER_ROW;
    }

    /**
     * Creates a new InventoryGui
     *
     * @return the new Gui
     */
    @Contract("-> new")
    public static InventoryGui inventory() {
        return inventory(rowsToSlots(MAX_CHEST_ROWS));
    }

    /**
     * Creates a new InventoryGui
     *
     * @param inventoryType the type of the inventory
     * @return the new Gui
     */
    @Contract("_ -> new")
    public static InventoryGui inventory(InventoryType inventoryType) {
        return inventory(inventoryType, "Made by SpiJetAPI");
    }

    /**
     * Creates a new InventoryGui
     *
     * @param inventoryType the type of the inventory
     * @param title         the title of the inventory
     * @return the new Gui
     */
    @Contract("_, _ -> new")
    public static InventoryGui inventory(InventoryType inventoryType, String title) {
        return new InventoryGui().setInventoryType(inventoryType).setGuiTitle(title);
    }

    /**
     * Creates a new InventoryGui
     *
     * @param size the size of the inventory
     * @return the new Gui
     */
    @Contract("_ -> new")
    public static InventoryGui inventory(int size) {
        return inventory(size, "Made by SpiJetAPI");
    }

    /**
     * Creates a new InventoryGui
     *
     * @param size  the size of the inventory
     * @param title the title of the inventory
     * @return the new Gui
     */
    @Contract("_, _ -> new")
    public static InventoryGui inventory(int size, String title) {
        return new InventoryGui().setSize(size).setGuiTitle(title);
    }

    /**
     * Creates a new {@link HotbarGui}
     *
     * @return the new Gui
     */
    @Contract("-> new")
    public static HotbarGui hotbar() {
        return new HotbarGui();
    }
}
