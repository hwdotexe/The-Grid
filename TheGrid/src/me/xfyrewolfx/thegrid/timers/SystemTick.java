package me.xfyrewolfx.thegrid.timers;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import me.xfyrewolfx.thegrid.Main;
import me.xfyrewolfx.thegrid.apis.Particle;

public class SystemTick extends BukkitRunnable{
	
	Main plugin;
	public SystemTick(Main c){
		plugin=c;
	}
	
	public void run(){
		for(Location l : plugin.sys.keySet()){
			Particle.MAGIC_RUNES.showAt(l);
		}
	}
}
