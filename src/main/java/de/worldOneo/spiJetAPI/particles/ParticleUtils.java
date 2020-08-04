package de.worldOneo.spiJetAPI.particles;

public class ParticleUtils {
    private static final ParticleUtils instance = new ParticleUtils();
    private final IParticleWrapper particleWrapper;

    private ParticleUtils(){
        IParticleWrapper tempParticleWrapper;
        try {
            Class.forName("org.bukkit.Particle");
            tempParticleWrapper = new ParticleNew();
        } catch (ClassNotFoundException e) {
            tempParticleWrapper = new ParticleLegacy();
        }
        particleWrapper = tempParticleWrapper;
    }

    public static IParticleWrapper getWrapper(){
        return instance.particleWrapper;
    }
}
