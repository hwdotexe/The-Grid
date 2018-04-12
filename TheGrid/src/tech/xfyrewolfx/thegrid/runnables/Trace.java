package tech.xfyrewolfx.thegrid.runnables;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import tech.xfyrewolfx.thegrid.GSystem;
import tech.xfyrewolfx.thegrid.TheGrid;

public class Trace extends BukkitRunnable{
	private Player p;
	private TheGrid plugin;
	private int ticks;
	public Trace(Player pl, TheGrid c){
		p=pl;
		plugin=c;
		ticks=16;
		
		plugin.getGPlayer(p).setIsTracing(true);
	}
	
	public void run(){
		if(!p.isOnline() || p==null){
			this.cancel();
			return;
		}
		
		if(ticks > 0){
			ticks -= 1;
			String title = "";
			
			if(ticks == 1 || ticks == 5 || ticks == 9 || ticks == 13){
				title="[ \\\\ ]";
			}
			if(ticks == 2 || ticks == 6 || ticks == 10 || ticks == 14){
				title="[ | ]";
			}
			if(ticks == 3 || ticks == 7 || ticks == 11 || ticks == 15){
				title="[ / ]";
			}
			if(ticks == 4 || ticks == 8 || ticks == 12 || ticks == 16){
				title="[ - ]";
			}
			
			p.sendTitle("§ki§e§l Traceroute §f§ki", title, 0, 0, 15);
		}else{
			List<GSystem> sys = new ArrayList<GSystem>();
			List<Player> pla = new ArrayList<Player>();
			int lvl = plugin.getGPlayer(p).getLevel();
			for(GSystem s : plugin.getSystems().getSystemObjects()){
				
				if(lvl > 4 && lvl < 20){
					if(p.getLocation().distance(s.getLocation()) <= 15)
						sys.add(s);
				}
				
				if(lvl > 19 && lvl < 30){
					if(p.getLocation().distance(s.getLocation()) <= 30)
						sys.add(s);
				}
				
				if(lvl > 29 && lvl < 40){
					if(p.getLocation().distance(s.getLocation()) <= 50)
						sys.add(s);
				}
			}
			
			for(Player op : Bukkit.getOnlinePlayers()){
				if(!op.getName().equals(p.getName())){
					if(op.getWorld().getName().equals(p.getWorld().getName())){
						
						if(lvl > 4 && lvl < 20){
							if(p.getLocation().distance(op.getLocation()) <= 15)
								pla.add(op);
						}
						
						if(lvl > 19 && lvl < 30){
							if(p.getLocation().distance(op.getLocation()) <= 30)
								pla.add(op);
						}
						
						if(lvl > 29 && lvl < 40){
							if(p.getLocation().distance(op.getLocation()) <= 50)
								pla.add(op);
						}
					}
				}
			}
			
			if(sys.size()+pla.size()>0){
				for(GSystem s : sys){
					p.sendMessage("§8[§eTraceroute§8] §a["+s.getName()+" (lv."+s.getLevel()+")] §7 @ x§e"+s.getLocation().getBlockX()+" §7y§e"+s.getLocation().getBlockY()+" §7z§e"+s.getLocation().getBlockZ());
				}
				for(Player op : pla){
					p.sendMessage("§8[§eTraceroute§8] §a["+op.getName()+" (lv."+plugin.getGPlayer(op).getLevel()+")] §7 @ x§e"+op.getLocation().getBlockX()+" §7y§e"+op.getLocation().getBlockY()+" §7z§e"+op.getLocation().getBlockZ());
				}
			}else{
				p.sendMessage("§8[§eTraceroute§8] §7No systems were found in this area.");
			}
			
			plugin.getGPlayer(p).setIsTracing(false);
			this.cancel();
		}
	}
}
