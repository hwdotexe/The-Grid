package me.xfyrewolfx.thegrid.timers;

import org.bukkit.Sound;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.xfyrewolfx.thegrid.EnchantGlow;
import me.xfyrewolfx.thegrid.Main;
import me.xfyrewolfx.thegrid.apis.TitleAPI;

public class BatteryTick extends BukkitRunnable{
	
	Player p;
	Main plugin;
	double max;
	BossBar bb;
	public BatteryTick(Player pl, Main c){
		p=pl;
		plugin=c;
		
		max = plugin.getPlayerBattery(p.getName());
		
		bb = plugin.pdata.get(p).getBatteryBar();
		bb.setProgress(p.getLevel()/max);
		bb.addPlayer(p);
	}
	
	public void run(){
		if(p != null && p.isOnline()){
			if(p.getLevel() >= 1){
				if(!p.hasMetadata("charging")){
					if(plugin.isFirewallActive(p.getName())){
						p.setLevel(p.getLevel()-2);
					}else{
						p.setLevel(p.getLevel()-1);
					}
					
					double x = p.getLevel()/max;
					bb.setProgress(x);
				}
			}
			
			if(p.getLevel()<=0){
				if(plugin.isFirewallActive(p.getName())){
					ItemStack fw = p.getInventory().getItem(8);
					fw.removeEnchantment(EnchantGlow.getGlow());
					p.getInventory().setItem(8, fw);
				}
				
				TitleAPI.sendTitle(p, 0, 0, 100, "§8[§4 ! §8]", "§7Your Battery is Exhausted!");
				p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 5, 1);
				
				double x = p.getLevel()/max;
				bb.setProgress(x);
			}
		}
	}
}
