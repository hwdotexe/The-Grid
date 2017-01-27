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

import tech.xfyrewolfx.thegrid.GSystem;
import tech.xfyrewolfx.thegrid.Items;
import tech.xfyrewolfx.thegrid.TheGrid;
import tech.xfyrewolfx.thegrid.runnables.HackNPC;
import tech.xfyrewolfx.thegrid.runnables.HackPlayer;

public class VirusesGUI implements Listener{
	private TheGrid plugin;
	private Inventory GUI;
	private Player p;
	private boolean isActive;
	private String clickedVirus;
	private Object target;
	public VirusesGUI(TheGrid c, Player pl, Object t, boolean active){
		plugin=c;
		p=pl;
		isActive=active;
		target=t;
		clickedVirus = "";
		
		if(!isActive){
			GUI = Bukkit.createInventory(null, 18, "Your Viruses");
		}else{
			GUI = Bukkit.createInventory(null, 18, "Choose a Virus");
		}
		
		List<String> v = plugin.getGPlayer(p).getViruses();
		if(v.contains("shutdown"))
			GUI.addItem(Items.shutdownVirus());
		if(v.contains("sql"))
			GUI.addItem(Items.sqlVirus());
		if(v.contains("cryptolocker"))
			GUI.addItem(Items.cryptolockerVirus());
		if(v.contains("ddos"))
			GUI.addItem(Items.ddosVirus());
		if(v.contains("adware"))
			GUI.addItem(Items.adwareVirus());
		if(v.contains("killdisc"))
			GUI.addItem(Items.killdiscVirus());
		
		if(!isActive){
			GUI.setItem(8,Items.iceCube(plugin.getGPlayer(p).getIceCubes()));
			GUI.setItem(17,Items.openSHOP());
		}
		
		p.openInventory(GUI);
	}

	@EventHandler
	public void onClick(InventoryClickEvent e){
		if(e.getInventory().equals(GUI)){
			e.setCancelled(true);
			
			if(isActive){
				if(e.getCurrentItem() != null){
					if(e.getCurrentItem().hasItemMeta()){
						clickedVirus = e.getCurrentItem().getItemMeta().getDisplayName().substring(2);
						e.getWhoClicked().closeInventory();
					}
				}else{
					((Player)e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_BASS, 6, 1);
				}
			}else{
				if(e.getCurrentItem() != null){
					if(e.getCurrentItem().isSimilar(Items.openSHOP())){
						Bukkit.getPluginManager().registerEvents(new ShopGUI(plugin, p), plugin);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e){
		if(e.getInventory().getName()==GUI.getName()){
			
			if(clickedVirus.length()==0)
				clickedVirus = "closed";
			
			InventoryClickEvent.getHandlerList().unregister(this);
			InventoryCloseEvent.getHandlerList().unregister(this);
			
			if(target != null){
				if(target instanceof GSystem){
					if(clickedVirus != "closed"){
						if(((GSystem)target).getLevel() <= plugin.getGPlayer(p).getLevel()){
							new HackNPC(plugin, (GSystem)target, p, clickedVirus).runTaskTimer(plugin, 20, 20);
						}else{
							p.sendMessage("§a~$: disconnected from "+((GSystem)target).getName());
							p.sendMessage(plugin.getMessages().getFirewallTooStrong());
							plugin.getGPlayer(p).setIsHacking(false);
						}
					}else{
						p.sendMessage("§a~$: disconnected from "+((GSystem)target).getName());
						plugin.getGPlayer(p).setIsHacking(false);
					}
				}else{
					if(target instanceof Player){
						if(clickedVirus != "closed"){
							
							int levels = plugin.getGPlayer((Player)target).getLevel();
							if(plugin.getGPlayer((Player)target).getFirewallActive()){
								levels += 5; // firewall gives +5
							}
							
							if(levels <= plugin.getGPlayer(p).getLevel()){
								new HackPlayer(plugin, (Player)target, p, clickedVirus).runTaskTimer(plugin, 20, 20);
							}else{
								p.sendMessage(plugin.getMessages().getFirewallTooStrong());
								p.sendMessage("§a~$: disconnected from "+((Player)target).getName());
								plugin.getGPlayer(p).setIsHacking(false);
							}
						}else{
							p.sendMessage("§a~$: disconnected from "+((Player)target).getName());
							plugin.getGPlayer(p).setIsHacking(false);
						}
					}
				}
			}
		}
	}
}
