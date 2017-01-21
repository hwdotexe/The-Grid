package tech.xfyrewolfx.thegrid.files;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tech.xfyrewolfx.thegrid.TheGrid;

public class Messages {
	
	TheGrid plugin;
	private FileConfiguration messages;
	private File messagesFile;
	private HashMap<String, String> vals;
	public Messages(TheGrid c){
		plugin=c;
		this.messages = null;
	    this.messagesFile = null;
	    
	    vals = new HashMap<String, String>();
	    this.reloadMessages();
	    
	    if(this.getMessages().contains("title")){
	    	this.loadValues();
	    }else{
	    	this.saveDefaultValues();
	    }
	}
	
	public void saveDefaultValues(){
		this.getMessages().set("title", "&8[&aThe Grid&8]");
		this.getMessages().set("wrong-command", "&cError 404: syntax not found");
		this.getMessages().set("no-permission", "&cOperation not permitted");
		this.getMessages().set("player-joined", "&8[ &a+ &8] &7%PLAYER% established connection to the Grid");
		this.getMessages().set("player-quit", "&8[ &a- &8] &7%PLAYER% disconnected from the Grid");
		this.getMessages().set("battery-depleted", "&cYour battery is exhausted");
		this.getMessages().set("battery-full", "&aYour battery is full");
		this.getMessages().set("scoreboard-title", "&e-&aTheGrid&e-");

		this.saveMessages();
		this.loadValues();
	}
	
	public void loadValues(){
		vals.clear();
		vals.put("title", getMessages().getString("title").replaceAll("&", "§")+" ");
		vals.put("wrong-command", getMessages().getString("wrong-command").replaceAll("&", "§"));
		vals.put("no-permission", getMessages().getString("no-permission").replaceAll("&", "§"));
		vals.put("player-joined", getMessages().getString("player-joined").replaceAll("&", "§"));
		vals.put("player-quit", getMessages().getString("player-quit").replaceAll("&", "§"));
		vals.put("battery-depleted", getMessages().getString("battery-depleted").replaceAll("&", "§"));
		vals.put("battery-full", getMessages().getString("battery-full").replaceAll("&", "§"));
		
		// Make sure we're using safe values
		String sb_title = getMessages().getString("scoreboard-title").replaceAll("&", "§");
		if(sb_title.length()<=16){
			vals.put("scoreboard-title", sb_title);
		}else{
			vals.put("scoreboard-title", "§e-§aTheGrid§e-");
			plugin.getLogger().log(Level.INFO, "Value of `scoreboard-title` cannot exceed 16 characters! Using default value.");
		}
		
		plugin.getLogger().log(Level.INFO, "Loaded custom messages");
	}
	
	public String wrongCommand(){
		return vals.get("title")+vals.get("wrong-command");
	}
	public String noPermission(){
		return vals.get("title")+vals.get("no-permission");
	}
	public String playerJoined(String name){
		return vals.get("player-joined").replaceAll("%PLAYER%", name);
	}
	public String playerQuit(String name){
		return vals.get("player-quit").replaceAll("%PLAYER%", name);
	}
	public String scoreboardTitle(){
		return vals.get("scoreboard-title");
	}
	public String batteryDepleted(){
		return vals.get("title")+vals.get("battery-depleted");
	}
	public String batteryFull(){
		return vals.get("title")+vals.get("battery-full");
	}
	
	/* File Operations*/
	
	public void reloadMessages(){
		if(this.messagesFile == null){
			this.messagesFile = new File(plugin.getDataFolder(), "messages.yml");
		    this.messages = YamlConfiguration.loadConfiguration(this.messagesFile);
		}
	}
		 
	private FileConfiguration getMessages(){
		if(this.messages == null){
			reloadMessages();
		}
	   return this.messages;
	}
		 
	private void saveMessages(){
		if ((this.messages == null) || (this.messagesFile == null)) {
			return;
		}
		try{
			getMessages().save(this.messagesFile);
		}catch(Exception ex){
			plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.messagesFile, ex);
		}
	}
}
