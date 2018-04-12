package tech.xfyrewolfx.thegrid.runnables;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import tech.xfyrewolfx.thegrid.GSystem;
import tech.xfyrewolfx.thegrid.TheGrid;

public class HackNPC extends BukkitRunnable{
	private TheGrid plugin;
	private GSystem system;
	private Player p;
	private int ticks;
	private String virus;
	private Random r;
	public HackNPC(TheGrid c, GSystem sys, Player pl, String vir){
		plugin=c;
		system=sys;
		p=pl;
		ticks=10;
		virus=vir;
		r = new Random();
	}
	
	public void run(){
		if(!p.isOnline() || p==null){
			this.cancel();
			return;
		}
		
		if(ticks>0){
			ticks -= 1;
			if(p.getLocation().distance(system.getLocation())>15){
				p.sendMessage(plugin.getMessages().outOfRange());
				plugin.getGPlayer(p).setIsHacking(false);
				p.sendMessage("§a~$: disconnected from "+system.getName());
				this.cancel();
				return;
			}
			
			if(p.getLevel()<1){
				p.sendMessage(plugin.getMessages().batteryDepleted());
				plugin.getGPlayer(p).setIsHacking(false);
				p.sendMessage("§a~$: disconnected from "+system.getName());
				this.cancel();
				return;
			}
			
			// Create a Title progress bar
			StringBuilder sb = new StringBuilder();
			String bar="";
			sb.append("§cUPLOAD "+virus+" §f[");
			
			for(int i=0; i<(10-ticks); i++){
				sb.append("§a|");
			}
			for(int i=0; i<ticks; i++){
				sb.append("§f|");
			}
			
			sb.append("§f]");
			bar=sb.toString();
			
			p.sendTitle("", bar, 0, 0, 25);
		}else{
			new Cooldown(plugin, p).runTaskTimer(plugin, 20, 20);
			awardPlayer();
			plugin.getGPlayer(p).setIsHacking(false);
			this.cancel();
		}
	}
	
	private void awardPlayer(){
		if(virus.equals("SHUTDOWN.vbs")){
			int exp = generateEXP(system.getLevel(), 1);
			plugin.getGPlayer(p).addExp(exp);
			p.sendMessage(plugin.getMessages().gotEXP(exp));
		}
		
		if(virus.equals("SQL Slammer")){
			int exp = generateEXP(system.getLevel(), 1);
			plugin.getGPlayer(p).addExp(exp);
			p.sendMessage(plugin.getMessages().gotEXP(exp));
		}
		
		if(virus.equals("Cryptolocker")){
			int btc = r.nextInt(3)+1;
			p.sendMessage(plugin.getMessages().gotBTC(btc));
		}
		
		if(virus.equals("DDoS Attack")){
			int exp = generateEXP(system.getLevel(), 2);
			plugin.getGPlayer(p).addExp(exp);
			p.sendMessage(plugin.getMessages().gotEXP(exp));
		}
		
		if(virus.equals("Adware")){
			int btc = r.nextInt(2)+1;
			p.sendMessage(plugin.getMessages().gotBTC(btc));
		}
		
		if(virus.equals("Killdisc")){
			int exp = generateEXP(system.getLevel(), 2);
			plugin.getGPlayer(p).addExp(exp);
			p.sendMessage(plugin.getMessages().gotEXP(exp));
		}
		
		p.sendMessage("§a~$: disconnected from "+system.getName());
	}
	
	private int generateEXP(int f, int m){
		return (f * m)+r.nextInt(30);
	}
}
