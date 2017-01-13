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
		this.getMessages().set("wrong-command", "&cWrong command or usage!");
		this.getMessages().set("no-permission", "&cYou don't have permission!");
		this.getMessages().set("player-joined", "&8[ &a+ &8] &7%PLAYER% joined the Grid");
		this.getMessages().set("player-quit", "&8[ &a- &8] &7%PLAYER% quit the Grid");

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
		
		plugin.getLogger().log(Level.INFO, "Loaded custom messages");
	}
	
	public String getTitle(){
		return vals.get("title");
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
