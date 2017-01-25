package tech.xfyrewolfx.thegrid.files;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import tech.xfyrewolfx.thegrid.TheGrid;

public class Configuration {
	private Location tutorial;
	private Location spawn;
	private String motd;
	private boolean doTutorialPotionEffects;
	private boolean overrideMOTD;
	
	private TheGrid plugin;
	public Configuration(TheGrid c){
		plugin=c;
		this.loadValues();
	}
	
	public void loadValues(){
		plugin.saveDefaultConfig();
		plugin.reloadConfig();
		
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
		
		if(plugin.getConfig().contains("doTutorialPotionEffects")){
			doTutorialPotionEffects = plugin.getConfig().getBoolean("doTutorialPotionEffects");
		}else{
			doTutorialPotionEffects = true;
		}
		
		if(plugin.getConfig().contains("overrideMOTD")){
			overrideMOTD = plugin.getConfig().getBoolean("overrideMOTD");
		}else{
			overrideMOTD = true;
		}
		
		if(plugin.getConfig().contains("motd")){
			motd = plugin.getConfig().getString("motd").replaceAll("&", "§");
		}else{
			motd = "        §f-- §a§kii§8§l [§2§lTheGrid§8§l] §a§kii§f --";
		}
		
		plugin.getLogger().log(Level.INFO, "Loaded configuration");
	}
	
	public Location getTutorialLocation(){
		return tutorial;
	}
	
	public void setTutorialLocation(Location l){
		tutorial = l;
		plugin.getConfig().set("tutorial-location.w", l.getWorld().getName());
		plugin.getConfig().set("tutorial-location.x", l.getX());
		plugin.getConfig().set("tutorial-location.y", l.getY());
		plugin.getConfig().set("tutorial-location.z", l.getZ());
		plugin.getConfig().set("tutorial-location.yaw", l.getYaw());
		plugin.getConfig().set("tutorial-location.pitch", l.getPitch());
		plugin.saveConfig();
	}
	
	public Location getSpawnLocation(){
		return spawn;
	}
	
	public void setSpawnLocation(Location l){
		spawn = l;
		plugin.getConfig().set("spawn-location.w", l.getWorld().getName());
		plugin.getConfig().set("spawn-location.x", l.getX());
		plugin.getConfig().set("spawn-location.y", l.getY());
		plugin.getConfig().set("spawn-location.z", l.getZ());
		plugin.getConfig().set("spawn-location.yaw", l.getYaw());
		plugin.getConfig().set("spawn-location.pitch", l.getPitch());
		plugin.saveConfig();
	}
	
	public String getMOTD(){
		return motd;
	}
	
	public boolean getOverrideMOTD(){
		return overrideMOTD;
	}
	
	public boolean getDoTutorialPotionEffects(){
		return doTutorialPotionEffects;
	}
}
