package tech.xfyrewolfx.thegrid.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import tech.xfyrewolfx.thegrid.GSystem;
import tech.xfyrewolfx.thegrid.Outlet;
import tech.xfyrewolfx.thegrid.TheGrid;
import tech.xfyrewolfx.thegrid.apis.EnchantGlow;
import tech.xfyrewolfx.thegrid.gui.VirusesGUI;
import tech.xfyrewolfx.thegrid.runnables.Charge;
import tech.xfyrewolfx.thegrid.runnables.Trace;

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
			
			if(e.getAction()==Action.RIGHT_CLICK_BLOCK){
				Block b = e.getClickedBlock();
				if(b.getType()==Material.TRAP_DOOR || b.getType()==Material.ACACIA_DOOR || b.getType()==Material.BIRCH_DOOR || b.getType()==Material.DARK_OAK_DOOR || b.getType()==Material.JUNGLE_DOOR || b.getType()==Material.SPRUCE_DOOR || b.getType()==Material.WOOD_DOOR || b.getType()==Material.WOODEN_DOOR){
					e.setCancelled(false);
				}
			}
			
			if(e.getItem() != null){
				Player p = e.getPlayer();
				
				if(e.getItem().isSimilar(plugin.getItems().my_items())){
					VirusesGUI vgui = new VirusesGUI(plugin, p, null, false);
					Bukkit.getPluginManager().registerEvents(vgui, plugin);
				}
				
				if(e.getItem().isSimilar(plugin.getItems().firewall_1()) || e.getItem().isSimilar(plugin.getItems().firewall_2()) || e.getItem().isSimilar(plugin.getItems().firewall_3()) || e.getItem().isSimilar(plugin.getItems().firewall_4()) || e.getItem().containsEnchantment(EnchantGlow.getGlow())){
					if(plugin.getGPlayer(p).getFirewallActive()){
						ItemStack fw = p.getInventory().getItem(8);
						fw.removeEnchantment(EnchantGlow.getGlow());
						p.getInventory().setItem(8, fw);
						plugin.getGPlayer(p).setFirewallActive(false);
					}else{
						if(p.getLevel() > 0){
							ItemStack fw = p.getInventory().getItem(8);
							fw = EnchantGlow.addGlow(fw);
							p.getInventory().setItem(8, fw);
							plugin.getGPlayer(p).setFirewallActive(true);
						}else{
							p.sendMessage(plugin.getMessages().batteryDepleted());
						}
					}
				}
				
				if(e.getItem().isSimilar(plugin.getItems().traceroute())){
					if(p.getLevel()>0){
						if(!plugin.getGPlayer(p).getIsTracing())
							new Trace(e.getPlayer(), plugin).runTaskTimer(plugin, 10, 10);
					}else{
						p.sendMessage(plugin.getMessages().batteryDepleted());
					}
				}
				
				if(e.getItem().isSimilar(plugin.getItems().laptop_1()) || e.getItem().isSimilar(plugin.getItems().laptop_2()) || e.getItem().isSimilar(plugin.getItems().laptop_3()) || e.getItem().isSimilar(plugin.getItems().laptop_4())){
					if(e.getAction()==Action.RIGHT_CLICK_BLOCK){
						GSystem s = plugin.isBlockSystem(e.getClickedBlock().getLocation());
						Outlet o = plugin.isBlockOutlet(e.getClickedBlock().getLocation());
						if(s != null){
							if(!plugin.getGPlayer(e.getPlayer()).getIsHacking()){
								if(!plugin.getGPlayer(e.getPlayer()).getIsCoolingDown()){
									plugin.hackCPU(p, s);
								}else{
									e.getPlayer().sendMessage(plugin.getMessages().notCooledDown());
								}
							}
						}else{
							if(o != null){
								if(!plugin.getGPlayer(p).getIsCharging())
									new Charge(e.getPlayer(), plugin, o).runTaskTimer(plugin, 20, 100);
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void playerClickPlayer(PlayerInteractEntityEvent e){
		if(e.getRightClicked() instanceof Player){
			e.setCancelled(true);
			Player t = (Player)e.getRightClicked();
			if(!plugin.getGPlayer(e.getPlayer()).getIsHacking()){
				if(t.getLevel() > 0){
					if(!plugin.getGPlayer(e.getPlayer()).getIsCoolingDown()){
						plugin.hackPlayer(e.getPlayer(), t);
					}else{
						e.getPlayer().sendMessage(plugin.getMessages().notCooledDown());
					}
				}else{
					e.getPlayer().sendMessage(plugin.getMessages().otherBatteryDepleted(t.getName()));
				}
			}
		}
	}
}
