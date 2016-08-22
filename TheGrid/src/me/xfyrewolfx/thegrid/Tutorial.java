package me.xfyrewolfx.thegrid;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.xfyrewolfx.thegrid.timers.BatteryTick;

public class Tutorial extends BukkitRunnable{
	
	Main plugin;
	Player p;
	int i;
	List<String> msgs;
	Tutorial(Main c, Player pl){
		plugin=c;
		p=pl;
		msgs = new ArrayList<String>();
		msgs.add("§8[ §2Tutorial §8] §7Welcome to §aThe Grid§7, a server dedicated to the fun of §6Hacking§7.");
		msgs.add("§8[ §2Tutorial §8] §7In this game, you are an adventurous hacker whose goal is to become as powerful as possible.");
		msgs.add("§8[ §2Tutorial §8] §7To grow stronger, you need to collect §3EXP§7, a reward you earn for each successful hack.");
		msgs.add("§8[ §2Tutorial §8] §7You can also buy new equipment by using the in-game currency §6Bitcoin§7.");
		msgs.add("§8[ §2Tutorial §8] §3EXP §7allows you to level-up and gain access to new equipment. ");
		msgs.add("§8[ §2Tutorial §8] §7Your equipment is made up of 4 main items: your §6System§7, your §6Firewall§7, your §6Viruses§7, and your §6Battery§7.");
		msgs.add("§8[ §2Tutorial §8] §7The §6Battery§7 is what keeps your system running and needs to be recharged regularly.");
		msgs.add("§8[ §2Tutorial §8] §6Viruses§7 are deployed on other systems and bring in §3EXP§7 as a result.");
		msgs.add("§8[ §2Tutorial §8] §7The §6Firewall§7 is used to slow or prevent others from hacking your system.");
		msgs.add("§8[ §2Tutorial §8] §7There are no rules in §aThe Grid§7. You may team up with others, or play solo. Be sure to collect as much §3EXP§7 as you can, and prepare for an exciting adventure!");
		i=0;
	}
	
	public void run(){
		if(i < msgs.size()){
				p.sendMessage(msgs.get(i));
			
			i++;
		}else{
			if(plugin.swnLoc != null)
				p.teleport(plugin.swnLoc);
			
			p.getInventory().setContents(plugin.getPlayerInventory(p.getName()));
			p.setLevel(plugin.getPlayerBattery(p.getName()));
			new BatteryTick(p, plugin).runTaskTimer(plugin, 600, 600);
			
			plugin.giveNewScoreboard(p);
			
			p.removeMetadata("tutorial", plugin);
			
			this.cancel();
		}
	}
}
