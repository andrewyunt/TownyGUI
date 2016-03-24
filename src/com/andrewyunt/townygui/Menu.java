package com.andrewyunt.townygui;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.andrewyunt.townygui.utilities.Utils;
import com.palmergames.bukkit.towny.TownyMessaging;

public class Menu {
	
	private Set<String> icons;
	
	FileConfiguration config;
	
	Utils utils;
	
	String title;
	int size;
	
	public Menu(Player player, String menu) {
		
		utils = TownyGUI.plugin.utils;
		
		icons = TownyGUI.plugin.menuConfig.getConfig().getConfigurationSection("menus." + menu + ".icons").getKeys(false); 
		
		title = ChatColor.translateAlternateColorCodes('&', TownyGUI.plugin.menuConfig.getConfig().getString("menus." + menu + ".title"));
		size = TownyGUI.plugin.utils.getInventorySize(TownyGUI.plugin.menuConfig.getConfig().getInt("menus." + menu + ".size"));
		
		openMenu(player, menu);
	}
	
	private void openMenu(Player player, String menu) {
		
		Inventory inv = Bukkit.createInventory(null, size, title);
		
		for(String icon : icons) {
			
			String type;
			
			if(icon.startsWith("/")) {
				config = TownyGUI.plugin.commandConfig.getConfig();
				type = "commands";
			} else {
				config = TownyGUI.plugin.menuConfig.getConfig();
				type = "menus";
			}
				
			ItemStack is = new ItemStack(Material.getMaterial(config.getString(type + "." + icon + ".material")));
			ItemMeta meta = is.getItemMeta();
			
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString(type + "." + icon + ".title")));
			meta.setLore(utils.hideStringInLore(utils.colorizeStringList(config.getStringList(type + "." + icon + ".lore")), icon));
			
			is.setItemMeta(meta);
			
			try {
				inv.setItem(TownyGUI.plugin.menuConfig.getConfig().getInt("menus." + menu + ".icons." + icon + ".slot") - 1, is);
			} catch(ArrayIndexOutOfBoundsException e) {
				TownyMessaging.sendErrorMsg(player, "Icon " + icon + " is configured in a slot outside of the menu. " + e);
				TownyMessaging.sendErrorMsg(player, "Please report this error to your server administrator.");
			}
		}
		
		player.openInventory(inv);
	}
}