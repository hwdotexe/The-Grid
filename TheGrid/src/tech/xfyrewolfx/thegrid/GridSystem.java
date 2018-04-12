package tech.xfyrewolfx.thegrid;

import org.bukkit.Location;

public class GridSystem {
	private Location loc;
	private String name;
	private int level;
	public GridSystem(Location l, String n, int lvl){
		loc = l.getBlock().getLocation();
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
