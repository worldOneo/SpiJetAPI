package de.worldoneo.spijetapi.particles;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;

public class ParticleLegacy extends ParticleWrapper {
    public void createParticle(Object particle, Location location, int data, Collection<? extends Player> players) {
        for (Player player : players) {
            player.playEffect(location, (Effect) particle, data);
        }
    }
}
