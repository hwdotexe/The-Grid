package tech.xfyrewolfx.thegrid.listeners;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import tech.xfyrewolfx.thegrid.TheGrid;
import tech.xfyrewolfx.thegrid.files.GPlayer;
import tech.xfyrewolfx.thegrid.runnables.Battery;
import tech.xfyrewolfx.thegrid.runnables.Tutorial;

public class PlayerListener implements Listener{
	
	TheGrid plugin;
	private Calendar cal;
	private SimpleDateFormat sdf;
	public PlayerListener(TheGrid c){
		sdf = new SimpleDateFormat("HH:mm:ss");
		plugin=c;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		e.setJoinMessage(plugin.getMessages().playerJoined(e.getPlayer().getName()));
		e.getPlayer().setGameMode(GameMode.ADVENTURE);
		plugin.getGPlayers().put(e.getPlayer().getName(), new GPlayer(plugin, e.getPlayer()));
		
		if(!e.getPlayer().hasPlayedBefore()){
			Location tl = plugin.getUserConfig().getTutorialLocation();
			if(tl != null){
				e.getPlayer().teleport(tl);
				new Tutorial(plugin, e.getPlayer()).runTaskTimer(plugin, 20, 150);
			}
		}else{
			new Battery(plugin, e.getPlayer()).runTaskTimer(plugin, 600, 600);
			e.getPlayer().getInventory().setContents(plugin.getGPlayer(e.getPlayer()).getInventoryItems());
			plugin.giveNewScoreboard(e.getPlayer());
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e){
		e.setQuitMessage(plugin.getMessages().playerQuit(e.getPlayer().getName()));
		
		plugin.getGPlayer(e.getPlayer()).saveValues();
		plugin.getGPlayers().remove(e.getPlayer().getName());
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent e){
		e.setLeaveMessage(plugin.getMessages().playerQuit(e.getPlayer().getName()));
		
		plugin.getGPlayer(e.getPlayer()).saveValues();
		plugin.getGPlayers().remove(e.getPlayer().getName());
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		if(!e.getPlayer().isOp()){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPing(ServerListPingEvent e){
		e.setMotd(plugin.getUserConfig().getMOTD());
	}
	
	@EventHandler
	public void onBreakItemFrame(HangingBreakEvent e){
		if(e.getCause()!=RemoveCause.PHYSICS){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e){
		cal = Calendar.getInstance();
        String time = sdf.format(cal.getTime());
		e.getPlayer().setDisplayName("§8[§a"+time+"§8] §7"+e.getPlayer().getName());
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
	public void foodChange(FoodLevelChangeEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void weatherChange(WeatherChangeEvent e){
		e.setCancelled(true);
	}
}
