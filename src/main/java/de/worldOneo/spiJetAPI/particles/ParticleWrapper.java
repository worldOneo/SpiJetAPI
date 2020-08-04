package de.worldOneo.spiJetAPI.particles;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Random;

public abstract class ParticleWrapper implements IParticleWrapper {
    /**
     * Create a field of particles around the location.
     * <p>
     * O(PlayerAmount*ParticleAmount)
     *
     * @param particle the Particle type
     * @param location the base location the "middle"
     * @param amount   the total amount of Particles
     * @param spreadx  the spreadx (Random spread on the x dimension)
     * @param spready  the spready (Random spread on the x dimension)
     * @param spreadz  the spreadz (Random spread on the x dimension)
     * @param players  the players to send the particle to
     */
    public void createField(Object particle, Location location, int amount,
                            double spreadx, double spready, double spreadz,
                            Collection<? extends Player> players) {
        Random r = new Random();
        double dx, dy, dz;
        double mx = -spreadx;
        double my = -spready;
        double mz = -spreadz;
        double tx = spreadx * 2;
        double ty = spready * 2;
        double tz = spreadz * 2;

        for (int i = 0; i < amount; i++) {
            dx = mx + r.nextDouble() * tx;
            dy = my + r.nextDouble() * ty;
            dz = mz + r.nextDouble() * tz;
            Location newLocation = new Location(location.getWorld(),
                    location.getX() + dx,
                    location.getY() + dy,
                    location.getZ() + dz);
            createParticle(particle, newLocation, 1, players);
        }
    }

    /**
     * Creates a field within a spherical shape
     */
    @Override
    public void createSpherical(Object particle, Location location, int amount, double spreadx, double spready, double spreadz, Collection<? extends Player> players) {
        Random r = new Random();
        double dx, dy, dz, u, mag, c;
        double mx = -spreadx;
        double my = -spready;
        double mz = -spreadz;
        double tx = spreadx * 2;
        double ty = spready * 2;
        double tz = spreadz * 2;
        for (int i = 0; i < amount; i++) {
            u = r.nextDouble();
            dx = mx + r.nextDouble() * tx;
            dy = my + r.nextDouble() * ty;
            dz = mz + r.nextDouble() * tz;

            mag = Math.sqrt(dx * dx + dy * dy + dz * dz);

            dx /= mag;
            dy /= mag;
            dz /= mag;

            c = Math.cbrt(Math.abs(u));

            Location newLocation = new Location(location.getWorld(),
                    location.getX() + dx * c,
                    location.getY() + dy * c,
                    location.getZ() + dz * c);
            createParticle(particle, newLocation, 1, players);
        }
    }
}
