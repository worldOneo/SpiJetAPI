package de.worldOneo.spiJetAPI.particles;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Collection;

public interface IParticleWrapper {
    void createParticle(Object particle, Location location, int amount, Collection<? extends Player> players);

    void createField(Object particle, Location location, int amount,
                     double spreadx, double spready, double spreadz,
                     Collection<? extends Player> players);

    void createSpherical(Object particle, Location location, int amount, double spreadx, double spready, double spreadz,
                         Collection<? extends Player> players);

    void createLine(Object particle, World world, int amount, double x, double y, double z,
                    double x1, double y1, double z1, Collection<? extends Player> players);
}