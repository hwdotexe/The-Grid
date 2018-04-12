package tech.xfyrewolfx.thegrid.runnables;

import org.bukkit.Sound;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import tech.xfyrewolfx.thegrid.TheGrid;
import tech.xfyrewolfx.thegrid.apis.EnchantGlow;

public class Battery extends BukkitRunnable{
	private TheGrid plugin;
	private Player p;
	private double max;
	private BossBar bb;
	public Battery(TheGrid c, Player pl){
		plugin=c;
		p=pl;
		
		max = plugin.getGPlayer(p).getBattery();
		bb = plugin.getGPlayer(p).getBatteryBar();
		double x = p.getLevel()/max;
		bb.setProgress(x);
		bb.addPlayer(p);
	}
	
	public void run(){
		if(p != null && p.isOnline()){
			if(p.getLevel() >= 1){
				if(!plugin.getGPlayer(p).getIsCharging()){
					if(plugin.getGPlayer(p).getFirewallActive()){
						p.setLevel(p.getLevel()-2);
					}else{
						p.setLevel(p.getLevel()-1);
					}
					
					double x = p.getLevel()/max;
					bb.setProgress(x);
				}
			}
			
			if(p.getLevel()<=0){
				if(plugin.getGPlayer(p).getFirewallActive()){
					ItemStack fw = p.getInventory().getItem(8);
					fw.removeEnchantment(EnchantGlow.getGlow());
					p.getInventory().setItem(8, fw);
					plugin.getGPlayer(p).setFirewallActive(false);
				}
				
				p.sendTitle("§8[§4 ! §8]", "§7Your Battery is Exhausted!", 0, 0, 100);
				p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 5, 1);
				
				double x = p.getLevel()/max;
				bb.setProgress(x);
			}
		}else{
			this.cancel();
		}
	}
}
