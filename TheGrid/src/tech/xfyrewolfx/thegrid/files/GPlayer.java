package tech.xfyrewolfx.thegrid.files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.xfyrewolfx.thegrid.statics.Items;
import tech.xfyrewolfx.thegrid.TheGrid;

public class GPlayer {
	private Player p;
	private FileConfiguration player;
	private File playerFile;
	private BossBar batteryBar;
	private BossBar cooldownBar;
	private TheGrid plugin;
	
	private int level;
	private int btc;
	private int exp;
	private int battery;
	private List<String> viruses;
	
	private boolean firewallActive;
	private boolean isCharging;
	
	public GPlayer(TheGrid c, Player pl){
		plugin=c;
		p=pl;
		this.player = null;
	    this.playerFile = null;
		
		File f = new File(plugin.getDataFolder()+"//playerdata");
		if(!f.exists())
			f.mkdir();
	    
	    batteryBar = Bukkit.createBossBar("Battery", BarColor.YELLOW, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
	    cooldownBar = Bukkit.createBossBar("Cooldown", BarColor.BLUE, BarStyle.SOLID, BarFlag.DARKEN_SKY);
	    
	    loadValues();
	}
	
	private void loadValues(){
		reloadPlayer();
		
		if(getPlayer().contains("level")){
			level = getPlayer().getInt("level");
			btc = getPlayer().getInt("btc");
			exp = getPlayer().getInt("exp");
			battery = getPlayer().getInt("battery");
			viruses = getPlayer().getStringList("viruses");
		}else{
			level = 1;
			btc = 5;
			exp = 0;
			battery = 12;
			viruses = new ArrayList<String>();
			viruses.add("shutdown");
		}
		
		firewallActive = false;
		isCharging = false;
	}
	
	public BossBar getBatteryBar(){
		return batteryBar;
	}
	
	public BossBar getCooldownBar(){
		return cooldownBar;
	}
	
	public int getBattery(){
		return battery;
	}
	
	public boolean getFirewallActive(){
		return firewallActive;
	}
	
	public void setFirewallActive(boolean active){
		firewallActive = active;
	}
	
	public boolean getIsCharging(){
		return isCharging;
	}
	
	public void setIsCharging(boolean charging){
		isCharging = charging;
	}
	
	public ItemStack[] getInventoryItems(){
		if(level < 5){
			return Items.getBasicInventory();
		}
		if(level > 4 && level < 10){
			return Items.getBasicInventoryTraceroute();
		}
		if(level > 9 && level < 20){
			return Items.getSpecialistInventory();
		}
		if(level > 19 && level < 30){
			return Items.getEliteInventory();
		}
		if(level > 29){
			return Items.getProInventory();
		}
		
		return null;
	}
	
	public void saveValues(){
		getPlayer().set("level", level);
		getPlayer().set("btc", btc);
		getPlayer().set("exp", exp);
		getPlayer().set("battery", battery);
		getPlayer().set("viruses", viruses);
		savePlayer();
	}
	
	/* File Operations*/
	
	private void reloadPlayer(){
		if(this.playerFile == null){
			this.playerFile = new File(plugin.getDataFolder(), "//playerdata//"+p.getUniqueId().toString()+".yml");
		    this.player = YamlConfiguration.loadConfiguration(this.playerFile);
		}
	}
		 
	private FileConfiguration getPlayer(){
		if(this.player == null){
			reloadPlayer();
		}
	   return this.player;
	}
		 
	private void savePlayer(){
		if ((this.player == null) || (this.playerFile == null)) {
			return;
		}
		try{
			getPlayer().save(this.playerFile);
		}catch(Exception ex){
			plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.playerFile, ex);
		}
	}
}
