package tech.xfyrewolfx.thegrid.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import tech.xfyrewolfx.thegrid.TheGrid;

public class ClickListener implements Listener{
	private TheGrid plugin;
	public ClickListener(TheGrid c){
		plugin=c;
	}
	
	@EventHandler
	public void playerUseItem(PlayerInteractEvent e){
		if(e.getAction()==Action.RIGHT_CLICK_AIR || e.getAction()==Action.RIGHT_CLICK_BLOCK){
			if(!e.getPlayer().isOp())
				e.setCancelled(true);
			
			// TODO using items (firewall, traceroute, etc. P.S. We could also be hacking an NPC
		}
	}
	
	@EventHandler
	public void playerClickPlayer(PlayerInteractEntityEvent e){
		if(e.getRightClicked() instanceof Player){
			e.setCancelled(true);
			
			// TODO we're hacking a player, so let's screw up their system. Really bad this time.
		}
	}
}
