package tech.xfyrewolfx.thegrid.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import tech.xfyrewolfx.thegrid.GridSystem;
import tech.xfyrewolfx.thegrid.TheGrid;

public class RemSystem implements Listener{
	TheGrid plugin;
	Player p;
	public RemSystem(TheGrid c, Player pl){
		plugin=c;
		p=pl;
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e){
		if(e.getAction()==Action.RIGHT_CLICK_BLOCK){
			if(e.getPlayer().getName().equalsIgnoreCase(p.getName())){
				e.setCancelled(true);
				
				boolean found = false;
				for(GridSystem s : plugin.getSystems().getSystemObjects()){
					if(s.getLocation().getBlockX() == e.getClickedBlock().getX() && s.getLocation().getBlockY() == e.getClickedBlock().getY() && s.getLocation().getBlockZ() == e.getClickedBlock().getZ() && s.getLocation().getWorld().getName().equalsIgnoreCase(e.getClickedBlock().getWorld().getName())){
						plugin.getSystems().getSystemObjects().remove(s);
						found = true;
						p.sendMessage("System removed!");
						break;
					}
				}
				
				if(!found)
					p.sendMessage("That isn't a System!");
				
				PlayerInteractEvent.getHandlerList().unregister(this);
			}
		}
	}
}
