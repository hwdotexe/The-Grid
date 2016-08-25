package me.xfyrewolfx.thegrid.timers;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.xfyrewolfx.thegrid.Main;
import me.xfyrewolfx.thegrid.apis.TitleAPI;
import me.xfyrewolfx.thegrid.statics.Items;
import me.xfyrewolfx.thegrid.statics.MSG;

public class HackTimer extends BukkitRunnable{
	
	Player p;
	Player t; //can be null if hacking an NPC
	Location tl;
	ItemStack v;
	int secs;
	boolean tHadValue; //used to check if target logs out
	Main plugin;
	int firewallLevel;
	Random r;
	public HackTimer(Player hacker, Player target, Location targetLocation, ItemStack virusUsed, Main c, int firewall){
		p=hacker;
		t=target;
		tl=targetLocation;
		v=virusUsed;
		plugin=c;
		firewallLevel = firewall;
		secs=15;
		
		if(t != null){
			tHadValue=true;
		}else{
			tHadValue=false;
		}
		
		r = new Random();
	}
	
	public void run(){
		if(p != null){
			if(secs > 0){
				secs=secs-1;
				
				if(t != null){
					tl=t.getLocation();
				}else{
					if(tHadValue){
						p.sendMessage(MSG.targetLoggedOut());
						p.removeMetadata("hacking", plugin);
						this.cancel();
					}
				}
				
				if(p.getLocation().distance(tl)>15){
					p.sendMessage(MSG.outOfRange());
					p.removeMetadata("hacking", plugin);
					this.cancel();
				}
				
				if(p.getLevel()<1){
					p.sendMessage(MSG.batteryDepleted());
					p.removeMetadata("hacking", plugin);
					this.cancel();
				}
				
				StringBuilder sb = new StringBuilder();
				String bar="";
				sb.append(v.getItemMeta().getDisplayName()+" §f[");
				
				for(int i=0; i<(15-secs); i++){
					sb.append("§a|");
				}
				for(int i=0; i<secs; i++){
					sb.append("§f|");
				}
				
				sb.append("§f]");
				bar=sb.toString();
				TitleAPI.sendTitle(p, 0, 0, 25, "", bar);
			}else{ //Hacking completed
				if(t != null){
					t.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,100,1));
					t.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,100,1));
					String msg = this.checkViruses(t);
					
					TitleAPI.sendTitle(t, 10, 10, 100, "§c§lYou Were Hacked", msg);
					
					plugin.cooldownList.add(p.getName());
					plugin.cooldownList.add(t.getName());
					new Cooldown(this.getVariableCooldown(plugin.getPlayerLevel(t.getName())), t, plugin).runTaskTimer(plugin, 0, 20);
					new Cooldown(this.getVariableCooldown(plugin.getPlayerLevel(p.getName())), p, plugin).runTaskTimer(plugin, 0, 20);
					
