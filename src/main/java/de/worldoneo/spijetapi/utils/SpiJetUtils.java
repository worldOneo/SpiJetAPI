package de.worldoneo.spijetapi.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SpiJetUtils {
    /**
     * Translates the color codes of a string which is written with a '{@literal &}'.
     * E.g: "{@literal &}aMy name is Joe"
     *
     * @param msg the string to Translate
     * @return the translated String
     */
    public static String colorize(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    /**
     * @param material the Material of the ItemStack
     * @param name     the name of the ItemStack
     * @return the named ItemStack
     */
    public static ItemStack createNamedItemStack(Material material, String name) {
        return new ItemStackBuilder(material).setDisplayName(name).build();
    }
}
