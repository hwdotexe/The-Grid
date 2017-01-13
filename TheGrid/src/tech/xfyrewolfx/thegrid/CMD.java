package tech.xfyrewolfx.thegrid;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tech.xfyrewolfx.thegrid.listeners.AddOutlet;
import tech.xfyrewolfx.thegrid.listeners.AddSystem;

public class CMD implements CommandExecutor{
	
	TheGrid plugin;
	public CMD(TheGrid c){
		plugin=c;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("thegrid")){
			if(sender.hasPermission("thegrid.admin")){
				if(args.length==0){
					sender.sendMessage("§a===§8===§a===§8===§a===§8===§a===§8===§a===§8===");
					sender.sendMessage("§7/thegrid addSystem <name> <level>");
					sender.sendMessage("§7/thegrid addOutlet");
					sender.sendMessage("§7/thegrid setSpawn");
					sender.sendMessage("§7/thegrid setTutorial");
					sender.sendMessage("§7/thegrid reload");
					sender.sendMessage("§a===§8===§a===§8===§a===§8===§a===§8===§a===§8===");
				}else{
					if(args[0].equalsIgnoreCase("addSystem")){
						if(args.length==3){
							Bukkit.getPluginManager().registerEvents(new AddSystem(plugin, (Player)sender, args[1], Integer.parseInt(args[2])), plugin);
							sender.sendMessage("Click the new system block");
						}else{
							sender.sendMessage(plugin.getMessages().wrongCommand());
						}
					}else{
						if(args[0].equalsIgnoreCase("addOutlet")){
							Bukkit.getPluginManager().registerEvents(new AddOutlet(plugin, (Player)sender), plugin);
							sender.sendMessage("Click the TRIPWIRE HOOK to add an outlet");
						}else{
							if(args[0].equalsIgnoreCase("setSpawn")){
								Player p = (Player)sender;
								plugin.getUserConfig().setSpawnLocation(p.getLocation());
								p.sendMessage("Spawn location set!");
							}else{
								if(args[0].equalsIgnoreCase("setTutorial")){
									Player p = (Player)sender;
									plugin.getUserConfig().setTutorialLocation(p.getLocation());
									p.sendMessage("Tutorial location set!");
								}else{
									if(args[0].equalsIgnoreCase("reload")){
										plugin.getUserConfig().loadValues();
										plugin.getMessages().reloadMessages();
										plugin.getMessages().loadValues();
										sender.sendMessage("Configuration and Messages reloaded!");
									}else{
										sender.sendMessage(plugin.getMessages().wrongCommand());
									}
								}
							}
						}
					}
				}
			}else{
				sender.sendMessage(plugin.getMessages().noPermission());
			}
			return true;
		}
		return false;
	}
}
