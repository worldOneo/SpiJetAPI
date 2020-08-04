package de.worldOneo.spiJetAPI;

import de.worldOneo.spiJetAPI.guiAPI.OnInventoryClickListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SpiJetAPI extends JavaPlugin {

    private static SpiJetAPI instance = null;

    public static SpiJetAPI getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        registerEvents();
    }

    private void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new OnInventoryClickListener(), this);
    }
}
