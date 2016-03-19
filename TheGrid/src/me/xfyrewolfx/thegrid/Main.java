package me.xfyrewolfx.thegrid;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.xfyrewolfx.thegrid.apis.ScoreboardAPI;
import me.xfyrewolfx.thegrid.statics.Items;
import me.xfyrewolfx.thegrid.statics.MSG;
import me.xfyrewolfx.thegrid.timers.OutletTick;
import me.xfyrewolfx.thegrid.timers.SystemTick;
import me.xfyrewolfx.thegrid.timers.Tips;

public class Main extends JavaPlugin{

	private FileConfiguration systems;
	private File systemsFile;
	private FileConfiguration outlets;
	private File outletsFile;
	public List<String> cooldownList;
	
	public HashMap<Location, Integer> sys;
	public List<Location> out;
	
	String motd;
	String scoreboardObj;
	
	public void onEnable(){
	    this.systems = null;
	    this.systemsFile = null;
	    this.outlets = null;
	    this.outletsFile = null;
	    cooldownList = new ArrayList<String>();
	    sys = new HashMap<Location, Integer>();
	    out = new ArrayList<Location>();
	    
	    scoreboardObj = "{§a§lTheGrid§f}";
	    
	    reloadsystems();
	    reloadoutlets();
	    
	    if(this.getConfig().contains("motd")){
			motd=this.getConfig().getString("motd").replaceAll("&", "§");
		}else{
			this.getConfig().set("motd", "       &f-- &a&kii&8&l [&2&lTheGrid&8&l] &a&kii&f --");
			this.saveConfig();
			motd="       §f-- §a§kii§8§l [§2§lTheGrid§8§l] §a§kii§f --";
		}
	    
	    for(String n : this.getoutlets().getStringList("outlets")){
			if(n != null && n != ""){
				int x = this.getoutlets().getInt(n+".x");
				int y = this.getoutlets().getInt(n+".y");
				int z = this.getoutlets().getInt(n+".z");
				Location l = new Location(Bukkit.getWorld("world"),x,y,z);
				out.add(l);
			}
		}
	    
	    for(String n : this.getsystems().getStringList("systems")){
			if(n != null && n != ""){
				int x = this.getsystems().getInt(n+".x");
				int y = this.getsystems().getInt(n+".y");
				int z = this.getsystems().getInt(n+".z");
				int level = this.getsystems().getInt(n+".level");
				Location l = new Location(Bukkit.getWorld("world"),x,y,z);
				sys.put(l, level);
			}
		}
	    
		this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		new OutletTick(this).runTaskTimer(this, 100, 100);
		new SystemTick(this).runTaskTimer(this, 100, 60);
		new Tips().runTaskTimer(this, 200, 8600);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("spawn")){
			Player p = (Player)sender;
			if(!p.hasMetadata("tutorial")){
				p.teleport(new Location(p.getWorld(),-402.5, 21, 771.5));
			}
			
			return true;
		}
		
		if(cmd.getName().equalsIgnoreCase("addsystem")){
			if(sender.isOp()){
				if(args.length==2){
					Player p = (Player)sender;
					Bukkit.getPluginManager().registerEvents(new AddSystem(this,p, args[0].toLowerCase(), Integer.parseInt(args[1])), this);
					p.sendMessage("Right click the block to use as a system");
				}else{
					sender.sendMessage("/addsystem <name> <firewallLevel>");
				}
			}
			return true;
		}
		
		if(cmd.getName().equalsIgnoreCase("shop")){
			Bukkit.getPluginManager().registerEvents(new ShopGUI((Player)sender, this), this);
			return true;
		}
		
