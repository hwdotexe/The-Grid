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
		this.getMessages().set("charging-finished", "&aCharging finished");
		this.getMessages().set("firewallTooStrong", "&cThat system's firewall is too strong");
		this.getMessages().set("player-out-of-range", "&cYou are out of range!");
		this.getMessages().set("usedIceCube", "&aYou used an Ice Cube!");
		this.getMessages().set("player-cooled-down", "&aYour system cooled down");
		this.getMessages().set("scoreboard-title", "&e-&aTheGrid&e-");
		this.getMessages().set("exp-gained", "&aYou got %EXP% EXP!");
		this.getMessages().set("bitcoin-gained", "&aYou got %BTC% BTC!");

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
		vals.put("charging-finished", getMessages().getString("charging-finished").replaceAll("&", "§"));
		vals.put("firewallTooStrong", getMessages().getString("firewallTooStrong").replaceAll("&", "§"));
		vals.put("player-out-of-range", getMessages().getString("player-out-of-range").replaceAll("&", "§"));
		vals.put("usedIceCube", getMessages().getString("usedIceCube").replaceAll("&", "§"));
		vals.put("player-cooled-down", getMessages().getString("player-cooled-down").replaceAll("&", "§"));
		vals.put("exp-gained", getMessages().getString("exp-gained").replaceAll("&", "§"));
		vals.put("bitcoin-gained", getMessages().getString("bitcoin-gained").replaceAll("&", "§"));
		
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
	public String chargingFinished(){
		return vals.get("title")+vals.get("charging-finished");
	}
	public String getFirewallTooStrong(){
		return vals.get("title")+vals.get("firewallTooStrong");
	}
	public String outOfRange(){
		return vals.get("title")+vals.get("player-out-of-range");
	}
	public String usedIceCube(){
		return vals.get("title")+vals.get("usedIceCube");
	}
	public String cooledDown(){
		return vals.get("title")+vals.get("player-cooled-down");
	}
	public String gotEXP(int exp){
		return vals.get("title")+vals.get("exp-gained").replaceAll("%EXP%", Integer.toString(exp));
	}
	public String gotBTC(int btc){
		return vals.get("title")+vals.get("bitcoin-gained").replaceAll("%BTC%", Integer.toString(btc));
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
