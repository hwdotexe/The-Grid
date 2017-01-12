package tech.xfyrewolfx.thegrid;

import org.bukkit.Location;

public class System {
	private Location loc;
	private String name;
	private int level;
	public System(Location l, String n, int lvl){
		loc = l;
		level = lvl;
		name=n;
	}
	
	public Location getLocation(){
		return loc;
	}
	
	public int getLevel(){
		return level;
	}
	
	public String getName(){
		return name;
	}
}
