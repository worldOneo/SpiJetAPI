package de.worldOneo.spiJetAPI;

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
}
