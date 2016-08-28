package me.xfyrewolfx.thegrid;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PlayerData {
	Main plugin;
	Player p;
	private FileConfiguration player;
	private File playerFile;
	private BossBar batteryBar;
	private BossBar cooldownBar;
	PlayerData(Main c, Player pl){
		plugin=c;
		p=pl;
		
		File f = new File(plugin.getDataFolder()+"//playerdata");
		if(!f.exists())
			f.mkdir();
		
		this.player = null;
	    this.playerFile = null;
	    reloadplayer();
	    
	    batteryBar = Bukkit.createBossBar("Battery", BarColor.YELLOW, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
	    cooldownBar = Bukkit.createBossBar("Cooldown", BarColor.BLUE, BarStyle.SOLID, BarFlag.DARKEN_SKY);
	}
	
	public BossBar getBatteryBar(){
		return batteryBar;
	}
	
	public BossBar getCooldownBar(){
		return cooldownBar;
	}
	
	  public void reloadplayer()
		 {
		   if (this.playerFile == null)
		   {
		     this.playerFile = new File(plugin.getDataFolder(), "//playerdata//"+p.getUniqueId().toString()+".yml");
		     this.player = YamlConfiguration.loadConfiguration(this.playerFile);
		   }
		 }
		 
		 public FileConfiguration getplayer()
		 {
		   if (this.player == null) {
		     reloadplayer();
		   }
		   return this.player;
		 }
		 
		 public void saveplayer()
		 {
		   if ((this.player == null) || (this.playerFile == null)) {
		     return;
		   }
		   try
		   {
		     getplayer().save(this.playerFile);
		   }
		   catch (Exception ex)
		   {
		     plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.playerFile, ex);
		   }
		 }
}
