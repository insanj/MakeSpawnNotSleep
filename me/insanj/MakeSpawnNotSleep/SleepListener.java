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

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class SleepListener extends PlayerListener
{
	MakeSpawnNotSleep plugin;
	Location spawn;
	
	public SleepListener(MakeSpawnNotSleep instance){
		plugin = instance;	
	}
	
	public void onPlayerBedEnter(PlayerBedEnterEvent event){
		event.setCancelled(true);
	}
	
	@Override
	public void onPlayerInteract(PlayerInteractEvent event){
		if( event.getClickedBlock() != null && event.getClickedBlock().getType().equals(Material.BED_BLOCK) ){
			if( (spawn == null) || (spawn != null && !spawn.equals(event.getPlayer().getLocation())) ){
				spawn = event.getPlayer().getLocation();
				event.getPlayer().sendMessage(ChatColor.AQUA + "Your new spawn has been set!");
			}
		}//end if
	}//end onPlayerInteract()
	
	public void onPlayerRespawn(PlayerRespawnEvent event){
		if(spawn != null)
			event.setRespawnLocation(spawn);
	}
	
}//end class SleepListener
