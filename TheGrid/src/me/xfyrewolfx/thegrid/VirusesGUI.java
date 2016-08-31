package me.xfyrewolfx.thegrid;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;

import me.xfyrewolfx.thegrid.statics.Items;
import me.xfyrewolfx.thegrid.timers.HackTimer;

public class VirusesGUI implements Listener{
	Player p;
	Inventory GUI;
	Main plugin;
	boolean isFunctional; //True if we are hacking something. False if player is checking viruses owned.
	Player t;
	Location tl;
	int firewallLevel;
	VirusesGUI(Player pl, Main c, boolean isHacking, Player target, Location targetLocation, int firewall){
		p=pl;
		plugin=c;
		isFunctional = isHacking;
		t=target;
		tl=targetLocation;
		firewallLevel = firewall;
		
		if(!isFunctional){
			GUI = Bukkit.createInventory(null, 9, "Your Viruses");
		}else{
			GUI = Bukkit.createInventory(null, 9, "Choose a Virus");
		}
		
		List<String> viruses = plugin.getPlayerViruses(p.getName());
		for(String v : viruses){
			
			if(v.equalsIgnoreCase("shutdown"))
				GUI.addItem(Items.shutdownVirus());
			if(v.equalsIgnoreCase("sql"))
				GUI.addItem(Items.sqlVirus());
			if(v.equalsIgnoreCase("cryptolocker"))
				GUI.addItem(Items.cryptolockerVirus());
			if(v.equalsIgnoreCase("ddos"))
				GUI.addItem(Items.ddosVirus());
			if(v.equalsIgnoreCase("adware"))
				GUI.addItem(Items.adwareVirus());
			if(v.equalsIgnoreCase("killdisc"))
				GUI.addItem(Items.killdiscVirus());
		}
		
		if(!isFunctional){
			GUI.setItem(8,Items.iceCube(plugin.pdata.get(p).getplayer().getInt(p.getUniqueId().toString()+".icecubes")));
		}
		
		p.openInventory(GUI);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e){
		if(e.getInventory().equals(GUI)){
			e.setCancelled(true);
			
			if(isFunctional){
				if(e.getCurrentItem() != null){
					if(e.getCurrentItem().hasItemMeta()){
						e.getWhoClicked().closeInventory();
						new HackTimer((Player)e.getWhoClicked(), t, tl, e.getCurrentItem(), plugin, firewallLevel).runTaskTimer(plugin, 10, 20);
						p.setMetadata("hacking", new FixedMetadataValue(plugin, true));
					}
				}else{
					((Player)e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_BASS, 6, 1);
				}
			}
		}
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e){
		if(e.getInventory()==GUI){
			InventoryClickEvent.getHandlerList().unregister(this);
			InventoryCloseEvent.getHandlerList().unregister(this);
		}
	}
}
