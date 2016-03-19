package me.xfyrewolfx.thegrid;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class AddSystem implements Listener{
	Main plugin;
	Player p;
	String name;
	int level;
	AddSystem(Main c, Player pl, String n, int l){
		plugin=c;
		p=pl;
		name=n;
		level=l;
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e){
		if(e.getAction()==Action.RIGHT_CLICK_BLOCK){
			if(e.getPlayer().getName().equalsIgnoreCase(p.getName())){
				Location b = e.getClickedBlock().getLocation();
				
				List<String> sys;
				if(plugin.getsystems().contains("systems")){
					sys = plugin.getsystems().getStringList("systems");
				}else{
					sys = new ArrayList<String>();
				}
				
				if(!sys.contains(name)){
					sys.add(name);
					
					plugin.getsystems().set(name+".x", b.getBlockX());
					plugin.getsystems().set(name+".y", b.getBlockY());
					plugin.getsystems().set(name+".z", b.getBlockZ());
					plugin.getsystems().set(name+".level", level);
					plugin.getsystems().set("systems", sys);
					plugin.savesystems();
					
					plugin.sys.put(b, level);
					
					p.sendMessage("System added!");
					PlayerInteractEvent.getHandlerList().unregister(this);
				}else{
					p.sendMessage("That system already exists!");
					PlayerInteractEvent.getHandlerList().unregister(this);
				}
			}
		}
	}
}
