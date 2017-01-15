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

import tech.xfyrewolfx.thegrid.Items;
import tech.xfyrewolfx.thegrid.TheGrid;
import tech.xfyrewolfx.thegrid.apis.EnchantGlow;
import tech.xfyrewolfx.thegrid.gui.VirusesGUI;

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
				
				if(e.getItem().isSimilar(Items.listViruses())){
					VirusesGUI vgui = new VirusesGUI(plugin, p, false);
					Bukkit.getPluginManager().registerEvents(vgui, plugin);
				}
				
				if(e.getItem().isSimilar(Items.getVirtualFirewall()) || e.getItem().isSimilar(Items.getBasicHardwareFirewall()) || e.getItem().isSimilar(Items.getAdvancedHardwareFirewall()) || e.getItem().isSimilar(Items.getEncryptedFirewall()) || e.getItem().containsEnchantment(EnchantGlow.getGlow())){
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
				
				if(e.getItem().isSimilar(Items.getTraceroute())){
					// TODO run a traceroute, but prevent spam.
				}
				
				if(e.getItem().isSimilar(Items.getHP()) || e.getItem().isSimilar(Items.getLinux()) || e.getItem().isSimilar(Items.getAlienware())){
					if(e.getAction()==Action.RIGHT_CLICK_BLOCK){
						// TODO hacking an NPC
					}
				}
			}
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
