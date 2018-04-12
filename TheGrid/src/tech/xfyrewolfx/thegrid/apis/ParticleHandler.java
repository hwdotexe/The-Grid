package tech.xfyrewolfx.thegrid.apis;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.comphenix.packetwrapper.WrapperPlayServerWorldParticles;
import com.comphenix.protocol.wrappers.EnumWrappers.Particle;

public class ParticleHandler{
	public static void showAt(Location l, Particle type) {
		WrapperPlayServerWorldParticles particle = new WrapperPlayServerWorldParticles();
		
		particle.setX(l.getBlockX()+0.5f);
		particle.setY(l.getBlockY());
		particle.setZ(l.getBlockZ()+0.5f);
		
		particle.setOffsetX(0.5f);
		particle.setOffsetZ(0.5f);
		particle.setOffsetY(0.5f);
		particle.setNumberOfParticles(25);
		
		particle.setParticleType(type);
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(p.getWorld().getName().equals(l.getWorld().getName()))
				particle.sendPacket(p);
		}
	}
}