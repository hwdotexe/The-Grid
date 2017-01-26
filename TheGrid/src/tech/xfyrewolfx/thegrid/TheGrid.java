package tech.xfyrewolfx.thegrid;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import tech.xfyrewolfx.thegrid.apis.ScoreboardAPI;
import tech.xfyrewolfx.thegrid.files.Configuration;
import tech.xfyrewolfx.thegrid.files.GPlayer;
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
	private HashMap<String, GPlayer> gplayers;
	
	public void onEnable(){
		msgs = new Messages(this);
		outlets = new Outlets(this);
		systems = new Systems(this);
		config = new Configuration(this);
		gplayers = new HashMap<String, GPlayer>();
		
		this.getCommand("thegrid").setExecutor(new CMD(this));
		this.getCommand("gridspawn").setExecutor(new CMD(this));
		Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
		Bukkit.getPluginManager().registerEvents(new ClickListener(this), this);
		new Sparks(this).runTaskTimer(this, 100, 100);
	}
	
	public GPlayer getGPlayer(Player p){
		return gplayers.get(p.getName());
	}
	
	public HashMap<String, GPlayer> getGPlayers(){
		return gplayers;
	}
	
	public void onDisable(){
		outlets.saveValues();
		systems.saveValues();
		
		for(GPlayer gp : this.getGPlayers().values()){
			gp.saveValues();
		}
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
		ScoreboardAPI.setScore(p, "§2[*] EXP", getGPlayer(p).getExp());
		ScoreboardAPI.setScore(p, "§c[#] Level", getGPlayer(p).getLevel());
		ScoreboardAPI.setScore(p, "§6[$] Bitcoins", getGPlayer(p).getBTC());
	}
	
	public GSystem isBlockSystem(Location l){
		for(GSystem sys : getSystems().getSystemObjects()){
			if(l.getX() == sys.getLocation().getX() && l.getY()== sys.getLocation().getY() && l.getZ()== sys.getLocation().getZ()){
				return sys;
			}
		}
		return null;
	}
	
	public Outlet isBlockOutlet(Location l){
		for(Outlet out : getOutlets().getOutletObjects()){
			if(l.getX() == out.getLocation().getX() && l.getY()== out.getLocation().getY() && l.getZ()== out.getLocation().getZ()){
				return out;
			}
		}
		return null;
	}
	
	public void hackCPU(Player p, GSystem s){
		if(getGPlayer(p).getBatteryBar().getProgress()>0.0){
			getGPlayer(p).setIsHacking(true);
			p.sendMessage("§a~$: connected to "+s.getName()+" (lv. "+s.getLevel()+")");
			
			VirusesGUI vgui = new VirusesGUI(this, p, s, true);
			Bukkit.getPluginManager().registerEvents(vgui, this);
		}else{
			p.sendMessage(getMessages().batteryDepleted());
		}
	}
	
	public void hackPlayer(Player h, Player t){
		if(getGPlayer(h).getBatteryBar().getProgress()>0.0){
			getGPlayer(h).setIsHacking(true);
			h.sendMessage("§a~$: connected to "+t.getName()+" (lv. "+getGPlayer(t).getLevel()+")");
			
			VirusesGUI vgui = new VirusesGUI(this, h, t, true);
			Bukkit.getPluginManager().registerEvents(vgui, this);
		}else{
			h.sendMessage(getMessages().batteryDepleted());
		}
	}
}
