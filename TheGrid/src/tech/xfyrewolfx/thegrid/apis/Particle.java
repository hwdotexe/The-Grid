package tech.xfyrewolfx.thegrid.apis;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.Packet;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;

public class Particle{
    public static final Particle SPARKLE = new Particle(21, "happyVillager", 0F, 20);
    public static final Particle PORTAL = new Particle(24, "portal", 1F, 100);
    public static final Particle MAGIC_RUNES = new Particle(25, "enchantmenttable", 1F, 100);
    public static final Particle LAVA_SPARK = new Particle(27, "lava", 0F, 4);
    public static final Particle CLOUD = new Particle(29, "cloud", 0.1F, 50);
    public static final Particle RED_SMOKE = new Particle(30, "reddust", 0F, 40);
    public static final Particle BLOCK_DUST = new Particle(38, "blockdust", 0.1F, 100);

    private static final Random RANDOM = new Random();
    private final int id;
    private final String name;
    private final float speed;
    private final int amount;
    private final int[] data;

    public Particle(int id, String name, float speed, int amount, int[] data) {
        this.id = id;
        this.name = name;
        this.speed = speed;
        this.amount = amount;
        this.data = data;
    }

    public Particle(int id, String name, float speed, int amount) {
        this(id, name, speed, amount, new int[0]);
    }

    @SuppressWarnings("deprecation")
	public Particle withMaterial(Material material) {
        return new Particle(id, name, speed, amount, new int[]{material.getId(), 0});
    }

    public void showAt(Location location) {
        EnumParticle particleType = EnumParticle.a(id);
        float x = (float) location.getX();
        float y = (float) location.getY();
        float z = (float) location.getZ();
        float ox = RANDOM.nextFloat();
        float oy = RANDOM.nextFloat();
        float oz = RANDOM.nextFloat();
        Packet<?> particles = new PacketPlayOutWorldParticles(particleType, false, x, y, z, ox, oy, oz, speed, amount, data);
        for (Entity entity : location.getWorld().getNearbyEntities(location, 50, 50, 50)) {
            if (entity instanceof Player) {
                ((CraftPlayer) entity).getHandle().playerConnection.sendPacket(particles);
            }
        }
    }
}