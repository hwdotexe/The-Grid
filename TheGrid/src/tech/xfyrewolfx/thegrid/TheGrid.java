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
import tech.xfyrewolfx.thegrid.runnables.HackNPC;
import tech.xfyrewolfx.thegrid.runnables.HackPlayer;
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
			if(l==sys.getLocation()){
				return sys;
			}
		}
		return null;
	}
	
	public Outlet isBlockOutlet(Location l){
		for(Outlet out : getOutlets().getOutletObjects()){
			if(l==out.getLocation()){
				return out;
			}
		}
		return null;
	}
	
	public void hackCPU(Player p, GSystem s){
		if(getGPlayer(p).getBatteryBar().getProgress()>0.0){
			p.sendMessage("§a~$: connected to "+s.getName()+" (lv. "+s.getLevel()+")");
			
			VirusesGUI vgui = new VirusesGUI(this, p, true);
			Bukkit.getPluginManager().registerEvents(vgui, this);
			String virus = "";
			
			while(true){
				if(vgui.getClickedVirus().length()>0){
					virus = vgui.getClickedVirus();
					break;
				}
			}
			
			if(virus != "closed"){
				if(s.getLevel() <= getGPlayer(p).getLevel()){
					new HackNPC(this, s, p, virus).runTaskTimer(this, 20, 20);
				}else{
					p.sendMessage(getMessages().getFirewallTooStrong());
				}
			}else{
				p.sendMessage("§a~$: disconnected from "+s.getName());
			}
		}else{
			p.sendMessage(getMessages().batteryDepleted());
		}
	}
	
	public void hackPlayer(Player h, Player t){
		if(getGPlayer(h).getBatteryBar().getProgress()>0.0){
			h.sendMessage("§a~$: connected to "+t.getName()+" (lv. "+getGPlayer(t).getLevel()+")");
			
			VirusesGUI vgui = new VirusesGUI(this, h, true);
			Bukkit.getPluginManager().registerEvents(vgui, this);
			String virus = "";
			
			while(true){
				if(vgui.getClickedVirus().length()>0){
					virus = vgui.getClickedVirus();
					break;
				}
			}
			
			if(virus != "closed"){
				
				int levels = getGPlayer(t).getLevel();
				if(getGPlayer(t).getFirewallActive()){
					levels += 5; // firewall gives +5
				}
				
				if(levels <= getGPlayer(h).getLevel()){
					new HackPlayer(this, t, h, virus).runTaskTimer(this, 20, 20);
				}else{
					h.sendMessage(getMessages().getFirewallTooStrong());
					h.sendMessage("§a~$: disconnected from "+t.getName());
				}
			}else{
				h.sendMessage("§a~$: disconnected from "+t.getName());
			}
		}else{
			h.sendMessage(getMessages().batteryDepleted());
		}
	}
}
