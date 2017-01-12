package tech.xfyrewolfx.thegrid.files;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import tech.xfyrewolfx.thegrid.TheGrid;

public class Configuration {
	private Location tutorial;
	private Location spawn;
	private String motd;
	
	private TheGrid plugin;
	public Configuration(TheGrid c){
		plugin=c;
		plugin.saveDefaultConfig();
		plugin.reloadConfig();
		this.loadValues();
	}
	
	public void loadValues(){
		if(plugin.getConfig().contains("tutorial-location")){
			tutorial = new Location(Bukkit.getWorld(plugin.getConfig().getString("tutorial-location.w")), plugin.getConfig().getDouble("tutorial-location.x"), plugin.getConfig().getDouble("tutorial-location.y"), plugin.getConfig().getDouble("tutorial-location.z"), plugin.getConfig().getInt("tutorial-location.yaw"), plugin.getConfig().getInt("tutorial-location.pitch"));
		}else{
			tutorial = null;
		}
		
		if(plugin.getConfig().contains("spawn-location")){
			spawn = new Location(Bukkit.getWorld(plugin.getConfig().getString("spawn-location.w")), plugin.getConfig().getDouble("spawn-location.x"), plugin.getConfig().getDouble("spawn-location.y"), plugin.getConfig().getDouble("spawn-location.z"), plugin.getConfig().getInt("spawn-location.yaw"), plugin.getConfig().getInt("spawn-location.pitch"));
		}else{
			spawn = null;
		}
		
		if(plugin.getConfig().contains("motd")){
			motd = plugin.getConfig().getString("motd").replaceAll("&", "§");
		}else{
			motd = "        §f-- §a§kii§8§l [§2§lTheGrid§8§l] §a§kii§f --";
		}
		
	}
	
	public Location getTutorialLocation(){
		return tutorial;
	}
	
	public Location getSpawnLocation(){
		return spawn;
	}
	
	public String getMOTD(){
		return motd;
	}
}
