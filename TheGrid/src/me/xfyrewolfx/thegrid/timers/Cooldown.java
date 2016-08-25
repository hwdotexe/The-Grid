package me.xfyrewolfx.thegrid.timers;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.xfyrewolfx.thegrid.Main;
import me.xfyrewolfx.thegrid.statics.MSG;

public class Cooldown extends BukkitRunnable{
	
	int secs;
	Player p;
	Main plugin;
	Cooldown(int seconds, Player pl, Main c){
		secs=seconds;
		p=pl;
		plugin=c;
		
		p.sendMessage("§8[ §4Cooldown §8] §7Cooling down in §a"+secs+" §7seconds.");
	}
	
	public void run(){
		if(secs > 0){
			secs=secs-1;
			
			int cubes=0;
			if(plugin.getConfig().contains(p.getUniqueId().toString()+".icecubes"))
				cubes = plugin.getConfig().getInt(p.getUniqueId().toString()+".icecubes");
			
			if(cubes > 0){
				cubes = cubes-1;
				
				plugin.getConfig().set(p.getUniqueId().toString()+".icecubes", cubes);
				plugin.saveConfig();
				p.sendMessage("§8[ §2! §8] §7Used an §bIce Cube§7! §8[§6"+cubes+" §8remaining]");
				this.end();
			}
			
		}else{
			this.end();
		}
	}
	
	private void end(){
		plugin.cooldownList.remove(p.getName());
		
		if(p!=null && p.isOnline())
			p.sendMessage(MSG.systemCooledDown());
		
		this.cancel();
	}
}
