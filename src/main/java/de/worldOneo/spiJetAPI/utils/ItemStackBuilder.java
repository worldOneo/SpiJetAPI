package de.worldOneo.spiJetAPI.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.Consumer;

public class ItemStackBuilder implements SpiJetBuilder<ItemStack> {
    private final ItemStack itemStack;

    public ItemStackBuilder(Material material, int amount, String displayName, List<String> lore) {
        this.itemStack = new ItemStack(material);
        setAmount(amount);
        setDisplayName(displayName);
        setLore(lore);
    }

    public ItemStackBuilder(Material material, int amount, String displayName) {
        this.itemStack = new ItemStack(material);
        setAmount(amount);
        setDisplayName(displayName);
    }

    public ItemStackBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material);
        setAmount(amount);
    }

    public ItemStackBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemStackBuilder setDisplayName(String displayName) {
        changeItemMeta(itemMeta -> itemMeta.setDisplayName(displayName));
        return this;
    }

    public ItemStackBuilder setLore(List<String> lore) {
        changeItemMeta(itemMeta -> itemMeta.setLore(lore));
        return this;
    }

    public ItemStackBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemStackBuilder addEnchantment(Enchantment enchantment, int level) {
        itemStack.addEnchantment(enchantment, level);
        return this;

    }

    public ItemStackBuilder addLore(List<String> lore) {
        changeItemMeta(itemMeta -> {
            if(itemMeta.hasLore() && itemMeta.getLore() != null){
                List<String> currentLores = itemMeta.getLore();
                currentLores.addAll(lore);
                itemMeta.setLore(currentLores);
            }else{
                itemMeta.setLore(lore);
            }
        });
        return this;
    }

    private void changeItemMeta(Consumer<ItemMeta> callable) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        callable.accept(itemMeta);
        itemStack.setItemMeta(itemMeta);
    }

    @Override
    public ItemStack build() {
        return itemStack.clone();
    }
}
