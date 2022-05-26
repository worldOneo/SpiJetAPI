package de.worldoneo.spijetapi.guiapi.modifier;

import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

@UtilityClass
public class Modifiers {
    /**
     * Creates a generator which fills every empty slot of the inventory
     * with the given {@link ItemStack}
     *
     * @param itemStack the ItemStack to fill the empty slots with.
     * @return a new {@link IModifier}
     */
    public static IModifier fillEmpty(ItemStack itemStack) {
        return inventory -> {
            for (int i = 0; i < inventory.getSize(); i++) {
                if (inventory.getItem(i) == null) inventory.setItem(i, itemStack);
            }
        };
    }

    /**
     * Creates a generator which fills every empty slot of the inventory
     * with the an {@link ItemStack} received from the generator.
     *
     * @param generator the ItemStack to fill the empty slots with.
     * @return a new {@link IModifier}
     */
    public static IModifier fillEmpty(Supplier<ItemStack> generator) {
        return inventory -> {
            for (int i = 0; i < inventory.getSize(); i++) {
                if (inventory.getItem(i) == null) inventory.setItem(i, generator.get());
            }
        };
    }
}
