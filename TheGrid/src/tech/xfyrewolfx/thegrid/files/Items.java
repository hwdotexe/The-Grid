package tech.xfyrewolfx.thegrid.files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import tech.xfyrewolfx.thegrid.TheGrid;

public class Items {
	
	private File itemsFile;
	private FileConfiguration items;
	private TheGrid plugin;
	
	public Items(TheGrid c){
		plugin=c;
		items = null;
		itemsFile = null;
		
		this.reloadItems();
		
		// Call these methods on load to generate default settings (used if the settings do not exist)
		this.laptop_1();
		this.laptop_2();
		this.laptop_3();
		this.laptop_4();
		this.firewall_1();
		this.firewall_2();
		this.firewall_3();
		this.firewall_4();
		this.my_items();
		this.traceroute();
		this.ice_cube(1);
		
		plugin.getLogger().log(Level.INFO, "Loaded items");
	}
	
	
	public ItemStack[] getBasicInventory(){
		Inventory inv = Bukkit.createInventory(null, 36);
		inv.setItem(0, this.laptop_1());
		inv.setItem(4, this.my_items());
		inv.setItem(8, this.firewall_1());
		
		return inv.getContents();
	}
	
	public ItemStack[] getBasicInventoryTraceroute(){
		Inventory inv = Bukkit.createInventory(null, 36);
		inv.setItem(0, this.laptop_1());
		inv.setItem(3, this.my_items());
		inv.setItem(5, this.traceroute());
		inv.setItem(8, this.firewall_1());
		
		return inv.getContents();
	}
	public ItemStack[] getSpecialistInventory(){
		Inventory inv = Bukkit.createInventory(null, 36);
		inv.setItem(0, this.laptop_2());
		inv.setItem(3, this.my_items());
		inv.setItem(5, this.traceroute());
		inv.setItem(8, this.firewall_2());
		
		return inv.getContents();
	}
	public ItemStack[] getEliteInventory(){
		Inventory inv = Bukkit.createInventory(null, 36);
		inv.setItem(0, this.laptop_3());
		inv.setItem(3, this.my_items());
		inv.setItem(5, this.traceroute());
		inv.setItem(8, this.firewall_3());
		
		return inv.getContents();
	}
	public ItemStack[] getProInventory(){
		Inventory inv = Bukkit.createInventory(null, 36);
		inv.setItem(0, this.laptop_4());
		inv.setItem(3, this.my_items());
		inv.setItem(5, this.traceroute());
		inv.setItem(8, this.firewall_4());
		
		return inv.getContents();
	}
	
	
	/**
	 * Computers
	 */
	public ItemStack laptop_1(){
		String entry = "laptop1";
		String defTitle = "§e§lHP Pavilion G4";
		String defLore1 = "§7A simple Laptop from 2011";
		Material defType = Material.DAYLIGHT_DETECTOR;
		List<String> lore = new ArrayList<String>();
		
		return this.getItem(entry, defTitle, defType, lore, defLore1, 1);
	}
	
	public ItemStack laptop_2(){
		String entry = "laptop2";
		String defTitle = "§c§lLinux box";
		String defLore1 = "§7A secure, upgraded laptop";
		Material defType = Material.DAYLIGHT_DETECTOR;
		List<String> lore = new ArrayList<String>();
		
		return this.getItem(entry, defTitle, defType, lore, defLore1, 1);
	}
	
	public ItemStack laptop_3(){
		String entry = "laptop3";
		String defTitle = "§a§lAlienware 15";
		String defLore1 = "§7A performance-centric powerhouse";
		Material defType = Material.DAYLIGHT_DETECTOR;
		List<String> lore = new ArrayList<String>();
		
		return this.getItem(entry, defTitle, defType, lore, defLore1, 1);
	}
	
	public ItemStack laptop_4(){
		String entry = "laptop4";
		String defTitle = "§6§lRayzer-235";
		String defLore1 = "§7Top-tier supercomputer";
		Material defType = Material.DAYLIGHT_DETECTOR_INVERTED;
		List<String> lore = new ArrayList<String>();
		
		return this.getItem(entry, defTitle, defType, lore, defLore1, 1);
	}
	
	
	/**
	 * Firewalls
	 */
	public ItemStack firewall_1(){
		String entry = "firewall1";
		String defTitle = "§e§lVirtual Firewall";
		String defLore1 = "§7A simple software-based firewall";
		Material defType = Material.TRAP_DOOR;
		List<String> lore = new ArrayList<String>();
		
		return this.getItem(entry, defTitle, defType, lore, defLore1, 1);
	}
	
	public ItemStack firewall_2(){
		String entry = "firewall2";
		String defTitle = "§c§lHardware Firewall";
		String defLore1 = "§7An upgraded firewall";
		Material defType = Material.IRON_TRAPDOOR;
		List<String> lore = new ArrayList<String>();
		
		return this.getItem(entry, defTitle, defType, lore, defLore1, 1);
	}
	
