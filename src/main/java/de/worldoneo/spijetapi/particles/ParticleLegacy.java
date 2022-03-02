package de.worldoneo.spijetapi.particles;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Objects;

@SuppressWarnings("deprecation") //It's legacy. Of course it's deprecated.
public class ParticleLegacy extends ParticleWrapper {
    public void createParticle(Object particle, Location location, int data, Collection<? extends Player> players) {
        for (Player player : players) {
            player.playEffect(location, (Effect) particle, data);
        }
    }

    @Override
    public void createParticle(Object particle, Location location, int amount) {
        World world = Objects.requireNonNull(location.getWorld(), "The world of the location can't be null.");
        world.playEffect(location, (Effect) particle, amount);
    }
}
