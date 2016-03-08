package com.andrewyunt.townygui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InventoryGUI {
	
	protected void openMainMenu(Player player) {
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLACK + "Main Menu");
		
		player.openInventory(inv);
	}
	
	protected void openResidentMenu(Player player) {
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLACK + "Main Menu");
		
		player.openInventory(inv);
	}
	
	protected void openTownMenu(Player player) {
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLACK + "Main Menu");
		
		player.openInventory(inv);
	}
	
	protected void openNationMenu(Player player) {
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLACK + "Main Menu");
		
		player.openInventory(inv);
	}
	
	protected void openPlotMenu(Player player) {
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLACK + "Main Menu");
		
		player.openInventory(inv);
	}
	
	protected void openTownyMenu(Player player) {
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLACK + "Main Menu");
		
		player.openInventory(inv);
	}
	
	protected void openTownyAdminMenu(Player player) {
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLACK + "Main Menu");
		
		player.openInventory(inv);
	}
}