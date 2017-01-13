package tech.xfyrewolfx.thegrid.listeners;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;

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
		
		// Tutorial
		if(!e.getPlayer().hasPlayedBefore()){
			Location tl = plugin.getUserConfig().getTutorialLocation();
			if(tl != null){
				e.getPlayer().teleport(tl);
				new Tutorial(plugin, e.getPlayer()).runTaskTimer(plugin, 20, 150);
			}
		}else{
			//TODO inventory, battery, and scoreboard
			new Battery(plugin, e.getPlayer()).runTaskTimer(plugin, 600, 600);
			e.getPlayer().getInventory().setContents(plugin.getGPlayer(e.getPlayer()).getInventoryItems());
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e){
		e.setQuitMessage(plugin.getMessages().playerQuit(e.getPlayer().getName()));
		
		plugin.getGPlayer(e.getPlayer()).saveValues();
		plugin.getGPlayers().remove(e.getPlayer().getName());
	}
	
	//TODO kick event
	
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
		e.getPlayer().setDisplayName("§8[ §a"+time+" §8] §7"+e.getPlayer().getName());
		e.setMessage("§f"+e.getMessage());
		e.setFormat(e.getPlayer().getDisplayName()+": "+e.getMessage());
	}
}
