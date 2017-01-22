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

import tech.xfyrewolfx.thegrid.Items;
import tech.xfyrewolfx.thegrid.TheGrid;
import tech.xfyrewolfx.thegrid.apis.TitleAPI;

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
	private int iceCubes;
	private List<String> viruses;
	
	private boolean firewallActive;
	private boolean isCharging;
	private boolean isTracing;
	private boolean isCoolingDown;
	private boolean isHacking;
	
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
			iceCubes = getPlayer().getInt("icecubes");
			isCoolingDown = getPlayer().getBoolean("coolingdown");
		}else{
			level = 1;
			btc = 5;
			exp = 0;
			battery = 12;
			iceCubes = 1;
			viruses = new ArrayList<String>();
			viruses.add("shutdown");
			isCoolingDown = false;
		}
		
		firewallActive = false;
		isCharging = false;
		isTracing = false;
		isHacking = false;
	}
	
	public boolean getIsTracing(){
		return isTracing;
	}
	
	public void setIsTracing(boolean tracing){
		isTracing = tracing;
	}
	
	public boolean getIsHacking(){
		return isHacking;
	}
	
	public void setIsHacking(boolean hacking){
		isHacking = hacking;
	}
	
	public boolean getIsCoolingDown(){
		return isCoolingDown;
	}
	
	public void setIsCoolingDown(boolean cooling){
		isCoolingDown = cooling;
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
	
	public void setBattery(int bat){
		battery = bat;
	}
	
	public int getExp(){
		return exp;
	}
	
	public void setExp(int xp){
		exp = xp;
	}
	
	public int getLevel(){
		return level;
	}
	
	public void setLevel(int lvl){
		level = lvl;
	}
	
	public int getBTC(){
		return btc;
	}
	
	public void setBTC(int nbtc){
		btc = nbtc;
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
	
	public List<String> getViruses(){
		return viruses;
	}
	
	public int getIceCubes(){
		return iceCubes;
	}
	
	public void setIceCubes(int cubes){
		iceCubes = cubes;
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
	
	public void addExp(int nexp){
		int maxexp = level * 100;
		
		if(exp+nexp >= maxexp){
			this.setLevel(level+1);
			this.setExp((exp+nexp)-maxexp);
			
			TitleAPI.sendTitle(p, 20, 20, 150, "§8[ §e! §8]", "§e§lLEVEL UP");
			
			// TODO add notifications
			if(getLevel() == 5){ 
				p.getInventory().setContents(Items.getBasicInventoryTraceroute());
			}
			
			if(getLevel() == 10){ 
				p.getInventory().setContents(Items.getSpecialistInventory());
				getViruses().add("sql");
				getViruses().add("cryptolocker");
				setBattery(24);
			}
			
			if(getLevel() == 20){ 
				p.getInventory().setContents(Items.getEliteInventory());
				setBattery(48);
			}
			
			if(getLevel() == 30){ 
				p.getInventory().setContents(Items.getProInventory());
				getViruses().add("ddos");
				setBattery(72);
			}
		}else{
			this.setExp(exp+nexp);
		}
		
		plugin.updateScoreboard(p);
	}
	
	public void saveValues(){
		getPlayer().set("level", level);
		getPlayer().set("btc", btc);
		getPlayer().set("exp", exp);
		getPlayer().set("battery", battery);
		getPlayer().set("viruses", viruses);
		getPlayer().set("icecubes", iceCubes);
		getPlayer().set("coolingdown", isCoolingDown);
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
