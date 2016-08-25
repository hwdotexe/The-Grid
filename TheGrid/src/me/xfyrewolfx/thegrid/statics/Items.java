package me.xfyrewolfx.thegrid.statics;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {
	public static ItemStack[] getBasicInventory(){
		Inventory inv = Bukkit.createInventory(null, 36);
		inv.setItem(0, getHP());
		inv.setItem(4, listViruses());
		inv.setItem(8, getVirtualFirewall());
		
		return inv.getContents();
	}
	
	public static ItemStack[] getBasicInventoryTraceroute(){
		Inventory inv = Bukkit.createInventory(null, 36);
		inv.setItem(0, getHP());
		inv.setItem(3, listViruses());
		inv.setItem(5, getTraceroute());
		inv.setItem(8, getVirtualFirewall());
		
		return inv.getContents();
	}
	public static ItemStack[] getSpecialistInventory(){
		Inventory inv = Bukkit.createInventory(null, 36);
		inv.setItem(0, getLinux());
		inv.setItem(3, listViruses());
		inv.setItem(5, getTraceroute());
		inv.setItem(8, getBasicHardwareFirewall());
		
		return inv.getContents();
	}
	public static ItemStack[] getEliteInventory(){
		Inventory inv = Bukkit.createInventory(null, 36);
		inv.setItem(0, getAlienware());
		inv.setItem(3, listViruses());
		inv.setItem(5, getTraceroute());
		inv.setItem(8, getAdvancedHardwareFirewall());
		
		return inv.getContents();
	}
	public static ItemStack[] getProInventory(){
		Inventory inv = Bukkit.createInventory(null, 36);
		inv.setItem(0, getAlienware());
		inv.setItem(3, listViruses());
		inv.setItem(5, getTraceroute());
		inv.setItem(8, getEncryptedFirewall());
		
		return inv.getContents();
	}
	
	
	/**
	 * Computers
	 */
	public static ItemStack getHP(){
		ItemStack item = new ItemStack(Material.DAYLIGHT_DETECTOR);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§e§lHP Pavilion G4");
		List<String> lore = new ArrayList<String>();
		lore.add("§7A simple Laptop from 2011");
		
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getLinux(){
		ItemStack item = new ItemStack(Material.DAYLIGHT_DETECTOR);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§6§lLinux-powered Laptop");
		List<String> lore = new ArrayList<String>();
		lore.add("§7A decent laptop with a secure");
		lore.add("§7Operating System");
		
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getAlienware(){
		ItemStack item = new ItemStack(Material.DAYLIGHT_DETECTOR);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§c§lAlienware 18");
		List<String> lore = new ArrayList<String>();
		lore.add("§7A powerhouse laptop with");
		lore.add("§7extreme performance");
		
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	
	/**
	 * Firewalls
	 */
	public static ItemStack getVirtualFirewall(){
		ItemStack item = new ItemStack(Material.RECORD_10);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§a§lVirtual Firewall");
		List<String> lore = new ArrayList<String>();
		lore.add("§7A basic software-based firewall");
		
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getBasicHardwareFirewall(){
		ItemStack item = new ItemStack(Material.BREWING_STAND_ITEM);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§2§lBasic Firewall");
		List<String> lore = new ArrayList<String>();
		lore.add("§7A decent physical firewall");
		
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getAdvancedHardwareFirewall(){
		ItemStack item = new ItemStack(Material.BREWING_STAND_ITEM);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§3§lAdvanced Firewall");
		List<String> lore = new ArrayList<String>();
		lore.add("§7An advanced physical firewall");
		
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getEncryptedFirewall(){
		ItemStack item = new ItemStack(Material.BREWING_STAND_ITEM);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§c§lEncrypted Firewall");
		List<String> lore = new ArrayList<String>();
		lore.add("§7An encrypted firewall that");
		lore.add("§7is difficult to bypass");
		
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	
	/**
	 * Misc. Items
	 */
	
	public static ItemStack listViruses(){
		ItemStack item = new ItemStack(Material.RABBIT_HIDE);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§7§lMy Viruses");
		List<String> lore = new ArrayList<String>();
		lore.add("§7List the viruses you own");
		
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getTraceroute(){
		ItemStack item = new ItemStack(Material.WATCH);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§e§lTraceroute");
		List<String> lore = new ArrayList<String>();
		lore.add("§7Scan for nearby systems");
		
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	
	/**
	 * Shop Viruses
	 */
	
	public static ItemStack adwareVirusSHOP(){
		ItemStack item = new ItemStack(Material.RABBIT_HIDE);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§bAdware Virus §6($15 BTC)");
		List<String> lore = new ArrayList<String>();
		lore.add("§7Adware forces advertisements on the");
		lore.add("§7target, generating a lot of Bitcoin");
		
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack killdiscVirusSHOP(){
		ItemStack item = new ItemStack(Material.RABBIT_HIDE);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§bKilldisc Virus §6($10 BTC)");
		List<String> lore = new ArrayList<String>();
		lore.add("§7Use the Killdisc to completely");
		lore.add("§7erase a target Hard Drive");
		
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack iceSHOP(){
		ItemStack item = new ItemStack(Material.ICE);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§bIce §6($5 BTC)");
		List<String> lore = new ArrayList<String>();
		lore.add("§7Cools down your system, eliminating");
		lore.add("§7the wait time. One-time use.");
		
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	
	/**
	 * Viruses
	 */
	
	public static ItemStack shutdownVirus(){
		ItemStack item = new ItemStack(Material.RABBIT_HIDE);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§bSHUTDOWN.vbs");
		List<String> lore = new ArrayList<String>();
		lore.add("§7Forces the target computer to restart");
		
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack sqlVirus(){
		ItemStack item = new ItemStack(Material.RABBIT_HIDE);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§4SQL Slammer");
		List<String> lore = new ArrayList<String>();
		lore.add("§7Crashes and corrupts the target");
		
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack cryptolockerVirus(){
		ItemStack item = new ItemStack(Material.RABBIT_HIDE);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§2CryptoLocker");
		List<String> lore = new ArrayList<String>();
		lore.add("§7Holds the target computer for ransom,");
		lore.add("§7generating high EXP and some Bitcoin");
		
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack ddosVirus(){
		ItemStack item = new ItemStack(Material.RABBIT_HIDE);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§dDDoS Attack");
		List<String> lore = new ArrayList<String>();
		lore.add("§7The Distributed Denial of Service");
		lore.add("§7attack crashes the target");
		
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack adwareVirus(){
		ItemStack item = new ItemStack(Material.RABBIT_HIDE);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§aAdware");
		List<String> lore = new ArrayList<String>();
		lore.add("§7Adware forces advertisements on the");
		lore.add("§7target, generating a lot of Bitcoin");
		
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack killdiscVirus(){
		ItemStack item = new ItemStack(Material.RABBIT_HIDE);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§cKilldisc");
		List<String> lore = new ArrayList<String>();
		lore.add("§7Use the Killdisc to completely");
		lore.add("§7erase a target Hard Drive");
		
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
}
