package me.xfyrewolfx.thegrid.timers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Tips extends BukkitRunnable{
	
	List<String> ann;
	int i;
	public Tips(){
		ann = new ArrayList<String>();
		ann.add("§8[§6Tip§8] §7Use §6/help §7for beginner's tips");
		ann.add("§8[§6Tip§8] §7Use §6/shop §7to buy new gear.");
		ann.add("§8[§6Tip§8] §7The §6Traceroute §7can detect nearby systems.");
		ann.add("§8[§6Tip§8] §7The §6Firewall §7can help prevent attacks, but uses a lot of energy.");
		ann.add("§8[§6Tip§8] §7Press §6F3 §7to see your coordinates.");
		ann.add("§8[§6Tip§8] §7Your gear will upgrade every §a10 levels§7!");
		
		i=0;
	}
	
	public void run(){
		i++;
		
		if(i<ann.size()){
			for(Player p : Bukkit.getOnlinePlayers()){
				p.sendMessage(ann.get(i));
			}
		}else{
			i=0;
			this.run();
		}
	}
}