	public ItemStack firewall_3(){
		String entry = "firewall3";
		String defTitle = "§a§lAdvanced Firewall";
		String defLore1 = "§7An advanced physical firewall";
		Material defType = Material.BREWING_STAND_ITEM;
		List<String> lore = new ArrayList<String>();
		
		return this.getItem(entry, defTitle, defType, lore, defLore1, 1);
	}
	
	public ItemStack firewall_4(){
		String entry = "firewall4";
		String defTitle = "§6§lEncrypted Firewall";
		String defLore1 = "§7A highly-advanced encrypted firewall";
		Material defType = Material.NOTE_BLOCK;
		List<String> lore = new ArrayList<String>();
		
		return this.getItem(entry, defTitle, defType, lore, defLore1, 1);
	}
	
	
	/**
	 * Misc. Items
	 */
	
	public ItemStack my_items(){
		String entry = "my_items";
		String defTitle = "§8§lMy Items";
		String defLore1 = "§7List the items you own";
		Material defType = Material.RABBIT_HIDE;
		List<String> lore = new ArrayList<String>();
		
		return this.getItem(entry, defTitle, defType, lore, defLore1, 1);
	}
	
	public ItemStack traceroute(){
		String entry = "traceroute";
		String defTitle = "§e§lTraceroute";
		String defLore1 = "§7Search for nearby systems and players";
		Material defType = Material.WATCH;
		List<String> lore = new ArrayList<String>();
		
		return this.getItem(entry, defTitle, defType, lore, defLore1, 1);
	}
	
	public ItemStack ice_cube(int amt){
		String entry = "ice_cube";
		String defTitle = "§b§lIce Cube";
		String defLore1 = "§7Cools down your system immediately";
		Material defType = Material.ICE;
		List<String> lore = new ArrayList<String>();
		
		return this.getItem(entry, defTitle, defType, lore, defLore1, amt);
	}
	
	
	/**
	 * Shop Viruses
	 */
	
	public ItemStack adwareVirusSHOP(){
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
	
	public ItemStack killdiscVirusSHOP(){
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
	
	public ItemStack iceSHOP(){
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
	
	public ItemStack openSHOP(){
		ItemStack item = new ItemStack(Material.CHEST);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§b§lHackShop");
		List<String> lore = new ArrayList<String>();
		lore.add("§7Visit the shop to buy new gear,");
		lore.add("§7virues, and more.");
		
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	
	/**
	 * Viruses
	 */
	
	public ItemStack shutdownVirus(){
		ItemStack item = new ItemStack(Material.RABBIT_HIDE);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§bSHUTDOWN.vbs");
		List<String> lore = new ArrayList<String>();
		lore.add("§7Forces the target computer to restart");
		
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public ItemStack sqlVirus(){
		ItemStack item = new ItemStack(Material.RABBIT_HIDE);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§4SQL Slammer");
		List<String> lore = new ArrayList<String>();
		lore.add("§7Crashes and corrupts the target");
		
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public ItemStack cryptolockerVirus(){
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
	
	public ItemStack ddosVirus(){
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
	
	public ItemStack adwareVirus(){
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
	
	public ItemStack killdiscVirus(){
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
	
	/* Misc */
	
	private ItemStack getItem(String entry, String defTitle, Material defType, List<String> lore, String defLore1, int amt){
		ItemStack item;
		Material block;
		
		if(getItems().contains(entry)){
			block = Material.getMaterial(getItems().getString(entry+".blocktype"));
			if(block == null)
				block = defType;
				
			item = new ItemStack(block, amt);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(getItems().getString(entry+".name").replaceAll("&", "§"));
			lore = getItems().getStringList(entry+".lore");
			List<String>temp = new ArrayList<String>();
			
			for(String s : lore){
				temp.add(s.replaceAll("&", "§"));
			}
			
			im.setLore(temp);
			item.setItemMeta(im);
		}else{
			block = defType;
				
			item = new ItemStack(block, amt);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(getItems().getString(defTitle));
			lore = new ArrayList<String>();
			lore.add(defLore1);
			
			im.setLore(lore);
			item.setItemMeta(im);
			
			getItems().set(entry+".blocktype", defType.toString());
			getItems().set(entry+".name", defTitle.replaceAll("§", "&"));
			lore.set(0, lore.get(0).replaceAll("§", "&"));
			getItems().set(entry+".lore", lore);
			saveItems();
		}
		
		return item;
	}
	
	
	/* File Operations*/
	
	public void reloadItems(){
		if(this.itemsFile == null){
			this.itemsFile = new File(plugin.getDataFolder(), "items.yml");
		    this.items = YamlConfiguration.loadConfiguration(this.itemsFile);
		}
	}
		 
	private FileConfiguration getItems(){
		if(this.items == null){
			reloadItems();
		}
	   return this.items;
	}
		 
	private void saveItems(){
		if ((this.items == null) || (this.itemsFile == null)) {
			return;
		}
		try{
			getItems().save(this.itemsFile);
		}catch(Exception ex){
			plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.itemsFile, ex);
		}
	}
}
