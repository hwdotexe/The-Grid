package tech.xfyrewolfx.thegrid.runnables;

import org.bukkit.scheduler.BukkitRunnable;

import com.comphenix.protocol.wrappers.EnumWrappers.Particle;

import tech.xfyrewolfx.thegrid.GridOutlet;
import tech.xfyrewolfx.thegrid.GridSystem;
import tech.xfyrewolfx.thegrid.TheGrid;
import tech.xfyrewolfx.thegrid.apis.ParticleHandler;

public class Sparks extends BukkitRunnable{
	private TheGrid plugin;
	public Sparks(TheGrid c){
		plugin=c;
	}
	
	public void run(){
		for(GridOutlet o : plugin.getOutlets().getOutletObjects()){
			if(o.getLocation().getWorld() != null)
				ParticleHandler.showAt(o.getLocation(), Particle.CRIT);
		}
		for(GridSystem s : plugin.getSystems().getSystemObjects()){
			if(s.getLocation().getWorld() != null)
				ParticleHandler.showAt(s.getLocation(), Particle.ENCHANTMENT_TABLE);
		}
	}
}
