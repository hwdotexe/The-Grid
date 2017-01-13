package tech.xfyrewolfx.thegrid.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import tech.xfyrewolfx.thegrid.System;
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
				plugin.getSystems().getSystemObjects().add(new System(e.getClickedBlock().getLocation(), name, level));
				
				p.sendMessage("System added!");
				
				PlayerInteractEvent.getHandlerList().unregister(this);
			}
		}
	}
}
