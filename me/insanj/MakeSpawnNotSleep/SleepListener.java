/*
 Created by Julian Weiss (insanj), updates frequent on Google+ (and sometimes Twitter)!

 Please do not modify or decompile at any date, but feel free to distribute with credit.
 Production began on Tuesday, August 9th, 2011.
 Last edited on: 8/11/11

 MakeSpawnNotSleep 1.2.1!
 Special thanks to: 
 		Ross Gosling, for the idea and feature designs!

 		
 Works with the current CraftBukkit Build (#1000).
 All other information should be available at bukkit.org under MakeSpawnNotSleep.


 THIS VERSION CURRENT HAS TWO CLASSES:
			MakeSpawnNotSleep.java
			SleepListener.java

*/
package me.insanj.MakeSpawnNotSleep;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class SleepListener extends PlayerListener
{
	public static MakeSpawnNotSleep plugin;
	public static Location spawn;
	
	public SleepListener(MakeSpawnNotSleep instance){
		plugin = instance;
	}
	
	public void onPlayerJoin(PlayerJoinEvent event){
		spawn = getSpawnLocation(event.getPlayer());
	}
	
	public void onPlayerBedEnter(PlayerBedEnterEvent event){
		event.setCancelled(true);
	}
	
	@Override
	public void onPlayerInteract(PlayerInteractEvent event){
		if( event.getClickedBlock() != null && event.getClickedBlock().getType().equals(Material.BED_BLOCK) ){
			event.setCancelled(true);
			if( event.getAction() == Action.RIGHT_CLICK_BLOCK ){
				if( distance(spawn, event.getPlayer().getLocation()) > 3.5 ){
					spawn = event.getPlayer().getLocation();
					writeSpawnLocation(event.getPlayer());
					event.getPlayer().sendMessage(ChatColor.AQUA + "Your new spawn has been set!");
				}//end if
			} else{
				event.getClickedBlock().setTypeId(0);
				event.getClickedBlock().getWorld().dropItemNaturally(event.getClickedBlock().getLocation(), new ItemStack(355, 1));
			}

		}//end if
	}//end onPlayerInteract()
	
	public void onPlayerRespawn(PlayerRespawnEvent event){
		event.setRespawnLocation(spawn);
	}

	double distance(Location loc1, Location loc2) {
		return Math.sqrt( Math.pow(loc2.getX() - loc1.getX(), 2) + Math.pow(loc2.getY() - loc1.getY(), 2) + Math.pow(loc2.getZ() - loc1.getZ(), 2) );
		
	}
	
	public Location getSpawnLocation(Player player){
		try{
			Scanner outdoors = new Scanner(new File("plugins/MakeSpawnNotSleep/spawns.txt"));
			String location = "";
			
			while( outdoors.hasNextLine() ){
				String next = outdoors.nextLine();
				if( next.contains(player.getDisplayName() + ": ") )
					location = next.substring(player.getDisplayName().length() + 2);
			}//end while	
			
			String[] contents = location.split(", ");
			return new Location( player.getServer().getWorld(contents[0]), Double.parseDouble(contents[1]), Double.parseDouble(contents[2]), Double.parseDouble(contents[3]) );

		} catch(Exception e){
			return player.getWorld().getSpawnLocation();
		}
	}//end getSpawnLocation
	
	public void writeSpawnLocation(Player player){
	
		try{
			if( !(new File("plugins/MakeSpawnNotSleep/spawns.txt").exists()) ){
				new File("plugins/MakeSpawnNotSleep").mkdir();
			}
		
			File file = new File("plugins/MakeSpawnNotSleep/spawns.txt");
			FileWriter output = new FileWriter(file, false);
			
			Scanner outdoors = new Scanner(new File("plugins/MakeSpawnNotSleep/spawns.txt"));
			ArrayList<String> contents = new ArrayList<String>();
			
			while( outdoors.hasNextLine() )
				contents.add( outdoors.nextLine() );
			
			for(int i = 0; i < contents.size(); i++)
				if(contents.get(i).contains(player.getDisplayName() + ": "))
					contents.remove(i);
		
			contents.add(player.getDisplayName() + ": " + player.getWorld().getName() + ", " + player.getLocation().getX() + ", " + player.getLocation().getY() + ", " + player.getLocation().getZ() + "\n");

			for(int i = 0; i < contents.size(); i++)
				output.write(contents.get(i));
			
			output.close();

		}catch(Exception e){
			System.out.println("{MakeSpawnNotSleep} could not save spawn location for " + player.getDisplayName() + ": " );
			e.printStackTrace();
		}
	}//end writeSpawnLocation()
	
}//end class SleepListener
