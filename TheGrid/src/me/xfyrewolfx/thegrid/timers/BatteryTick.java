package me.xfyrewolfx.thegrid.timers;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.xfyrewolfx.thegrid.Main;

public class BatteryTick extends BukkitRunnable{
	
	Player p;
	Main plugin;
	public BatteryTick(Player pl, Main c){
		p=pl;
		plugin=c;
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
					
					if(p.getLevel()<=0){
						if(plugin.isFirewallActive(p.getName())){
							ItemStack fw = p.getInventory().getItem(8);
							fw.removeEnchantment(Enchantment.DURABILITY);
							p.getInventory().setItem(8, fw);
						}
					}
				}
			}
		}
	}
}
