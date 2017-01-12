package tech.xfyrewolfx.thegrid;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class AddOutlet implements Listener{
	TheGrid plugin;
	Player p;
	AddOutlet(TheGrid c, Player pl){
		plugin=c;
		p=pl;
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e){
		if(e.getAction()==Action.RIGHT_CLICK_BLOCK){
			if(e.getClickedBlock().getType()==Material.TRIPWIRE_HOOK){
				if(e.getPlayer().getName().equalsIgnoreCase(p.getName())){
					plugin.getOutlets().getOutletObjects().add(new Outlet(e.getClickedBlock().getLocation()));
					
					p.sendMessage("Outlet added!");
					
					PlayerInteractEvent.getHandlerList().unregister(this);
				}
			}
		}
	}
}