					p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 5, 1);
					p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,10,1));
					
					p.removeMetadata("hacking", plugin);
					this.cancel();
				}else{
					this.checkVirusesNPC();
					
					plugin.cooldownList.add(p.getName());
					new Cooldown(this.getVariableCooldown(plugin.getPlayerLevel(p.getName())), p, plugin).runTaskTimer(plugin, 0, 20);
					p.removeMetadata("hacking", plugin);
					this.cancel();
				}
			}
		}else{
			p.removeMetadata("hacking", plugin);
			this.cancel();
		}
	}
	
	private String checkViruses(Player t){
		String msg="";
		
		//CryptoLocker (3)
		if(v.isSimilar(Items.cryptolockerVirus())){
			int vbtc = plugin.getPlayerBitcoin(t.getName());
			int btc = r.nextInt(3)+1;
			
			if(vbtc >= btc){
				plugin.setPlayerBitcoins(t.getName(), vbtc-btc);
				plugin.setPlayerBitcoins(p.getName(), plugin.getPlayerBitcoin(p.getName())+btc);
				msg=p.getName()+" stole "+btc+" BTC from you";
				p.sendMessage("§8[§2!§8] §7You stole §6"+btc+" BTC §7from §6"+t.getName());
			}else{
				msg=p.getName()+" stole "+vbtc+" BTC from you";
				plugin.setPlayerBitcoins(t.getName(), 0);
				plugin.setPlayerBitcoins(p.getName(), plugin.getPlayerBitcoin(p.getName())+vbtc);
				p.sendMessage("§8[§2!§8] §7You stole §6"+vbtc+" BTC §7from §6"+t.getName());
			}
			
			int exp = plugin.generateEXP(firewallLevel, 3);
			p.sendMessage("§8[§2!§8] §7You generated §3"+exp+" EXP §7from the Cryptolocker!");
			plugin.addExp(p.getName(), exp);
		}
		
		//Adware (2)
		if(v.isSimilar(Items.adwareVirus())){
			int pbtc = plugin.getPlayerBitcoin(p.getName());
			
			int ri = r.nextInt(7)+1;
			
			plugin.setPlayerBitcoins(p.getName(), pbtc+ri);
			msg="Your system is temporarily disabled";
			p.sendMessage("§8[§2!§8] §7You generated §6"+ri+" BTC §7from the Adware!");
		}
		
		//Killdisc (2)
		if(v.isSimilar(Items.killdiscVirus())){
			int exp = plugin.generateEXP(firewallLevel, 2);
			msg="Your system is temporarily disabled";
			p.sendMessage("§8[§2!§8] §7You generated §3"+exp+" EXP §7from the Killdisc!");
			plugin.addExp(p.getName(), exp);
		}
		
		//DDoS (2)
		if(v.isSimilar(Items.ddosVirus())){
			int exp = plugin.generateEXP(firewallLevel, 2);
			msg="Your system is temporarily disabled";
			p.sendMessage("§8[§2!§8] §7You generated §3"+exp+" EXP §7from the DDoS attack!");
			plugin.addExp(p.getName(), exp);
		}
		
		//SQL Slammer (1)
		if(v.isSimilar(Items.sqlVirus())){
			int exp = plugin.generateEXP(firewallLevel, 1);
			msg="Your system is temporarily disabled";
			p.sendMessage("§8[§2!§8] §7You generated §3"+exp+" EXP §7from the SQL Slammer!");
			plugin.addExp(p.getName(), exp);
		}
		
		//Shutdown.vbs (1)
		if(v.isSimilar(Items.shutdownVirus())){
			int exp = plugin.generateEXP(firewallLevel, 1);
			msg="Your system is temporarily disabled";
			p.sendMessage("§8[§2!§8] §7You generated §3"+exp+" EXP §7from the Shutdown virus!");
			plugin.addExp(p.getName(), exp);
		}
		
		return msg;
	}
	
	private void checkVirusesNPC(){	
		//CryptoLocker (3)
		if(v.isSimilar(Items.cryptolockerVirus())){
			int btc = r.nextInt(3)+1;
			int exp = plugin.generateEXP(firewallLevel, 3);
			
			plugin.setPlayerBitcoins(p.getName(), plugin.getPlayerBitcoin(p.getName())+btc);
			plugin.addExp(p.getName(), exp);
			p.sendMessage("§8[§2!§8] §7You generated §3"+exp+" EXP §7from the Cryptolocker!");
			p.sendMessage("§8[§2!§8] §7You generated §3"+btc+" BTC §7from the Cryptolocker!");
		}
		
		//Adware (2)
		if(v.isSimilar(Items.adwareVirus())){
			int pbtc = plugin.getPlayerBitcoin(p.getName());
			int ri = r.nextInt(5)+1;
			
			plugin.setPlayerBitcoins(p.getName(), pbtc+ri);
			p.sendMessage("§8[§2!§8] §7You generated §6"+ri+" BTC §7from the Adware!");
		}
		
		//Killdisc (2)
		if(v.isSimilar(Items.killdiscVirus())){
			int exp = plugin.generateEXP(firewallLevel, 2);
			p.sendMessage("§8[§2!§8] §7You generated §3"+exp+" EXP §7from the Killdisc!");
			plugin.addExp(p.getName(), exp);
		}
		
		//DDoS (2)
		if(v.isSimilar(Items.ddosVirus())){
			int exp = plugin.generateEXP(firewallLevel, 2);
			p.sendMessage("§8[§2!§8] §7You generated §3"+exp+" EXP §7from the DDoS attack!");
			plugin.addExp(p.getName(), exp);
		}
		
		//SQL Slammer (1)
		if(v.isSimilar(Items.sqlVirus())){
			int exp = plugin.generateEXP(firewallLevel, 1);
			p.sendMessage("§8[§2!§8] §7You generated §3"+exp+" EXP §7from the SQL Slammer!");
			plugin.addExp(p.getName(), exp);
		}
		
		//Shutdown.vbs (1)
		if(v.isSimilar(Items.shutdownVirus())){
			int exp = plugin.generateEXP(firewallLevel, 1);
			p.sendMessage("§8[§2!§8] §7You generated §3"+exp+" EXP §7from the Shutdown virus!");
			plugin.addExp(p.getName(), exp);
		}
	}
	
	private int getVariableCooldown(int pLevel){
		int seconds = (pLevel * 5)+r.nextInt(30);
		return seconds;
	}
}