		if(cmd.getName().equalsIgnoreCase("?") || cmd.getName().equalsIgnoreCase("help") || cmd.getName().equalsIgnoreCase("pl") || cmd.getName().equalsIgnoreCase("plugins")){
			Player p = (Player)sender;
			p.sendMessage("§7----------{ §a§ki§a §nt§ohE G§mr§aid §ki§7 }----------");
			p.sendMessage("§2§lThe Grid §7is an open-world Hacking game.");
			p.sendMessage("§8§l[?] Hacking");
			
			String name = "System";
			if(p.getInventory().getItem(0).hasItemMeta()){
				name = p.getInventory().getItem(0).getItemMeta().getDisplayName();
			}
			p.sendMessage("   §7Right-click a Player or System with your "+name);
			p.sendMessage("§8§l[?] Leveling Up");
			p.sendMessage("   §7Gain §3EXP §7from hacking others");
			p.sendMessage("§8§l[?] Charging the Battery");
			p.sendMessage("   §7Find an §6Outlet §7around town and right-click");
			p.sendMessage("§8§l[?] Preventing Hacks");
			p.sendMessage("   §7Enable your §6Firewall §7to slow/prevent attacks");
			p.sendMessage("§8§l[?] Buying Items");
			p.sendMessage("   §7Use §6/shop §7to buy new gear");
			p.sendMessage("§7----------{ §8§lEND §7}----------");
		}
		
		if(cmd.getName().equalsIgnoreCase("removesystem")){
			if(sender.isOp()){
				if(args.length==1){
					String name = args[0].toLowerCase();
					if(this.getsystems().contains(name)){
						int x = this.getsystems().getInt(name+".x");
						int y = this.getsystems().getInt(name+".y");
						int z = this.getsystems().getInt(name+".z");
						Location l = new Location(Bukkit.getWorld("world"),x,y,z);
						this.sys.remove(l);
						
						List<String> s = this.getsystems().getStringList("systems");
						s.remove(name);
						this.getsystems().set("systems", s);
						this.getsystems().set(name, null);
						this.savesystems();
						
						sender.sendMessage(name+" was removed ("+l.getBlockX()+" "+l.getBlockY()+" "+l.getBlockZ()+")");
					}else{
						sender.sendMessage("Could not find that system! Check systems.yml");
					}
				}else{
					sender.sendMessage("/removesystem <name>");
				}
			}
			return true;
		}
		
