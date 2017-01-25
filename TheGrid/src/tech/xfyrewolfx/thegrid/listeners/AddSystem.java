package tech.xfyrewolfx.thegrid.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import tech.xfyrewolfx.thegrid.GSystem;
import tech.xfyrewolfx.thegrid.TheGrid;

public class AddSystem implements Listener{
	TheGrid plugin;
	Player p;
	String name;
	int level;
	public AddSystem(TheGrid c, Player pl, String n, int lvl){
		plugin=c;
		p=pl;
		name=n;
		level=lvl;
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e){
		if(e.getAction()==Action.RIGHT_CLICK_BLOCK){
			if(e.getPlayer().getName().equalsIgnoreCase(p.getName())){
				e.setCancelled(true);
				
				for(GSystem s : plugin.getSystems().getSystemObjects()){
					if(s.getName().equalsIgnoreCase(name)){
						p.sendMessage("Error: A System with that name already exists!");
						PlayerInteractEvent.getHandlerList().unregister(this);
						return;
					}
					
					if(s.getLocation().getBlockX() == e.getClickedBlock().getX() && s.getLocation().getBlockY() == e.getClickedBlock().getY() && s.getLocation().getBlockZ() == e.getClickedBlock().getZ() && s.getLocation().getWorld().getName().equalsIgnoreCase(e.getClickedBlock().getWorld().getName())){
						p.sendMessage("Error: There is already a System in that location!");
						PlayerInteractEvent.getHandlerList().unregister(this);
						return;
					}
				}
				
				plugin.getSystems().getSystemObjects().add(new GSystem(e.getClickedBlock().getLocation(), name, level));
				
				p.sendMessage("System added!");
				
				PlayerInteractEvent.getHandlerList().unregister(this);
			}
		}
	}
}
