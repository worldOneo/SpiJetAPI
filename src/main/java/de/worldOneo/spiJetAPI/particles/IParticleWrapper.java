package de.worldOneo.spiJetAPI.particles;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;

public interface IParticleWrapper {
    void createParticle(Object particle, Location location, int amount, Collection<? extends Player> players);

    void createField(Object particle, Location location, int amount,
                             double spreadx, double spready, double spreadz,
                             Collection<? extends Player> players);

    void createSpherical(Object particle, Location location, int amoung, double spreadx, double spready, double spreadz,
                         Collection<? extends Player> players);
}