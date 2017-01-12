package tech.xfyrewolfx.thegrid.files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tech.xfyrewolfx.thegrid.System;

import tech.xfyrewolfx.thegrid.TheGrid;

public class Systems {
	
	TheGrid plugin;
	private FileConfiguration systems;
	private File systemsFile;
	private List<System> sysObjs;
	public Systems(TheGrid c){
		plugin=c;
		this.systems = null;
	    this.systemsFile = null;
	    sysObjs = new ArrayList<System>();
	    
	    this.reloadSystems();
	    this.loadValues();
	}
	
	public void loadValues(){
		sysObjs.clear();
		for(int i=0; i<this.getSystems().getInt("size"); i++){
			Location l = new Location(Bukkit.getWorld(this.getSystems().getString(i+".w")), this.getSystems().getDouble(i+".x"), this.getSystems().getDouble(i+".y"), this.getSystems().getDouble(i+".z"));
			int level = this.getSystems().getInt(i+".level");
			String name = this.getSystems().getString(i+".name");
			sysObjs.add(new System(l, name, level));
		}
		plugin.getLogger().log(Level.INFO, "Loaded NPC systems");
	}
	
	public void saveValues(){
		this.getSystems().set("size", this.sysObjs.size());
		for(int i=0; i<this.sysObjs.size(); i++){
			this.getSystems().set(i+".x", this.sysObjs.get(i).getLocation().getBlockX());
			this.getSystems().set(i+".y", this.sysObjs.get(i).getLocation().getBlockY());
			this.getSystems().set(i+".z", this.sysObjs.get(i).getLocation().getBlockZ());
			this.getSystems().set(i+".level", this.sysObjs.get(i).getLevel());
			this.getSystems().set(i+".name", this.sysObjs.get(i).getName());
		}
		this.saveSystems();
		plugin.getLogger().log(Level.INFO, "Saved NPC systems");
	}
	
	public List<System> getSystemObjects(){
		return this.sysObjs;
	}
	
	/* File Operations*/
	
	private void reloadSystems(){
		if(this.systemsFile == null){
			this.systemsFile = new File(plugin.getDataFolder(), "systems.grid");
		    this.systems = YamlConfiguration.loadConfiguration(this.systemsFile);
		}
	}
		 
	private FileConfiguration getSystems(){
		if(this.systems == null){
			reloadSystems();
		}
	   return this.systems;
	}
		 
	private void saveSystems(){
		if ((this.systems == null) || (this.systemsFile == null)) {
			return;
		}
		try{
			getSystems().save(this.systemsFile);
		}catch(Exception ex){
			plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.systemsFile, ex);
		}
	}
}
