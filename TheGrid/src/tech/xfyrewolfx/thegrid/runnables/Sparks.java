package tech.xfyrewolfx.thegrid.runnables;

import org.bukkit.scheduler.BukkitRunnable;

import tech.xfyrewolfx.thegrid.Outlet;
import tech.xfyrewolfx.thegrid.GSystem;
import tech.xfyrewolfx.thegrid.TheGrid;
import tech.xfyrewolfx.thegrid.apis.Particle;

public class Sparks extends BukkitRunnable{
	private TheGrid plugin;
	public Sparks(TheGrid c){
		plugin=c;
	}
	
	public void run(){
		for(Outlet o : plugin.getOutlets().getOutletObjects()){
			if(o.getLocation().getWorld() != null)
				Particle.CLOUD.showAt(o.getLocation());
		}
		for(GSystem s : plugin.getSystems().getSystemObjects()){
			if(s.getLocation().getWorld() != null)
				Particle.MAGIC_RUNES.showAt(s.getLocation());
		}
	}
}
