package me.xfyrewolfx.thegrid;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class AddOutlet implements Listener{
	Main plugin;
	Player p;
	AddOutlet(Main c, Player pl){
		plugin=c;
		p=pl;
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e){
		if(e.getAction()==Action.RIGHT_CLICK_BLOCK){
			if(e.getClickedBlock().getType()==Material.TRIPWIRE_HOOK){
				if(e.getPlayer().getName().equalsIgnoreCase(p.getName())){
					Location b = e.getClickedBlock().getLocation();
					
					List<String> out;
					if(plugin.getoutlets().contains("outlets")){
						out = plugin.getoutlets().getStringList("outlets");
					}else{
						out = new ArrayList<String>();
					}
					
					String name = Integer.toString(out.size()+1);
					out.add(name);
					
					plugin.getoutlets().set(name+".x", b.getBlockX());
					plugin.getoutlets().set(name+".y", b.getBlockY());
					plugin.getoutlets().set(name+".z", b.getBlockZ());
					plugin.getoutlets().set("outlets", out);
					plugin.saveoutlets();
					
					plugin.out.add(b);
					
					p.sendMessage("Outlet added!");
					
					PlayerInteractEvent.getHandlerList().unregister(this);
				}
			}
		}
	}
}
