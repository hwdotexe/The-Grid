package me.xfyrewolfx.thegrid.timers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.xfyrewolfx.thegrid.Main;
import me.xfyrewolfx.thegrid.apis.TitleAPI;

public class Traceroute extends BukkitRunnable{
	
	Main plugin;
	int lvl;
	Player p;
	int ticks;
	boolean console;
	public Traceroute(Main c, Player pl, int level){
		plugin=c;
		lvl=level;
		p=pl;
		ticks=15;
		console=true;
	}

	public void run(){
		if(p != null){
			if(ticks > 0){
				ticks=ticks-1;
				
				if(console){
					TitleAPI.sendTitle(p, 0, 0, 15, "§ki§e§l Traceroute §f§ki", "§6tracing");
					console=false;
				}else{
					TitleAPI.sendTitle(p, 0, 0, 15, "§ki§e§l Traceroute §f§ki", "§etracing");
					console=true;
				}
			}else{
				List<Location> loc = new ArrayList<Location>();
				for(Location l : plugin.sys.keySet()){
					
					if(lvl > 4 && lvl < 20){
						//distance is 25 or less
						if(p.getLocation().distance(l) <= 25)
							loc.add(l);
					}
					
					if(lvl > 19 && lvl < 30){
						//distance is 40 or less
						if(p.getLocation().distance(l) <= 40)
							loc.add(l);
					}
					
					if(lvl > 29 && lvl < 40){
						//distance is 60 or less
						if(p.getLocation().distance(l) <= 60)
							loc.add(l);
					}
				}
				
				if(loc.size()>0){
					for(Location l : loc){
						p.sendMessage("§8[§eTraceroute§8] §7Found system at x§e"+l.getBlockX()+" §7y§e"+l.getBlockY()+" §7z§e"+l.getBlockZ()+" §8[Level "+plugin.sys.get(l)+"]");
					}
				}else{
					p.sendMessage("§8[§eTraceroute§8] §7No systems were found in this area.");
				}
				
				p.removeMetadata("Grid_TR", plugin);
				this.cancel();
			}
		}else{
			this.cancel();
		}
	}
}
