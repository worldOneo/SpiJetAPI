package de.worldoneo.spijetapi.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@UtilityClass
public class SpigotUtils {
    /**
     * @param material the Material of the ItemStack
     * @param name     the name of the ItemStack
     * @return the named ItemStack
     */
    public static ItemStack createNamedItemStack(Material material, String name) {
        return new ItemStackBuilder(material).setDisplayName(name).build();
    }

    /**
     * Ensures that the {@link InventoryClickEvent} has an {@link ItemStack} with an {@link ItemMeta}
     *
     * @param inventoryClickEvent the {@link InventoryClickEvent} to check the meta on
     * @return true if the Item and the ItemMeta of this event are not null otherwise false
     */
    public static boolean ensureItemMeta(@NotNull InventoryClickEvent inventoryClickEvent) {
        return ensureItemMeta(inventoryClickEvent.getCurrentItem());
    }

    /**
     * Ensures that neither the {@link ItemStack} nor the {@link ItemMeta} of the ItemStack is null
     *
     * @param itemStack the ItemStack to test
     * @return true if neither the {@link ItemStack} nor its {@link ItemMeta} is null false otherwise
     */
    public static boolean ensureItemMeta(@Nullable ItemStack itemStack) {
        return itemStack != null && itemStack.getItemMeta() != null;
    }
}
