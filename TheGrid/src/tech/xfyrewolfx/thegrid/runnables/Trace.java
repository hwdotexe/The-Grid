package tech.xfyrewolfx.thegrid.runnables;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import tech.xfyrewolfx.thegrid.GSystem;
import tech.xfyrewolfx.thegrid.TheGrid;
import tech.xfyrewolfx.thegrid.apis.TitleAPI;

public class Trace extends BukkitRunnable{
	private Player p;
	private TheGrid plugin;
	private int ticks;
	public Trace(Player pl, TheGrid c){
		p=pl;
		plugin=c;
		ticks=16;
		
		if(plugin.getGPlayer(p).getIsTracing()){
			this.cancel();
		}else{
			plugin.getGPlayer(p).setIsTracing(true);
		}
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
				title="[\\]";
			}
			if(ticks == 2 || ticks == 6 || ticks == 10 || ticks == 14){
				title="[|]";
			}
			if(ticks == 3 || ticks == 7 || ticks == 11 || ticks == 15){
				title="[/]";
			}
			if(ticks == 4 || ticks == 8 || ticks == 12 || ticks == 16){
				title="[-]";
			}
			
			TitleAPI.sendTitle(p, 0, 0, 15, "§ki§e§l Traceroute §f§ki", title);
		}else{
			List<GSystem> sys = new ArrayList<GSystem>();
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
			
			if(sys.size()>0){
				for(GSystem s : sys){
					p.sendMessage("§8[§eTraceroute§8] §7Found system at x§e"+s.getLocation().getBlockX()+" §7y§e"+s.getLocation().getBlockY()+" §7z§e"+s.getLocation().getBlockZ()+" §8[Level "+s.getLevel()+"]");
				}
			}else{
				p.sendMessage("§8[§eTraceroute§8] §7No systems were found in this area.");
			}
			
			plugin.getGPlayer(p).setIsTracing(false);
			this.cancel();
		}
	}
}