package tech.xfyrewolfx.thegrid;

import org.bukkit.Location;

public class GridOutlet {
	private Location loc;
	public GridOutlet(Location l){
		loc=l.getBlock().getLocation();
	}
	
	public Location getLocation(){
		return loc;
	}
}
