package me.xfyrewolfx.thegrid.timers;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import me.xfyrewolfx.thegrid.Main;
import me.xfyrewolfx.thegrid.apis.Particle;

public class OutletTick extends BukkitRunnable{
	
	Main plugin;
	public OutletTick(Main c){
		plugin=c;
	}
	
	public void run(){
		for(Location l : plugin.out){
			Particle.CLOUD.showAt(l);
		}
	}
}
