package tech.xfyrewolfx.thegrid;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import tech.xfyrewolfx.thegrid.apis.EnchantGlow;
import tech.xfyrewolfx.thegrid.apis.ScoreboardAPI;
import tech.xfyrewolfx.thegrid.files.Configuration;
import tech.xfyrewolfx.thegrid.files.GPlayer;
import tech.xfyrewolfx.thegrid.files.Items;
import tech.xfyrewolfx.thegrid.files.Messages;
import tech.xfyrewolfx.thegrid.files.Outlets;
import tech.xfyrewolfx.thegrid.files.Systems;
import tech.xfyrewolfx.thegrid.gui.VirusesGUI;
import tech.xfyrewolfx.thegrid.listeners.ClickListener;
import tech.xfyrewolfx.thegrid.listeners.PlayerListener;
import tech.xfyrewolfx.thegrid.runnables.Sparks;

public class TheGrid extends JavaPlugin{
	private Messages msgs;
	private Outlets outlets;
	private Systems systems;
	private Configuration config;
	private HashMap<String, GPlayer> gridPlayers;
	private Items items;
	
	public void onEnable(){
		msgs = new Messages(this);
		outlets = new Outlets(this);
		systems = new Systems(this);
		config = new Configuration(this);
		gridPlayers = new HashMap<String, GPlayer>();
		items = new Items(this);
		
		EnchantGlow.Register();
		
		this.getCommand("thegrid").setExecutor(new CommandHandler(this));
		this.getCommand("gridspawn").setExecutor(new CommandHandler(this));
		Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
		Bukkit.getPluginManager().registerEvents(new ClickListener(this), this);
		new Sparks(this).runTaskTimer(this, 100, 100);
	}
	
	public void onDisable(){
		outlets.saveValues();
		systems.saveValues();
		
		for(GPlayer gp : this.getGPlayers().values()){
			gp.saveValues();
		}
	}
	
	public Items getItems(){
		return items;
	}
	
	public GPlayer getGridPlayer(Player p){
		return gridPlayers.get(p.getName());
	}
	
	public HashMap<String, GPlayer> getGPlayers(){
		return gridPlayers;
	}
	
	public Messages getMessages(){
		return msgs;
	}
	
	public Systems getSystems(){
		return systems;
	}
	
	public Outlets getOutlets(){
		return outlets;
	}
	
	public Configuration getUserConfig(){
		return config;
	}
	
	public void giveNewScoreboard(Player p){
		ScoreboardAPI.giveScoreboard(p, getMessages().scoreboardTitle());
		updateScoreboard(p);
	}
	
	public void updateScoreboard(Player p){
		ScoreboardAPI.setScore(p, "§2[*] EXP", getGridPlayer(p).getExp());
		ScoreboardAPI.setScore(p, "§c[#] Level", getGridPlayer(p).getLevel());
		ScoreboardAPI.setScore(p, "§6[$] Bitcoins", getGridPlayer(p).getBTC());
	}
	
	public GridSystem isBlockSystem(Location l){
		for(GridSystem sys : getSystems().getSystemObjects()){
			if(sys.getLocation().getWorld() != null){
				if(l.getX() == sys.getLocation().getX() && l.getY()== sys.getLocation().getY() && l.getZ()== sys.getLocation().getZ() && l.getWorld().getName() == sys.getLocation().getWorld().getName()){
					return sys;
				}
			}
		}
		return null;
	}
	
	public GridOutlet isBlockOutlet(Location l){
		for(GridOutlet out : getOutlets().getOutletObjects()){
			if(out.getLocation().getWorld() != null){
				if(l.getX() == out.getLocation().getX() && l.getY()== out.getLocation().getY() && l.getZ()== out.getLocation().getZ() && l.getWorld().getName() == out.getLocation().getWorld().getName()){
					return out;
				}
			}
		}
		return null;
	}
	
	public void hackCPU(Player p, GridSystem s){
		if(getGridPlayer(p).getBatteryBar().getProgress()>0.0){
			getGridPlayer(p).setIsHacking(true);
			p.sendMessage("§a~$: connected to "+s.getName()+" (lv. "+s.getLevel()+")");
			
			VirusesGUI vgui = new VirusesGUI(this, p, s, true);
			Bukkit.getPluginManager().registerEvents(vgui, this);
		}else{
			p.sendMessage(getMessages().batteryDepleted());
		}
	}
	
	public void hackPlayer(Player h, Player t){
		if(getGridPlayer(h).getBatteryBar().getProgress()>0.0){
			getGridPlayer(h).setIsHacking(true);
			h.sendMessage("§a~$: connected to "+t.getName()+" (lv. "+getGridPlayer(t).getLevel()+")");
			
			VirusesGUI vgui = new VirusesGUI(this, h, t, true);
			Bukkit.getPluginManager().registerEvents(vgui, this);
		}else{
			h.sendMessage(getMessages().batteryDepleted());
		}
	}
}
