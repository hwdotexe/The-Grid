package tech.xfyrewolfx.thegrid.runnables;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import tech.xfyrewolfx.thegrid.GSystem;
import tech.xfyrewolfx.thegrid.TheGrid;
import tech.xfyrewolfx.thegrid.apis.TitleAPI;

public class HackNPC extends BukkitRunnable{
	private TheGrid plugin;
	private GSystem system;
	private Player p;
	private int ticks;
	private String virus;
	public HackNPC(TheGrid c, GSystem sys, Player pl, String vir){
		plugin=c;
		system=sys;
		p=pl;
		ticks=10;
		virus=vir;
	}
	
	public void run(){
		if(!p.isOnline() || p==null){
			this.cancel();
			return;
		}
		
		if(ticks>0){
			if(p.getLocation().distance(system.getLocation())>15){
				p.sendMessage(plugin.getMessages().outOfRange());
				this.cancel();
				return;
			}
			
			if(p.getLevel()<1){
				p.sendMessage(plugin.getMessages().batteryDepleted());
				this.cancel();
				return;
			}
			
			// Create a Title progress bar
			StringBuilder sb = new StringBuilder();
			String bar="";
			sb.append(virus+" §f[");
			
			for(int i=0; i<(10-ticks); i++){
				sb.append("§a|");
			}
			for(int i=0; i<ticks; i++){
				sb.append("§f|");
			}
			
			sb.append("§f]");
			bar=sb.toString();
			TitleAPI.sendTitle(p, 0, 0, 25, "", bar);
		}else{
			new Cooldown(plugin, p).runTaskTimer(plugin, 20, 20);
			awardPlayer();
			this.cancel();
		}
	}
	
	public void awardPlayer(){
		// TODO award players
	}
}
