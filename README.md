# The Grid
Welcome to The Grid - an open-world hacking adventure, filled with addicting gameplay and loads of content. In The Grid, you take on the role of an aspiring hacker, whose only goal is to grow stronger. Level up your gear by hacking other players, as well as by hacking systems scattered around the map.

## Gameplay
In The Grid, you are a freelance computer enthusiast, with (coincidentally) all the right tools for the job. Your System is your life - it is what hacks and gets hacked, as well as refuels your battery. It sits in your first hotbar slot. Second is your list of Viruses - the arsenal of code that you use to attack other systems. Viruses can generate EXP, Bitcoins (in-game only), or both. Use them wisely! Players who are Level 5 or above also have a Traceroute, which can detect nearby systems and players. Finally, your Firewall is a device that when powered on reduces the chances of getting hacked.

## Objects
+Tripwire Hook: Used as an Outlet. Right-click one with your System to recharge your battery. Outlets give off a sparking particle effect.

+CPU Systems: CPU systems are systems that server Operators can manually add to the game. These systems can only be hacked by players who meet their Firewall requirements. The greater the Firewall level, the more EXP is earned. CPU systems give off an Enchanting Table's particle effect.

## Commands
/addsystem <name> <level> - adds a CPU system to the game with the provided specifications

/removesystem <name> - corrects the error you just made with the command above

/addoutlet - adds an outlet to the system

/shop - opens an item shop where players can purchase new gear with their Bitcoins

/setspawn - change the default spawn location for players using the `/spawn` command or finishing the tutorial

/settutorial - change the default tutorial location for new players

## Tips, Tricks, and Helpful Information
Some viruses generate EXP. Some viruses generate Bitcoin. Some viruses can generate both. EXP is rewarded based on the level of the target system and virus type (stronger target and/or virus = more EXP). Bitcoin is generated randomly, but the `Adware` viruses has the highest probability and reward. Firewalls of any kind use 2x more power when turned on, which drains your battery faster. Traceroutes, outlets, and hacking are all limited to a short range and must be completed within the range to work. Ice Cubes will eliminate your cooldown wait-time, but melt after use. Cooldown wait-times are generated randomly based on your level. Players' hacking gear (System, Firewall, Virus set, Traceroute) upgrades every 10 levels and improves each time. Outlets give off a sparking particle effect every few seconds, and NPC systems an Enchantment Table effect. You cannot hack a system whose level is higher than your own, however stronger players can hack you (so watch out).

## Environment
Currently, this plugin demands a dedicated server environment. In other words, it overrides players' inventories, gamemode, actions, etc. It is highly recommended that you use a server that is dedicated to the game to play. Any other servers will see many errors. 1GB minimum RAM is recommended.

A city / town game map is recommended for game lore and purpose, however you may choose whichever map you like. For cities, I recommend `Varenburg v1.1.1` or `Imperial City`. 

## Building
Currently the best way to import this code is to download the .zip and import into Eclipse. If this project sees more popularity in the future, I might consider changing this.

## License
This plugin was made by me. You didn't help. As long as you give credit, you're free to use this project in any way you please. If you decide to use this on a production server, feel free to let me know!
