package de.worldoneo.spijetapi;

import org.bukkit.plugin.java.JavaPlugin;

public class SpigotSpiJetAPI extends JavaPlugin {
    private static SpigotSpiJetAPI instance = null;

    public static SpigotSpiJetAPI getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
    }
}
