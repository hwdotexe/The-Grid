package me.xfyrewolfx.thegrid.timers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.particle.ParticleEffect;

import me.xfyrewolfx.thegrid.Main;

public class SystemTick extends BukkitRunnable{
	
	ParticleEffect e;
	Main plugin;
	public SystemTick(Main c){
		e = ParticleEffect.ENCHANTMENT_TABLE;
		plugin=c;
	}
	
	public void run(){
		for(Location l : plugin.sys.keySet()){
			e.send(Bukkit.getOnlinePlayers(), l.getBlockX(), l.getBlockY(), l.getBlockZ(), 0.5, 0.5, 0.5, 0.5, 25, 30);
		}
	}
}
