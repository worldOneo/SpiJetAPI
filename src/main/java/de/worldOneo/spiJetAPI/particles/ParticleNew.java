package de.worldOneo.spiJetAPI.particles;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Random;

public class ParticleNew extends ParticleWrapper {
    public void createParticle(Object particle, Location location, int amount, Collection<? extends Player> players) {
        for (Player player : players) {
            player.spawnParticle((Particle) particle, location, amount, null);
        }
    }
}
