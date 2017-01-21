package tech.xfyrewolfx.thegrid.runnables;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import tech.xfyrewolfx.thegrid.Outlet;
import tech.xfyrewolfx.thegrid.TheGrid;

public class Charge extends BukkitRunnable{
	
	private Player p;
	private TheGrid plugin;
	private Outlet outlet;
	
	private BossBar bb;
	private int maxBattery;
	public Charge(Player pl, TheGrid c, Outlet out){
		plugin=c;
		p=pl;
		outlet=out;
		maxBattery = plugin.getGPlayer(p).getBattery();
		
		if(plugin.getGPlayer(p).getIsCharging()){
			this.cancel();
		}else{
			plugin.getGPlayer(p).setIsCharging(true);
		}
		
		bb = plugin.getGPlayer(p).getBatteryBar();
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
			p.sendMessage(plugin.getMessages().batteryFull());
			bb.setColor(BarColor.YELLOW);
			plugin.getGPlayer(p).setIsCharging(false);
			this.cancel();
		}
		
		double x = p.getLevel()/maxBattery;
		bb.setProgress(x);
	}
}
