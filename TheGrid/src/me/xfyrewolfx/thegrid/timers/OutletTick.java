package me.xfyrewolfx.thegrid.timers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.particle.ParticleEffect;

import me.xfyrewolfx.thegrid.Main;

public class OutletTick extends BukkitRunnable{
	
	ParticleEffect e;
	Main plugin;
	public OutletTick(Main c){
		e = ParticleEffect.CRIT;
		plugin=c;
	}
	
	public void run(){
		for(Location l : plugin.out){
			e.send(Bukkit.getOnlinePlayers(), l.getBlockX(), l.getBlockY(), l.getBlockZ(), 0.5, 0.5, 0.5, 1, 15, 30);
		}
	}
}
