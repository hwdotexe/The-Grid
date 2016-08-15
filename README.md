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

## Environment
Currently, this plugin demands a dedicated server environment. In other words, it overrides players' inventories, gamemode, actions, etc. It is highly recommended that you use a server that is dedicated to the game to play. Any other servers will see many errors. 1GB minimum RAM, plus room. The Grid is made to be resource-friendly, but if there are a lot of players online you may experience lag with just 1GB. Also, The Grid depends on this plugin: https://www.spigotmc.org/resources/api-particleapi-1-7-1-8-1-9.2067/

## Building
This plugin isn't fancy. I don't use Maven or anything, and cURLing won't really help much. The easiest way to compile this code is to import it into Eclipse. But hey, if you know of a better way, let me know!

## License
This plugin was made by me. You didn't help. I published this for free on GitHub, where others may download it if they so desire. As long as you give me credit, you're free to use this as you please. But by no means may you take credit for my work or attach your name to its list of authors unless you become a recognized contributor to the project.
