/*
 Created by Julian Weiss (insanj), updates frequent on Google+ (and sometimes Twitter)!

 Please do not modify or decompile at any date, but feel free to distribute with credit.
 Production began on Tuesday, August 9th, 2011.
 Last edited on: 8/10/11

 MakeSpawnNotSleep 1.1!
 Special thanks to: 
 		Ross Gosling, for the idea and feature designs!

 		
 Works with the current CraftBukkit Build (#1000).
 All other information should be available at bukkit.org under MakeSpawnNotSleep.


 THIS VERSION CURRENT HAS TWO CLASSES:
			MakeSpawnNotSleep.java
			SleepListener.java

*/

package me.insanj.MakeSpawnNotSleep;

import org.bukkit.plugin.PluginManager;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public class MakeSpawnNotSleep extends JavaPlugin
{
	private static final Logger log = Logger.getLogger("Minecraft");
	private final SleepListener bedListener = new SleepListener(this);
	private static final String version = "1.1";
		
	@Override
	public void onEnable(){		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_BED_ENTER, bedListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT, bedListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_RESPAWN, bedListener, Event.Priority.Normal, this);
		
		log.info("{MakeSpawnNotSleep} plugin version " + version + " has successfully started.");
	}//end method onEnable()
	
	@Override
	public void onDisable() {
		log.info("{MakeSpawnNotSleep} plugin version " + version + " disabled.");
	}//end method onDisable()
	
}//end class MakeSpawnNotSleep


/***********************************Contents of "plugin.yml":*******************************
name: MakeSpawnNotSleep
version: 1.0
author: insanj
main: me.insanj.MakeSpawnNotSleep.MakeSpawnNotSleep
description: No more sleeping, much more spawn setting!
******************************************************************************************/