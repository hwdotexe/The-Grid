package me.xfyrewolfx.thegrid;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import me.xfyrewolfx.thegrid.statics.Items;
import me.xfyrewolfx.thegrid.statics.MSG;
import me.xfyrewolfx.thegrid.timers.BatteryTick;
import me.xfyrewolfx.thegrid.timers.ChargingTimer;
import me.xfyrewolfx.thegrid.timers.Traceroute;

public class PlayerListener implements Listener{
	Main plugin;
	PlayerListener(Main c){
		plugin=c;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		e.setJoinMessage(MSG.joinMessage(e.getPlayer().getName()));
		e.getPlayer().setGameMode(GameMode.ADVENTURE);
		
		plugin.pdata.put(e.getPlayer(), new PlayerData(plugin, e.getPlayer()));
		
		int level = e.getPlayer().getLevel();
		e.getPlayer().setExp(0f);
		e.getPlayer().setLevel(level);
		
		if(!e.getPlayer().hasPlayedBefore()){
			e.getPlayer().setMetadata("tutorial", new FixedMetadataValue(plugin, true));
			new Tutorial(plugin, e.getPlayer()).runTaskTimer(plugin, 20, 150);
			plugin.createNewPlayerEntries(e.getPlayer().getName());
			
			if(plugin.tutLoc != null)
				e.getPlayer().teleport(plugin.tutLoc);
		}else{
			e.getPlayer().getInventory().setContents(plugin.getPlayerInventory(e.getPlayer().getName()));
			new BatteryTick(e.getPlayer(), plugin).runTaskTimer(plugin, 600, 600);
			
			plugin.giveNewScoreboard(e.getPlayer());
		}
		
		e.getPlayer().sendMessage("§8> §7Server version §a"+plugin.getDescription().getVersion());
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e){
		e.setQuitMessage(MSG.leaveMessage(e.getPlayer().getName()));
		plugin.pdata.remove(e.getPlayer());
	}
	@EventHandler
	public void onKick(PlayerKickEvent e){
		e.setLeaveMessage(MSG.leaveMessage(e.getPlayer().getName()));
		plugin.pdata.remove(e.getPlayer());
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		if(!e.getPlayer().isOp()){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPing(ServerListPingEvent e){
		e.setMotd(plugin.motd);
	}
	
	@EventHandler
	public void onBreakItemFrame(HangingBreakEvent e){
		//the only way to break an item frame is to destroy the block behind it
		if(e.getCause()!=RemoveCause.PHYSICS){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e){
		int lvl = plugin.getPlayerLevel(e.getPlayer().getName());
		e.getPlayer().setDisplayName("§8[ §2"+lvl+" §8] §7"+e.getPlayer().getName());
		e.setMessage("§f"+e.getMessage());
		e.setFormat(e.getPlayer().getDisplayName()+": "+e.getMessage());
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e){
		if(e.getClickedInventory() != null){
			if(e.getClickedInventory().equals(e.getWhoClicked().getInventory())){
				if(!e.getWhoClicked().isOp())
					e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			e.setCancelled(true);
		}else{
			if(e.getEntity().getType()==EntityType.ITEM_FRAME){
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e){
		if(!e.getPlayer().isOp()){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		if(!e.getPlayer().isOp()){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void handleItemClicks(PlayerInteractEvent e){
		if(e.getAction()==Action.RIGHT_CLICK_AIR || e.getAction()==Action.RIGHT_CLICK_BLOCK){
			if(!e.getPlayer().isOp())
				e.setCancelled(true);//No interactions!
			
			if(e.getItem() != null){
				//List viruses
				if(e.getItem().isSimilar(Items.listViruses())){
					Bukkit.getServer().getPluginManager().registerEvents(new VirusesGUI(e.getPlayer(), plugin, false, null, null, 0), plugin);
				}
				
				//Firewall
				if(e.getItem().isSimilar(Items.getVirtualFirewall()) || e.getItem().isSimilar(Items.getBasicHardwareFirewall()) || e.getItem().isSimilar(Items.getAdvancedHardwareFirewall()) || e.getItem().isSimilar(Items.getEncryptedFirewall()) || e.getItem().containsEnchantment(Enchantment.DURABILITY)){
					Player p = e.getPlayer();
					if(plugin.isFirewallActive(p.getName())){
						ItemStack fw = p.getInventory().getItem(8);
						fw.removeEnchantment(Enchantment.DURABILITY);
						p.getInventory().setItem(8, fw);
					}else{
						if(p.getLevel() > 0){
							ItemStack fw = p.getInventory().getItem(8);
							fw.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
							p.getInventory().setItem(8, fw);
						}else{
							p.sendMessage(MSG.batteryDepleted());
						}
					}
				}
				
				//Traceroute
				if(e.getItem().isSimilar(Items.getTraceroute())){
					Player p = e.getPlayer();
					if(!p.hasMetadata("Grid_TR")){
						p.setMetadata("Grid_TR", new FixedMetadataValue(plugin, true));
						new Traceroute(plugin, p, plugin.getPlayerLevel(p.getName())).runTaskTimer(plugin, 20, 10);
					}
				}
				
				//Hacking
				if(e.getItem().isSimilar(Items.getHP()) || e.getItem().isSimilar(Items.getLinux()) || e.getItem().isSimilar(Items.getAlienware())){
					if(e.getAction()==Action.RIGHT_CLICK_BLOCK){
						if(e.getClickedBlock() != null){
							Player p = e.getPlayer();
							if(plugin.isBlockCPU(e.getClickedBlock().getLocation())){
								if(!plugin.cooldownList.contains(p.getName())){
									if(!p.hasMetadata("hacking")){
										plugin.hackCPU(e.getClickedBlock().getLocation(), p);
									}
								}else{
									p.sendMessage(MSG.needsCooldown());
								}
							}else{
								if(plugin.isBlockOutlet(e.getClickedBlock().getLocation())){
									if(!p.hasMetadata("charging")){
										int max = plugin.getPlayerBattery(p.getName());
										if(p.getLevel()!=max){
											p.sendMessage(MSG.batteryCharging());
											p.setMetadata("charging", new FixedMetadataValue(plugin, true));
											new ChargingTimer(p,max,e.getClickedBlock().getLocation(),plugin).runTaskTimer(plugin, 80, 100);
										}else{
											p.sendMessage(MSG.batteryFull());
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onClickPlayer(PlayerInteractEntityEvent e){
		if(e.getRightClicked() instanceof Player){
			e.setCancelled(true);
			
			Player t = (Player)e.getRightClicked();
			Player p = e.getPlayer();
			if(!p.hasMetadata("clickdelay")){
				ItemStack item = p.getInventory().getItemInMainHand();
				
				if(item != null){
					if(item.isSimilar(Items.getHP()) || item.isSimilar(Items.getLinux()) || item.isSimilar(Items.getAlienware())){
						if(!plugin.cooldownList.contains(p.getName())){
							if(!plugin.cooldownList.contains(t.getName())){
								if(!p.hasMetadata("hacking")){
									plugin.hackPlayer(p, t);
								}
							}else{
								p.sendMessage(MSG.playerOffline());
							}
						}else{
							p.sendMessage(MSG.needsCooldown());
						}
					}
				}
				
				p.setMetadata("clickdelay", new FixedMetadataValue(plugin, true));
			}else{
				p.removeMetadata("clickdelay", plugin);
			}
		}else{
			if(e.getRightClicked().getType()==EntityType.ITEM_FRAME){
				if(!e.getPlayer().isOp()){
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void foodChange(FoodLevelChangeEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void weatherChange(WeatherChangeEvent e){
		e.setCancelled(true);
	}
}
