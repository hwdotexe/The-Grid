package tech.xfyrewolfx.thegrid.files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tech.xfyrewolfx.thegrid.Outlet;
import tech.xfyrewolfx.thegrid.TheGrid;

public class Outlets {
	
	TheGrid plugin;
	private FileConfiguration outlets;
	private File outletsFile;
	private List<Outlet> outObjs;
	public Outlets(TheGrid c){
		plugin=c;
		this.outlets = null;
	    this.outletsFile = null;
	    outObjs = new ArrayList<Outlet>();
	    
	    this.reloadOutlets();
	    this.loadValues();
	}
	
	public void loadValues(){
		outObjs.clear();
		for(int i=0; i<this.getOutlets().getInt("size"); i++){
			Location l = new Location(Bukkit.getWorld(this.getOutlets().getString(i+".w")), this.getOutlets().getDouble(i+".x"), this.getOutlets().getDouble(i+".y"), this.getOutlets().getDouble(i+".z"));
			outObjs.add(new Outlet(l));
		}
		plugin.getLogger().log(Level.INFO, "Loaded outlets");
	}
	
	public void saveValues(){
		this.getOutlets().set("size", this.outObjs.size());
		for(int i=0; i<this.outObjs.size(); i++){
			this.getOutlets().set(i+".x", this.outObjs.get(i).getLocation().getBlockX());
			this.getOutlets().set(i+".y", this.outObjs.get(i).getLocation().getBlockY());
			this.getOutlets().set(i+".z", this.outObjs.get(i).getLocation().getBlockZ());
		}
		this.saveOutlets();
		plugin.getLogger().log(Level.INFO, "Saved outlets");
	}
	
	public List<Outlet> getOutletObjects(){
		return this.outObjs;
	}
	
	/* File Operations*/
	
	private void reloadOutlets(){
		if(this.outletsFile == null){
			this.outletsFile = new File(plugin.getDataFolder(), "outlets.grid");
		    this.outlets = YamlConfiguration.loadConfiguration(this.outletsFile);
		}
	}
		 
	private FileConfiguration getOutlets(){
		if(this.outlets == null){
			reloadOutlets();
		}
	   return this.outlets;
	}
		 
	private void saveOutlets(){
		if ((this.outlets == null) || (this.outletsFile == null)) {
			return;
		}
		try{
			getOutlets().save(this.outletsFile);
		}catch(Exception ex){
			plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.outletsFile, ex);
		}
	}
}
