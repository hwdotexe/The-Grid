package tech.xfyrewolfx.thegrid;

import org.bukkit.Location;

public class System {
	private Location loc;
	private int level;
	public System(Location l, int lvl){
		loc = l;
		level = lvl;
	}
	
	public Location getLocation(){
		return loc;
	}
	
	public int getLevel(){
		return level;
	}
}
