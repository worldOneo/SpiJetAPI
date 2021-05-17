package de.worldoneo.spijetapi.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.Consumer;

public class ItemStackBuilder implements SpiJetBuilder<ItemStack> {
    private final ItemStack itemStack;

    public ItemStackBuilder(Material material, int amount, String displayName, List<String> lore) {
        this(material, amount, displayName);
        this.setLore(lore);
    }

    public ItemStackBuilder(Material material, int amount, String displayName) {
        this(material, amount);
        this.setDisplayName(displayName);
    }

    public ItemStackBuilder(Material material, int amount) {
        this(material);
        this.setAmount(amount);
    }

    public ItemStackBuilder(Material material) {
        this(new ItemStack(material));
    }

    public ItemStackBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStackBuilder setDisplayName(String displayName) {
        this.changeItemMeta(itemMeta -> itemMeta.setDisplayName(displayName));
        return this;
    }

    public ItemStackBuilder setLore(List<String> lore) {
        this.changeItemMeta(itemMeta -> itemMeta.setLore(lore));
        return this;
    }

    public ItemStackBuilder setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemStackBuilder addEnchantment(Enchantment enchantment, int level) {
        this.itemStack.addEnchantment(enchantment, level);
        return this;

    }

    public ItemStackBuilder setFlags(ItemFlag... itemFlags) {
        this.changeItemMeta(itemMeta -> itemMeta.addItemFlags(itemFlags));
        return this;
    }

    public ItemStackBuilder addLore(List<String> lore) {
        this.changeItemMeta(itemMeta -> {
            if (itemMeta.hasLore() && itemMeta.getLore() != null) {
                List<String> currentLores = itemMeta.getLore();
                currentLores.addAll(lore);
                itemMeta.setLore(currentLores);
            } else {
                itemMeta.setLore(lore);
            }
        });
        return this;
    }

    public void changeItemMeta(Consumer<ItemMeta> callable) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        callable.accept(itemMeta);
        itemStack.setItemMeta(itemMeta);
    }

    @Override
    public ItemStack build() {
        return this.itemStack.clone();
    }
}
