package tech.xfyrewolfx.thegrid.runnables;

import java.util.Random;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import tech.xfyrewolfx.thegrid.TheGrid;
import tech.xfyrewolfx.thegrid.files.GPlayer;

public class Cooldown extends BukkitRunnable{
	private TheGrid plugin;
	private Player p;
	private BossBar cb;
	private Random r;
	private double ticks;
	private double oticks;
	private GPlayer gp;
	
	public Cooldown(TheGrid c, Player pl){
		plugin=c;
		p=pl;
		r = new Random();
		gp = plugin.getGPlayer(p);
		
		ticks = (gp.getLevel())+r.nextInt(30);
		oticks = ticks;
		cb = gp.getCooldownBar();
		double x = p.getLevel()/oticks;
		cb.setProgress(x);
		cb.addPlayer(p);
	}
	
	public void run(){
		if(ticks > 0){
			ticks -= 1;
			cb.setProgress(ticks/oticks);
			
			int cubes = gp.getIceCubes();
			if(cubes>0){
				gp.setIceCubes(cubes-1);
				p.sendMessage(plugin.getMessages().usedIceCube());
				endTask();
			}
		}else{
			endTask();
		}
	}
	
	private void endTask(){
		if(plugin.getGPlayers().containsValue(gp)){
			p.sendMessage(plugin.getMessages().cooledDown());
			plugin.getGPlayer(p).setIsCoolingDown(false);
		}else{
			gp.setIsCoolingDown(false);
			gp.saveValues();
		}
		
		this.cancel();
	}
}
