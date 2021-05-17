package de.worldoneo.spijetapi.particles;

public class ParticleUtils {
    private static final ParticleUtils INSTANCE = new ParticleUtils();
    private final ParticleWrapper particleWrapper;

    private ParticleUtils() {
        ParticleWrapper tempParticleWrapper;
        try {
            Class.forName("org.bukkit.Particle");
            tempParticleWrapper = new ParticleNew();
        } catch (ClassNotFoundException e) {
            tempParticleWrapper = new ParticleLegacy();
        }
        this.particleWrapper = tempParticleWrapper;
    }

    public static ParticleWrapper getWrapper() {
        return INSTANCE.particleWrapper;
    }
}