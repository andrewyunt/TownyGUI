package com.andrewyunt.townygui;

import com.andrewyunt.townygui.utilities.Utils;
import com.palmergames.bukkit.towny.TownyMessaging;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Set;

public class Menu {
	
	private Set<String> icons;
	
	private FileConfiguration commandConfig;
	private FileConfiguration menuConfig;
	private FileConfiguration config;
	
	private Utils utils;
	
	private String title;
	private int size;
	
	public Menu(Player player, String menu) {
		
		commandConfig = TownyGUI.plugin.commandConfig.getConfig();
		menuConfig = TownyGUI.plugin.menuConfig.getConfig();
		
		utils = TownyGUI.plugin.utils;
		
		icons = menuConfig.getConfigurationSection("menus." + menu + ".icons").getKeys(false); 
		
		title = ChatColor.translateAlternateColorCodes('&', menuConfig.getString("menus." + menu + ".title"));
		size = TownyGUI.plugin.utils.getInventorySize(menuConfig.getInt("menus." + menu + ".size"));
		
		openMenu(player, menu);
	}
	
	private void openMenu(Player player, String menu) {
		
		Inventory inv = Bukkit.createInventory(null, size, title);
		
		for(String icon : icons) {
			
			ItemStack is = null;
			
			String type;
			
			String permission = null;
			
			if(icon.startsWith("/")) {
				config = commandConfig;
				type = "commands";
			} else {
				config = menuConfig;
				type = "menus";
			}
			
			permission = config.getString(type + "." + icon + ".permission");
			
			if(!(permission == null))
				if(!player.hasPermission(permission))
					continue;
			
			try {
				is = new ItemStack(Material.getMaterial(config.getString(type + "." + icon + ".material")), 1, (short) config.getInt(type + "." + icon + ".data"));
			} catch(NullPointerException e) {
				TownyMessaging.sendErrorMsg(player, "The material for the icon " + icon + " is invalid. OR there is an error in the configuration.");
				TownyMessaging.sendErrorMsg(player, e.getMessage());
				TownyMessaging.sendErrorMsg(player, "Please report this error to your server administrator.");
			}
			
			ItemMeta meta = is.getItemMeta();
			
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString(type + "." + icon + ".title")));
			meta.setLore(utils.hideStringInLore(utils.colorizeStringList(config.getStringList(type + "." + icon + ".lore")), icon));
			
			is.setItemMeta(meta);
			
			if(config.getBoolean(type + "." + icon + ".enchant_glow"))
				is = TownyGUI.plugin.utils.addGlow(is);
			
			try {
				inv.setItem(menuConfig.getInt("menus." + menu + ".icons." + icon + ".slot") - 1, is);
			} catch(ArrayIndexOutOfBoundsException e) {
				TownyMessaging.sendErrorMsg(player, "Icon " + icon + " is configured in a slot outside of the menu. " + e.getMessage());
				TownyMessaging.sendErrorMsg(player, "Please report this error to your server administrator.");
			}
		}
		
		player.openInventory(inv);
	}
}