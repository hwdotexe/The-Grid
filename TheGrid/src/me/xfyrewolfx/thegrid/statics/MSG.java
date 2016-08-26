package me.xfyrewolfx.thegrid.statics;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MSG {
	public static void tierUnlocked(String name, String tier){
		if(Bukkit.getPlayer(name)!=null){
			Player p = Bukkit.getPlayer(name);
			p.sendMessage("§8[ §2! §8] §7You have been promoted to the §6"+tier+" §7tier!");
			
			if(tier.equalsIgnoreCase("specialist")){
				p.sendMessage("      §7> §6HP Pavilion G4 §7upgraded to §6Linux-based Laptop");
				p.sendMessage("      §7> §6Virtual Firewall §7upgraded to §6Basic Hardware Firewall");
				p.sendMessage("      §7> §6Battery §7upgraded to §624W");
				p.sendMessage("      §7> Unlocked §6SQL Slammer Virus");
				p.sendMessage("      §7> Unlocked §6Cryptolocker Virus");
			}
			if(tier.equalsIgnoreCase("elite")){
				p.sendMessage("      §7> §6Linux-based Laptop §7upgraded to §6Alienware 18 Laptop");
				p.sendMessage("      §7> §6Basic Hardware Firewall §7upgraded to §6Advanced Hardware Firewall");
				p.sendMessage("      §7> §6Battery §7upgraded to §648W");
			}
			if(tier.equalsIgnoreCase("pro")){
				p.sendMessage("      §7> §6Advanced Hardware Firewall §7upgraded to §6Encrypted Hardware Firewall");
				p.sendMessage("      §7> §6Battery §7upgraded to §672W");
				p.sendMessage("      §7> Unlocked §6DDoS Virus");
			}
		}
	}
	
	public static String joinMessage(String name){
		return "§8[ §a+ §8] §a"+name;
	}
	
	public static String leaveMessage(String name){
		return "§8[ §c- §8] §a"+name;
	}
	
	public static String targetTooStrong(int lvl){
		return "§8[ §2! §8] §7The target's firewall is too strong! §8§l(§e§l"+lvl+"§8§l)";
	}
	
	public static String batteryDepleted(){
		return "§8[ §2! §8] §7Your Battery is exhausted!";
	}
	
	public static String batteryCharging(){
		return "§8[ §2! §8] §7Battery is recharging...";
	}
	
	public static String batteryFull(){
		return "§8[ §2! §8] §7Battery is full!";
	}
	
	public static String playerOffline(){
		return "§8[ §2! §8] §7That player's system is currently offline.";
	}
	
	public static String systemCooledDown(){
		return "§8[ §2! §8] §7Your system has cooled down.";
	}
	
	public static String needsCooldown(){
		return "§8[ §2! §8] §7Your system must cool down before you can continue.";
	}
	
	public static String outOfRange(){
		return "§8[ §2! §8] §7You are out of range! Operation cancelled.";
	}
	
	public static String targetLoggedOut(){
		return "§8[ §2! §8] §7Your target left the game! Hacking cancelled.";
	}
}
