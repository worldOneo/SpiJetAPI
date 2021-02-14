package de.worldoneo.spijetapi.particles;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Random;
import java.util.function.Consumer;

public abstract class ParticleWrapper implements IParticleWrapper {
    private static final double TWO_PI = 2 * Math.PI;

    /**
     * Create a field of particles around the location.
     *
     * @param particle the Particle type
     * @param location the base location the "middle"
     * @param amount   the total amount of Particles
     * @param spreadx  the spreadx (Random spread on the x dimension)
     * @param spready  the spready (Random spread on the y dimension)
     * @param spreadz  the spreadz (Random spread on the z dimension)
     */
    @Override
    public void createField(Object particle, Location location, int amount, double spreadx, double spready, double spreadz) {
        createField(location, amount, spreadx, spready, spreadz, l -> createParticle(particle, l, amount));
    }

    /**
     * Create a field of particles around the location.
     *
     * @param particle the Particle type
     * @param location the base location the "middle"
     * @param amount   the total amount of Particles
     * @param spreadx  the spreadx (Random spread on the x dimension)
     * @param spready  the spready (Random spread on the y dimension)
     * @param spreadz  the spreadz (Random spread on the z dimension)
     * @param players  the players to send the particle to
     */
    public void createField(Object particle, Location location, int amount,
                            double spreadx, double spready, double spreadz,
                            Collection<? extends Player> players) {
        createField(location, amount, spreadx, spready, spreadz, l -> createParticle(particle, l, 1, players));
    }

    public void createField(Location location, int amount, double spreadx,
                            double spready, double spreadz, Consumer<Location> spawner) {
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
            spawner.accept(newLocation);
        }
    }

    @Override
    public void createSpherical(Object particle, Location location, int amount, double spreadx,
                                double spready, double spreadz, Collection<? extends Player> players) {
        createSpherical(location, amount, spreadx, spready, spreadz, l -> createParticle(particle, l, amount, players));
    }

    @Override
    public void createSpherical(Object particle, Location location, int amount, double spreadx, double spready, double spreadz) {
        createSpherical(location, amount, spreadx, spready, spreadz, l -> createParticle(particle, l, amount));
    }

    public void createSpherical(Location location, int amount, double spreadx,
                                double spready, double spreadz, Consumer<Location> spawner) {
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
            spawner.accept(newLocation);
        }
    }

    /**
     * Draw a line from x,y,z to x1,x2,x3 in world world
     *
     * @param x     From x
     * @param y     From y
     * @param z     From z
     * @param x1    To x
     * @param y1    to y
     * @param z1    to z
     * @param world the world to create the particles in
     */
    @Override
    public void createLine(Object particle, World world, int amount,
                           double x, double y, double z, double x1, double y1, double z1) {
        createLine(world, amount, x, y, z, x1, y1, z1, l -> createParticle(particle, l, 1));
    }

    /**
     * Draw a line from x,y,z to x1,x2,x3 in world world
     *
     * @param x       From x
     * @param y       From y
     * @param z       From z
     * @param x1      To x
     * @param y1      to y
     * @param z1      to z
     * @param world   the world to create the particles in
     * @param players the players to spawn the particles for
     */
    @Override
    public void createLine(Object particle, World world, int amount,
                           double x, double y, double z, double x1, double y1,
                           double z1, Collection<? extends Player> players) {
        createLine(world, amount, x, y, z, x1, y1, z1, l -> createParticle(particle, l, 1, players));
    }

    public void createLine(World world, int amount,
                           double x, double y, double z, double x1, double y1, double z1, Consumer<Location> spawner) {
        Random random = new Random();
        double factor, x2, y2, z2;
        for (int i = 0; i < amount; i++) {
            factor = random.nextDouble();
            x2 = factor * (x1 - x) + x;
            y2 = factor * (y1 - y) + y;
            z2 = factor * (z1 - z) + z;
            Location location = new Location(world, x2, y2, z2);
            spawner.accept(location);
        }
    }

    /**
     * Create a circle out of particles.
     *
     * @param particle The Particle/Effect to create
     * @param location The location of the circle
     * @param amount   The total amount of particles
     * @param radius   The radius of the circle
     * @param players  The players to send the particle
     */
    @Override
    public void createCircle(Object particle, Location location,
                             int amount, double radius, Collection<? extends Player> players) {
        createCircle(location, amount, radius, l -> createParticle(particle, l, 1, players));
    }

    /**
     * Create a circle out of particles.
     *
     * @param particle The Particle/Effect to create
     * @param location The location of the circle
     * @param amount   The total amount of particles
     * @param radius   The radius of the circle
     */
    @Override
    public void createCircle(Object particle, Location location, int amount, double radius) {
        createCircle(location, amount, radius, l -> createParticle(particle, l, 1));
    }

    public void createCircle(Location location, int amount, double radius, Consumer<Location> spawner) {
        Random random = new Random();
        double r, dx, dz;
        for (int i = 0; i < amount; i++) {
            r = random.nextDouble() * TWO_PI;
            dx = Math.cos(r) * radius;
            dz = Math.sin(r) * radius;
            Location newLocation = new Location(location.getWorld(), location.getX() + dx, location.getY(), location.getZ() + dz);
            spawner.accept(newLocation);
        }
    }
}
