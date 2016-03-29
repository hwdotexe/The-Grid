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
	Player t;
	Location tl;
	ItemStack v;
	int secs;
	boolean tHadValue; //used to check if target logs out
	Main plugin;
	int firewallLevel;
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
			}else{
				if(t != null){
					t.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,100,1));
					t.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,100,1));
					String msg = "";
					
					if(v.isSimilar(Items.cryptolockerVirus())){
						int vbtc = plugin.getPlayerBitcoin(t.getName());
						
						if(vbtc > 1){
							plugin.setPlayerBitcoins(t.getName(), vbtc-1);
							plugin.setPlayerBitcoins(p.getName(), plugin.getPlayerBitcoin(p.getName())+1);
							msg=p.getName()+" stole 1 BTC from you";
							p.sendMessage("§8[§2!§8] §7You stole §61 BTC §7from §6"+t.getName());
						}else{
							msg=p.getName()+"Your system is temporarily disabled";
							p.sendMessage("§8[§2!§8] §6"+t.getName()+" §7could not afford to pay the ransom!");
						}
						
						int exp = plugin.generateEXP(firewallLevel, 3);
						p.sendMessage("§8[§2!§8] §7You generated §3"+exp+" EXP §7from the Cryptolocker!");
						plugin.addExp(p.getName(), exp);
						
					}else{
						if(v.isSimilar(Items.adwareVirus())){
							int pbtc = plugin.getPlayerBitcoin(p.getName());
							
							Random r = new Random();
							int ri = r.nextInt(4);
							int exp = plugin.generateEXP(firewallLevel, 1);
							
							plugin.setPlayerBitcoins(p.getName(), pbtc+ri);
							msg="Your system is temporarily disabled";
							p.sendMessage("§8[§2!§8] §7You generated §6"+ri+" BTC §7from the Adware!");
							p.sendMessage("§8[§2!§8] §7You generated §3"+exp+" EXP §7from the Adware!");
							plugin.addExp(p.getName(), exp);
						}else{
							if(v.isSimilar(Items.killdiscVirus())){
								int exp = plugin.generateEXP(firewallLevel, 2);
								msg="Your system is temporarily disabled";
								p.sendMessage("§8[§2!§8] §7You generated §3"+exp+" EXP §7from the Killdisc!");
								plugin.addExp(p.getName(), exp);
							}else{
								int exp = plugin.generateEXP(firewallLevel, 1);
								msg="Your system is temporarily disabled";
								p.sendMessage("§8[§2!§8] §7You generated §3"+exp+" EXP §7from the Virus!");
								plugin.addExp(p.getName(), exp);
							}
						}
					}
					
					TitleAPI.sendTitle(t, 10, 10, 100, "§c§lYou Were Hacked", msg);
					
					plugin.cooldownList.add(p.getName());
					plugin.cooldownList.add(t.getName());
					new Cooldown(120, t, plugin).runTaskTimer(plugin, 0, 20);
					new Cooldown(60, p, plugin).runTaskTimer(plugin, 0, 20);
					
					p.removeMetadata("hacking", plugin);
					this.cancel();
				}else{
					if(v.isSimilar(Items.cryptolockerVirus()) || v.isSimilar(Items.adwareVirus())){
						Random r = new Random();
						int ri = r.nextInt(4);
						int exp = plugin.generateEXP(firewallLevel, 1);
						
						plugin.setPlayerBitcoins(p.getName(), plugin.getPlayerBitcoin(p.getName())+ri);
						p.sendMessage("§8[§2!§8] §7You generated §6"+ri+" BTC §7from the Virus!");
						p.sendMessage("§8[§2!§8] §7You generated §3"+exp+" EXP §7from the Virus!");
						plugin.addExp(p.getName(), exp);
					}else{
						int exp = plugin.generateEXP(firewallLevel, 1);
						p.sendMessage("§8[§2!§8] §7You generated §3"+exp+" EXP §7from the Virus!");
						plugin.addExp(p.getName(), exp);
					}
					
					plugin.cooldownList.add(p.getName());
					new Cooldown(60, p, plugin).runTaskTimer(plugin, 0, 20);
					
					p.removeMetadata("hacking", plugin);
					this.cancel();
				}
				
				p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 5, 1);
				p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,10,1));
			}
		}else{
			p.removeMetadata("hacking", plugin);
			this.cancel();
		}
	}
}
