package tech.xfyrewolfx.thegrid;

import org.bukkit.Location;

public class GSystem {
	private Location loc;
	private String name;
	private int level;
	public GSystem(Location l, String n, int lvl){
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
