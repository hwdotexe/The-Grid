package tech.xfyrewolfx.thegrid.gui;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import tech.xfyrewolfx.thegrid.Items;
import tech.xfyrewolfx.thegrid.TheGrid;

public class VirusesGUI implements Listener{
	private TheGrid plugin;
	private Inventory GUI;
	private Player p;
	private boolean isActive;
	private String clickedVirus;
	public VirusesGUI(TheGrid c, Player pl, boolean active){
		plugin=c;
		p=pl;
		isActive=active;
		clickedVirus = "";
		
		if(!isActive){
			GUI = Bukkit.createInventory(null, 9, "Your Viruses");
		}else{
			GUI = Bukkit.createInventory(null, 9, "Choose a Virus");
		}
		
		List<String> viruses = plugin.getGPlayer(p).getViruses();
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
		
		if(!isActive){
			GUI.setItem(8,Items.iceCube(plugin.getGPlayer(p).getIceCubes()));
		}
		
		p.openInventory(GUI);
	}
	
	// TODO while loop, check if a virus has been selected. Make a method here to return the virus clicked. Unregister listener.
	// that way we don't have to pass in any targets, and we can probably disable the isActive flag as well.
	

	@EventHandler
	public void onClick(InventoryClickEvent e){
		if(e.getInventory().equals(GUI)){
			e.setCancelled(true);
			
			if(isActive){
				if(e.getCurrentItem() != null){
					if(e.getCurrentItem().hasItemMeta()){
						e.getWhoClicked().closeInventory();
						clickedVirus = e.getCurrentItem().getItemMeta().getDisplayName().substring(2);
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
	
	public String getClickedVirus(){
		return clickedVirus;
	}
}
