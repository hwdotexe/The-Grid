package tech.xfyrewolfx.thegrid.gui;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import tech.xfyrewolfx.thegrid.Items;
import tech.xfyrewolfx.thegrid.TheGrid;
import tech.xfyrewolfx.thegrid.apis.EnchantGlow;
import tech.xfyrewolfx.thegrid.files.GPlayer;

public class ShopGUI implements Listener{
	private TheGrid plugin;
	private Player p;
	private Inventory GUI;
	private GPlayer gp;
	public ShopGUI(TheGrid c, Player pl){
		plugin=c;
		p=pl;
		gp=plugin.getGPlayer(p);
		GUI = Bukkit.createInventory(null, 9, "§4HackShop - §8"+gp.getBTC()+" BTC");
		
		// Add items
		if(gp.getViruses().contains("adware")){
			GUI.setItem(0, EnchantGlow.addGlow(Items.adwareVirusSHOP()));
		}else{
			GUI.setItem(0, Items.adwareVirusSHOP());
		}
		
		if(gp.getViruses().contains("killdisc")){
			GUI.setItem(1, EnchantGlow.addGlow(Items.killdiscVirusSHOP()));
		}else{
			GUI.setItem(1, Items.killdiscVirusSHOP());
		}
		
		GUI.setItem(8, Items.iceSHOP());
		
		
		p.openInventory(GUI);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e){
		if(e.getClickedInventory() != null){
			if(e.getClickedInventory().equals(GUI)){
				e.setCancelled(true);
				
				if(e.getCurrentItem() != null){
					p.closeInventory();
					
					//Adware
					if(e.getCurrentItem().isSimilar(Items.adwareVirusSHOP())){
						if(!gp.getViruses().contains("adware")){
							if(gp.getBTC() >= 15){
								p.sendMessage(plugin.getMessages().purchaseSuccessful());
								gp.setBTC(gp.getBTC()-15);
								gp.getViruses().add("adware");
							}else{
								p.sendMessage(plugin.getMessages().purchaseFailedNoFunds());
							}
						}else{
							p.sendMessage(plugin.getMessages().purchaseFailedOwned());
						}
					}
					
					//Killdisc
					if(e.getCurrentItem().isSimilar(Items.killdiscVirusSHOP())){
						if(!gp.getViruses().contains("killdisc")){
							if(gp.getBTC() >= 10){
								p.sendMessage(plugin.getMessages().purchaseSuccessful());
								gp.setBTC(gp.getBTC()-10);
								gp.getViruses().add("killdisc");
							}else{
								p.sendMessage(plugin.getMessages().purchaseFailedNoFunds());
							}
						}else{
							p.sendMessage(plugin.getMessages().purchaseFailedOwned());
						}
					}
					
					//Ice
					if(e.getCurrentItem().isSimilar(Items.iceSHOP())){
						if(gp.getBTC() >= 5){
							p.sendMessage(plugin.getMessages().purchaseSuccessful());
							gp.setBTC(gp.getBTC()-5);
							gp.setIceCubes(gp.getIceCubes()+1);
						}else{
							p.sendMessage(plugin.getMessages().purchaseFailedNoFunds());
						}
					}
					
					plugin.updateScoreboard(p);
				}else{
					p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 6, 1);
				}
			}
		}
	}
}
