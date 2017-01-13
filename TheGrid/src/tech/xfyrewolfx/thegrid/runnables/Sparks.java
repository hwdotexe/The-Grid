package tech.xfyrewolfx.thegrid.runnables;

import org.bukkit.scheduler.BukkitRunnable;

import tech.xfyrewolfx.thegrid.Outlet;
import tech.xfyrewolfx.thegrid.System;
import tech.xfyrewolfx.thegrid.TheGrid;
import tech.xfyrewolfx.thegrid.apis.Particle;

public class Sparks extends BukkitRunnable{
	private TheGrid plugin;
	public Sparks(TheGrid c){
		plugin=c;
	}
	
	public void run(){
		for(Outlet o : plugin.getOutlets().getOutletObjects()){
			Particle.CLOUD.showAt(o.getLocation());
		}
		for(System s : plugin.getSystems().getSystemObjects()){
			Particle.MAGIC_RUNES.showAt(s.getLocation());
		}
	}
}
