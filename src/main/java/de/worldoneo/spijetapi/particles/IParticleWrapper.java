package de.worldoneo.spijetapi.particles;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Collection;

public interface IParticleWrapper {
    /**
     * Spawns particles for the given players
     *
     * @param particle the particle type (version dependent)
     * @param location the location to spawn the particles
     * @param amount   the amount of the particles
     * @param players  the players to spawn the particles for
     */
    void createParticle(Object particle, Location location, int amount, Collection<? extends Player> players);

    /**
     * Spawns particles in the world of the given location
     *
     * @param particle the particle type (version dependent)
     * @param location the location to spawn the particles
     * @param amount   the amount of the particles
     */
    void createParticle(Object particle, Location location, int amount);

    void createField(Object particle, Location location, int amount,
                     double spreadx, double spready, double spreadz,
                     Collection<? extends Player> players);

    void createField(Object particle, Location location, int amount,
                     double spreadx, double spready, double spreadz);

    void createSpherical(Object particle, Location location, int amount, double spreadx, double spready, double spreadz,
                         Collection<? extends Player> players);

    void createSpherical(Object particle, Location location, int amount, double spreadx, double spready, double spreadz);

    void createLine(Object particle, World world, int amount, double x, double y, double z,
                    double x1, double y1, double z1, Collection<? extends Player> players);

    void createLine(Object particle, World world, int amount, double x, double y, double z,
                    double x1, double y1, double z1);

    void createCircle(Object particle, Location location, int amount, double radius, Collection<? extends Player> players);

    void createCircle(Object particle, Location location, int amount, double radius);
}