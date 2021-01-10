package de.worldoneo.spijetapi.utils;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class SpiJetUtils {
    /**
     * Translates the color codes of a string which is written with a '{@literal &}'.
     * E.g: "{@literal &}aMy name is Joe"
     *
     * @param msg the string to Translate
     * @return the translated String
     */
    public static String colorize(String msg) {
        return colorize(msg, '&');
    }

    /**
     * Translate the color codes of a string which is written with the altColorChar
     *
     * @param message      the string to translate
     * @param altColorChar the char to translate the color code from
     * @return the translated string
     */
    public static String colorize(String message, char altColorChar) {
        return ChatColor.translateAlternateColorCodes(altColorChar, message);
    }
}
