package tech.xfyrewolfx.thegrid.runnables;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import tech.xfyrewolfx.thegrid.GridOutlet;
import tech.xfyrewolfx.thegrid.TheGrid;

public class Charge extends BukkitRunnable{
	
	private Player p;
	private TheGrid plugin;
	private GridOutlet outlet;
	
	private BossBar bb;
	private double maxBattery;
	public Charge(Player pl, TheGrid c, GridOutlet out){
		plugin=c;
		p=pl;
		outlet=out;
		maxBattery = plugin.getGridPlayer(p).getBattery();
		
		if(plugin.getGridPlayer(p).getIsCharging()){
			this.cancel();
		}else{
			plugin.getGridPlayer(p).setIsCharging(true);
		}
		
		bb = plugin.getGridPlayer(p).getBatteryBar();
		bb.setColor(BarColor.GREEN);
	}
	
	public void run(){
		if(!p.isOnline() || p==null){
			this.cancel();
			return;
		}
		
		if(p.getLevel() < maxBattery && p.getLocation().distance(outlet.getLocation())<=10){
			p.setLevel(p.getLevel()+1);
		}else{
			p.sendMessage(plugin.getMessages().chargingFinished());
			bb.setColor(BarColor.YELLOW);
			plugin.getGridPlayer(p).setIsCharging(false);
			this.cancel();
		}
		
		double x = p.getLevel()/maxBattery;
		bb.setProgress(x);
	}
}
