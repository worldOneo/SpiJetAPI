package de.worldOneo.spiJetAPI.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpiJetUtils {
    /**
     * Translates the color codes of a string which is written with a '&'.
     * E.g: "&aMy name is Joe"
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
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
