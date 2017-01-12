package tech.xfyrewolfx.thegrid;

import org.bukkit.plugin.java.JavaPlugin;

import tech.xfyrewolfx.thegrid.files.Configuration;
import tech.xfyrewolfx.thegrid.files.Messages;
import tech.xfyrewolfx.thegrid.files.Outlets;
import tech.xfyrewolfx.thegrid.files.Systems;

public class TheGrid extends JavaPlugin{
	private Messages msgs;
	private Outlets outlets;
	private Systems systems;
	private Configuration config;
	
	public void onEnable(){
		msgs = new Messages(this);
		outlets = new Outlets(this);
		systems = new Systems(this);
		config = new Configuration(this);
		
		//TODO register listeners, start timers for sparks/tips. 
		//TODO create command handler (/thegrid).
		// Be thinking about XP / Bitcoin rewards. Players' max EXP should increase by ~100 per level.
		// GUIs should be more efficient. Create a GUI class with all necessary actions and implement it in subsequent GUIs.
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
}