		if(cmd.getName().equalsIgnoreCase("addoutlet")){
			if(sender.isOp()){
				if(args.length==0){
					Player p = (Player)sender;
					Bukkit.getPluginManager().registerEvents(new AddOutlet(this,p), this);
					p.sendMessage("Right click the TRIPWIRE_HOOK to use");
				}
			}
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * Hacking Methods
	 */
	
	public void hackCPU(Location l, Player p){
		if(l != null){
			if(l.getBlock().getType() != Material.AIR || l.getBlock().getType() != Material.WATER){
				List<String> sys = this.getsystems().getStringList("systems");
				
				String system = "";
				for(String n : sys){
					if(this.getsystems().getInt(n+".x")==l.getBlockX() && this.getsystems().getInt(n+".y")==l.getBlockY() && this.getsystems().getInt(n+".z")==l.getBlockZ()){
						system=n;
					}
				}
				
				int cpulevel = this.getsystems().getInt(system+".level");
				int plevel = this.getPlayerLevel(p.getName());
				int pbattery = p.getLevel();
				
				p.sendMessage("§a~$: connected to "+system+" ("+cpulevel+")");
				p.playSound(p.getLocation(), Sound.ENTITY_CREEPER_HURT, 5, 1);
				
				if(pbattery > 0){
					if(plevel >= cpulevel){
						Bukkit.getPluginManager().registerEvents(new VirusesGUI(p, this, true, null, l, cpulevel), this);
					}else{
						p.sendMessage(MSG.targetTooStrong(cpulevel));
					}
				}else{
					p.sendMessage(MSG.batteryDepleted());
				}
			}
		}
	}
	
	public void hackPlayer(Player p, Player t){
		int tlevel = this.getPlayerLevel(t.getName());
		int plevel = this.getPlayerLevel(p.getName());
		int pbattery = p.getLevel();
		
		p.sendMessage("§a~$: connected to "+t.getName()+" ("+tlevel+")");
		p.playSound(p.getLocation(), Sound.ENTITY_CREEPER_HURT, 5, 1);
		
		if(pbattery > 0){
			if(plevel >= tlevel){
				Bukkit.getPluginManager().registerEvents(new VirusesGUI(p, this, true, t, t.getLocation(), tlevel), this);
			}else{
				if(!this.isFirewallActive(t.getName())){
					Bukkit.getPluginManager().registerEvents(new VirusesGUI(p, this, true, t, t.getLocation(), tlevel), this);
				}else{
					p.sendMessage(MSG.targetTooStrong(tlevel));
				}
			}
		}else{
			p.sendMessage(MSG.batteryDepleted());
		}
	}
	
	
	/**
	 * Central Methods
	 */
	
	public void giveNewScoreboard(Player p){
		ScoreboardAPI.giveScoreboard(p, scoreboardObj);
		
		ScoreboardAPI.setScore(p, "EXP", getPlayerExp(p.getName()));
		
		int lvl = getPlayerLevel(p.getName());
		int ltn = 0;
		
		if(lvl < 10){
			ltn = 10-lvl;
		}
		if(lvl < 20 && lvl >= 10){
			ltn = 20-lvl;
		}
		if(lvl < 30 && lvl >= 20){
			ltn = 30-lvl;
		}
		if(lvl >= 40){
			ltn = 0;
		}
		
		ScoreboardAPI.setScore(p, "Level", lvl);
		ScoreboardAPI.setScore(p, "ToNextUpgrade", ltn);
		ScoreboardAPI.setScore(p, "Bitcoins", this.getPlayerBitcoin(p.getName()));
	}
	
	public void updateScoreboard(Player p){
		ScoreboardAPI.setScore(p, "EXP", getPlayerExp(p.getName()));
		
		int lvl = getPlayerLevel(p.getName());
		int ltn = 0;
		
		if(lvl < 10){
			ltn = 10-lvl;
		}
		if(lvl < 20 && lvl >= 10){
			ltn = 20-lvl;
		}
		if(lvl < 30 && lvl >= 20){
			ltn = 30-lvl;
		}
		if(lvl >= 40){
			ltn = 0;
		}
		
		ScoreboardAPI.setScore(p, "Level", lvl);
		ScoreboardAPI.setScore(p, "ToNextUpgrade", ltn);
		ScoreboardAPI.setScore(p, "Bitcoins", this.getPlayerBitcoin(p.getName()));
	}
	
	public void createNewPlayerEntries(String name){
		if(Bukkit.getPlayer(name)!=null){
			String UUID = Bukkit.getPlayer(name).getUniqueId().toString();
			this.getConfig().set(UUID+".level", 1);
			this.getConfig().set(UUID+".exp", 0);
			this.getConfig().set(UUID+".bitcoin", 5);
			this.getConfig().set(UUID+".battery", 12);
			List<String> v = new ArrayList<String>();
			v.add("shutdown");
			this.getConfig().set(UUID+".viruses", v);
			this.saveConfig();
		}
	}
	
	public int generateEXP(int firewall, int vexp){ //vexp is EXP based on virus used. multiplier.
		float i1 = (firewall * 30)/2;
		int i2 = Math.round(i1);
		int exp = i2 * vexp;
		return exp;
	}
	
	public boolean isBlockCPU(Location l){
		if(l != null){
			if(l.getBlock().getType() != Material.AIR || l.getBlock().getType() != Material.WATER){
				List<String> sys = this.getsystems().getStringList("systems");
				for(String n : sys){
					if(this.getsystems().getInt(n+".x")==l.getBlockX() && this.getsystems().getInt(n+".y")==l.getBlockY() && this.getsystems().getInt(n+".z")==l.getBlockZ()){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean isBlockOutlet(Location l){
		if(l != null){
			if(l.getBlock().getType() == Material.TRIPWIRE_HOOK){
				List<String> sys = this.getoutlets().getStringList("outlets");
				for(String n : sys){
					if(this.getoutlets().getInt(n+".x")==l.getBlockX() && this.getoutlets().getInt(n+".y")==l.getBlockY() && this.getoutlets().getInt(n+".z")==l.getBlockZ()){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void addExp(String name, int xp){
		if(Bukkit.getPlayer(name)!=null){
			Player p = Bukkit.getPlayer(name);
			int exp = this.getPlayerExp(name);
			int lvl = this.getPlayerLevel(name);
			int maxexp = lvl*100;
			
			if(exp+xp >= maxexp){
				//LEVEL UP
				this.setPlayerLevel(name, lvl+1);
				this.setPlayerExp(name, (exp+xp)-maxexp);
				p.sendMessage(MSG.levelUp());
				
				lvl=lvl+1;
				
				if(lvl == 5){
					p.sendMessage("      §7> Unlocked §6Traceroute");
					p.getInventory().setContents(this.getPlayerInventory(name));
				}else{
					if(lvl == 10){
						MSG.tierUnlocked(name, "Specialist");
						List<String> v = this.getPlayerViruses(name);
						v.add("sql");
						v.add("cryptolocker");
						this.setPlayerViruses(name, v);
						this.setPlayerBattery(name, 24);
						p.getInventory().setContents(this.getPlayerInventory(name));
					}else{
						if(lvl == 20 ){
							MSG.tierUnlocked(name, "Elite");
							this.setPlayerBattery(name, 48);
							p.getInventory().setContents(this.getPlayerInventory(name));
						}else{
							if(lvl == 30){
								MSG.tierUnlocked(name, "Pro");
								List<String> v = this.getPlayerViruses(name);
								v.add("ddos");
								this.setPlayerViruses(name, v);
								this.setPlayerBattery(name, 72);
								p.getInventory().setContents(this.getPlayerInventory(name));
							}else{
								if(lvl == 40){
									//Surpassed current tiers
									p.sendMessage("§8[ §2! §8] §7You have surpassed all current tiers!");
									/** FUTURE CONTENT POTENTIAL (unlock Master tier & unlock battles/NPC for master only) **/
								}
							}
						}
					}
				}
			}else{
				//NO LEVEL UP
				this.setPlayerExp(name, exp+xp);
			}
			
			this.updateScoreboard(p);
		}
	}
	
	public boolean isFirewallActive(String name){
		if(Bukkit.getPlayer(name)!=null){
			Player p = Bukkit.getPlayer(name);
			
			if(p.getInventory().getItem(8).containsEnchantment(Enchantment.DURABILITY)){
				return true;
			}
		}
		
		return false;
	}
	
	//Get
	public int getPlayerLevel(String name){
		if(Bukkit.getPlayer(name)!=null){
			String UUID = Bukkit.getPlayer(name).getUniqueId().toString();
			return this.getConfig().getInt(UUID+".level");
		}
		return 0;
	}
	public int getPlayerExp(String name){
		if(Bukkit.getPlayer(name)!=null){
			String UUID = Bukkit.getPlayer(name).getUniqueId().toString();
			return this.getConfig().getInt(UUID+".exp");
		}
		return 0;
	}
	public int getPlayerBitcoin(String name){
		if(Bukkit.getPlayer(name)!=null){
			String UUID = Bukkit.getPlayer(name).getUniqueId().toString();
			return this.getConfig().getInt(UUID+".bitcoin");
		}
		return 0;
	}
	public int getPlayerBattery(String name){
		if(Bukkit.getPlayer(name)!=null){
			String UUID = Bukkit.getPlayer(name).getUniqueId().toString();
			return this.getConfig().getInt(UUID+".battery");
		}
		return 0;
	}
	public List<String> getPlayerViruses(String name){
		if(Bukkit.getPlayer(name)!=null){
			String UUID = Bukkit.getPlayer(name).getUniqueId().toString();
			return this.getConfig().getStringList(UUID+".viruses");
		}
		return null;
	}
	
	/** POTENTIAL CONTENT AREA **/
	public ItemStack[] getPlayerInventory(String name){
		if(Bukkit.getPlayer(name)!=null){
			Inventory inv = Bukkit.createInventory(null, 36);
			int lvl = this.getPlayerLevel(name);
			
			if(lvl < 5){
				inv.setContents(Items.getBasicInventory());
			}
			if(lvl > 4 && lvl < 10){
				inv.setContents(Items.getBasicInventoryTraceroute());
			}
			if(lvl > 9 && lvl < 20){
				inv.setContents(Items.getSpecialistInventory());
			}
			if(lvl > 19 && lvl < 30){
				inv.setContents(Items.getEliteInventory());
			}
			if(lvl > 29){
				inv.setContents(Items.getProInventory());
			}
			
			return inv.getContents();
		}
		return null;
	}
	
	//Set
	public void setPlayerLevel(String name, int level){
		if(Bukkit.getPlayer(name)!=null){
			String UUID = Bukkit.getPlayer(name).getUniqueId().toString();
			this.getConfig().set(UUID+".level", level);
			this.saveConfig();
		}
	}
	public void setPlayerExp(String name, int exp){
		if(Bukkit.getPlayer(name)!=null){
			String UUID = Bukkit.getPlayer(name).getUniqueId().toString();
			this.getConfig().set(UUID+".exp", exp);
			this.saveConfig();
		}
	}
	public void setPlayerBitcoins(String name, int btc){
		if(Bukkit.getPlayer(name)!=null){
			String UUID = Bukkit.getPlayer(name).getUniqueId().toString();
			this.getConfig().set(UUID+".bitcoin", btc);
			this.saveConfig();
		}
	}
	public void setPlayerBattery(String name, int bat){
		if(Bukkit.getPlayer(name)!=null){
			String UUID = Bukkit.getPlayer(name).getUniqueId().toString();
			this.getConfig().set(UUID+".battery", bat);
			this.saveConfig();
		}
	}
	public void setPlayerViruses(String name, List<String> viruses){
		if(Bukkit.getPlayer(name)!=null){
			String UUID = Bukkit.getPlayer(name).getUniqueId().toString();
			this.getConfig().set(UUID+".viruses", viruses);
			this.saveConfig();
		}
	}
	
	
	/**
	 * Config methods
	 */
	
	//systems
	  public void reloadsystems()
		 {
		   if (this.systemsFile == null)
		   {
		     this.systemsFile = new File(getDataFolder(), "systems.yml");
		     this.systems = YamlConfiguration.loadConfiguration(this.systemsFile);
		   }
		 }
		 
		 public FileConfiguration getsystems()
		 {
		   if (this.systems == null) {
		     reloadsystems();
		   }
		   return this.systems;
		 }
		 
		 public void savesystems()
		 {
		   if ((this.systems == null) || (this.systemsFile == null)) {
		     return;
		   }
		   try
		   {
		     getsystems().save(this.systemsFile);
		   }
		   catch (Exception ex)
		   {
		     getLogger().log(Level.SEVERE, "Could not save config to " + this.systemsFile, ex);
		   }
		 }
		 
	//outlets
		  public void reloadoutlets()
			 {
			   if (this.outletsFile == null)
			   {
			     this.outletsFile = new File(getDataFolder(), "outlets.yml");
			     this.outlets = YamlConfiguration.loadConfiguration(this.outletsFile);
			   }
			 }
			 
			 public FileConfiguration getoutlets()
			 {
			   if (this.outlets == null) {
			     reloadoutlets();
			   }
			   return this.outlets;
			 }
			 
			 public void saveoutlets()
			 {
			   if ((this.outlets == null) || (this.outletsFile == null)) {
			     return;
			   }
			   try
			   {
			     getoutlets().save(this.outletsFile);
			   }
			   catch (Exception ex)
			   {
			     getLogger().log(Level.SEVERE, "Could not save config to " + this.outletsFile, ex);
			   }
			 }
}