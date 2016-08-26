package me.xfyrewolfx.thegrid.timers;

import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.xfyrewolfx.thegrid.Main;
import me.xfyrewolfx.thegrid.statics.MSG;

public class ChargingTimer extends BukkitRunnable{
	
	Player p;
	int maxBattery;
	Location l;
	Main plugin;
	BossBar bb;
	double max;
	public ChargingTimer(Player pl, int maxBat, Location loc, Main c){
		p=pl;
		maxBattery=maxBat;
		l=loc;
		plugin=c;
		
		max = plugin.getPlayerBattery(p.getName());
		bb = plugin.pdata.get(p).getBatteryBar();
		bb.setColor(BarColor.GREEN);
		bb.setProgress(p.getLevel()/max);
		bb.addPlayer(p);
	}
	
	public void run(){
		if(p != null){
			if(p.getLevel() < maxBattery){
				if(p.getLocation().distance(l)<=10){
					p.setLevel(p.getLevel()+1);
				}else{
					p.sendMessage(MSG.outOfRange());
					p.removeMetadata("charging", plugin);
					bb.setColor(BarColor.YELLOW);
					this.cancel();
				}
			}else{
				p.sendMessage(MSG.batteryFull());
				p.removeMetadata("charging", plugin);
				bb.setColor(BarColor.YELLOW);
				this.cancel();
			}
			
			double x = p.getLevel()/max;
			bb.setProgress(x);
		}else{
			this.cancel();
		}
	}
}
