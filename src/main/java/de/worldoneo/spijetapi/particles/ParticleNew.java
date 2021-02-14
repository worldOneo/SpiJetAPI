package de.worldoneo.spijetapi.particles;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Objects;

public class ParticleNew extends ParticleWrapper {
    public void createParticle(Object particle, Location location, int amount, Collection<? extends Player> players) {
        for (Player player : players) {
            player.spawnParticle((Particle) particle, location, amount, null);
        }
    }

    @Override
    public void createParticle(Object particle, Location location, int amount) {
        World world = Objects.requireNonNull(location.getWorld(), "The world of the location can't be null");
        world.spawnParticle((Particle) particle, location, amount);
    }
}
