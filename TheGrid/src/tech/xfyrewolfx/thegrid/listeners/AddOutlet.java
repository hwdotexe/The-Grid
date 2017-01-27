package tech.xfyrewolfx.thegrid.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import tech.xfyrewolfx.thegrid.Outlet;
import tech.xfyrewolfx.thegrid.TheGrid;

public class AddOutlet implements Listener{
	TheGrid plugin;
	Player p;
	public AddOutlet(TheGrid c, Player pl){
		plugin=c;
		p=pl;
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e){
		if(e.getAction()==Action.RIGHT_CLICK_BLOCK){
			if(e.getClickedBlock().getType()==Material.TRIPWIRE_HOOK){
				if(e.getPlayer().getName().equalsIgnoreCase(p.getName())){
					e.setCancelled(true);
					
					for(Outlet o : plugin.getOutlets().getOutletObjects()){
						if(o.getLocation().getBlockX() == e.getClickedBlock().getX() && o.getLocation().getBlockY() == e.getClickedBlock().getY() && o.getLocation().getBlockZ() == e.getClickedBlock().getZ() && o.getLocation().getWorld().getName().equalsIgnoreCase(e.getClickedBlock().getWorld().getName())){
							p.sendMessage("Error: There is already an Outlet in that location!");
							PlayerInteractEvent.getHandlerList().unregister(this);
							return;
						}
					}
					
					plugin.getOutlets().getOutletObjects().add(new Outlet(e.getClickedBlock().getLocation()));
					
					p.sendMessage("Outlet added!");
					
					PlayerInteractEvent.getHandlerList().unregister(this);
				}
			}
		}
	}
}
