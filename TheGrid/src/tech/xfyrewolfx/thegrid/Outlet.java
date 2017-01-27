package tech.xfyrewolfx.thegrid;

import org.bukkit.Location;

public class Outlet {
	private Location loc;
	public Outlet(Location l){
		loc=l;
	}
	
	public Location getLocation(){
		return loc;
	}
}
