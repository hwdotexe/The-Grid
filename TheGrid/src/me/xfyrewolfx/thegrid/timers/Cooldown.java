package me.xfyrewolfx.thegrid.timers;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.xfyrewolfx.thegrid.Main;
import me.xfyrewolfx.thegrid.statics.MSG;

public class Cooldown extends BukkitRunnable{
	
	double secs;
	double osecs;
	Player p;
	Main plugin;
	BossBar bb;
	Cooldown(double seconds, Player pl, Main c){
		secs=seconds;
		osecs=secs;
		p=pl;
		plugin=c;
		
		bb = plugin.pdata.get(p).getCooldownBar();
		bb.setProgress(1.0);
		bb.addPlayer(p);
		
		p.sendMessage("§8[ §4Cooldown §8] §7Cooling down in §a"+secs+" §7seconds.");
	}
	
	public void run(){
		if(secs > 0){
			secs=secs-1;
			
			if(!bb.getPlayers().contains(p)){
				bb.addPlayer(p);
			}
			
			int cubes=0;
			if(plugin.getConfig().contains(p.getUniqueId().toString()+".icecubes"))
				cubes = plugin.getConfig().getInt(p.getUniqueId().toString()+".icecubes");
			
			if(cubes > 0){
				cubes = cubes-1;
				
				plugin.getConfig().set(p.getUniqueId().toString()+".icecubes", cubes);
				plugin.saveConfig();
				p.sendMessage("§8[ §2! §8] §7Used an §bIce Cube§7! §8[§6"+cubes+" §8remaining]");
				this.end();
			}else{
				bb.setProgress(secs/osecs);
			}
			
		}else{
			this.end();
		}
	}
	
	private void end(){
		plugin.cooldownList.remove(p.getName());
		bb.removePlayer(p);
		
		if(p!=null && p.isOnline())
			p.sendMessage(MSG.systemCooledDown());
		
		this.cancel();
	}
}
