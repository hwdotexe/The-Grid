package me.xfyrewolfx.thegrid;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PlayerData {
	Main plugin;
	Player p;
	private FileConfiguration player;
	private File playerFile;
	PlayerData(Main c, Player pl){
		plugin=c;
		p=pl;
		
		File f = new File(plugin.getDataFolder()+"//playerdata");
		if(!f.exists())
			f.mkdir();
		
		this.player = null;
	    this.playerFile = null;
	    reloadplayer();
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
