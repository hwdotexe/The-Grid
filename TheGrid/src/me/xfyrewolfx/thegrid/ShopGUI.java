package me.xfyrewolfx.thegrid;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import me.xfyrewolfx.thegrid.statics.Items;

public class ShopGUI implements Listener{
	Player p;
	Main plugin;
	Inventory GUI;
	ShopGUI(Player pl, Main c){
		p=pl;
		plugin=c;
		
		GUI = Bukkit.createInventory(null, 9, "Shop - $"+plugin.getPlayerBitcoin(p.getName())+" BTC");
		
		if(plugin.getPlayerViruses(p.getName()).contains("adware")){
			GUI.setItem(0, EnchantGlow.addGlow(Items.adwareVirusSHOP()));
		}else{
			GUI.setItem(0, Items.adwareVirusSHOP());
		}
		if(plugin.getPlayerViruses(p.getName()).contains("killdisc")){
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
				
				if(e.getCurrentItem()!=null){
					if(e.getCurrentItem().isSimilar(Items.adwareVirusSHOP())){
						p.closeInventory();
						if(plugin.getPlayerBitcoin(p.getName())>=15){
							if(!plugin.getPlayerViruses(p.getName()).contains("adware")){
								
								int btc = plugin.getPlayerBitcoin(p.getName());
								plugin.setPlayerBitcoins(p.getName(), btc-15);
								p.sendMessage("§6-$15 BTC");
								
								List<String> v =  plugin.getPlayerViruses(p.getName());
								v.add("adware");
								plugin.setPlayerViruses(p.getName(), v);
								p.sendMessage("§8[ §2! §8] §7You purchased the Adware virus!");
							}else{
								p.sendMessage("§8[ §2! §8] §7You already own that!");
							}
						}else{
							p.sendMessage("§8[ §2! §8] §7Insufficient funds!");
						}
					}
					
					if(e.getCurrentItem().isSimilar(Items.killdiscVirusSHOP())){
						p.closeInventory();
						
						if(plugin.getPlayerBitcoin(p.getName())>=10){
							if(!plugin.getPlayerViruses(p.getName()).contains("killdisc")){
								
								int btc = plugin.getPlayerBitcoin(p.getName());
								plugin.setPlayerBitcoins(p.getName(), btc-10);
								p.sendMessage("§6-$10 BTC");
								
								List<String> v =  plugin.getPlayerViruses(p.getName());
								v.add("killdisc");
								plugin.setPlayerViruses(p.getName(), v);
								p.sendMessage("§8[ §2! §8] §7You purchased the Killdisc virus!");
							}else{
								p.sendMessage("§8[ §2! §8] §7You already own that!");
							}
						}else{
							p.sendMessage("§8[ §2! §8] §7Insufficient funds!");
						}
					}
					
					if(e.getCurrentItem().isSimilar(Items.iceSHOP())){
						p.closeInventory();
						
						if(plugin.getPlayerBitcoin(p.getName())>=5){
							int btc = plugin.getPlayerBitcoin(p.getName());
							plugin.setPlayerBitcoins(p.getName(), btc-5);
							p.sendMessage("§6-$5 BTC");
							
							int cubes=0;
							if(plugin.pdata.get(p).getplayer().contains(p.getUniqueId().toString()+".icecubes"))
								cubes = plugin.pdata.get(p).getplayer().getInt(p.getUniqueId().toString()+".icecubes");
							
							cubes = cubes+1;
							
							plugin.pdata.get(p).getplayer().set(p.getUniqueId().toString()+".icecubes", cubes);
							plugin.pdata.get(p).saveplayer();
							
							
							p.sendMessage("§8[ §2! §8] §7You purchased an Ice Cube!");
							p.sendMessage("§8[ §2! §8] §8You now own §6"+cubes+" §bIce Cubes");
						}else{
							p.sendMessage("§8[ §2! §8] §7Insufficient funds!");
						}
					}
					
					plugin.updateScoreboard(p);
				}
			}
		}
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e){
		if(e.getInventory().equals(GUI)){
			InventoryClickEvent.getHandlerList().unregister(this);
			InventoryCloseEvent.getHandlerList().unregister(this);
		}
	}
}
