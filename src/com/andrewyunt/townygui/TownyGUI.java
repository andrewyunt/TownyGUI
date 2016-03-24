package com.andrewyunt.townygui;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.andrewyunt.townygui.configuration.CommandConfiguration;
import com.andrewyunt.townygui.configuration.MenuConfiguration;
import com.andrewyunt.townygui.listeners.InventoryListener;
import com.palmergames.bukkit.towny.TownyMessaging;

public class TownyGUI extends JavaPlugin {
	
	public static TownyGUI plugin;
	
	public Utils utils;
	
	public MenuConfiguration menuConfig;
	public CommandConfiguration commandConfig;
	
	private PluginManager pm;
	
	@Override
	public void onEnable() {
		
		plugin = this;
		
		menuConfig = new MenuConfiguration();
		commandConfig = new CommandConfiguration();
		utils = new Utils();
		
		pm = getServer().getPluginManager();
		
		if(!checkDependencies()) {
			getServer().getLogger().warning("TownyGUI is missing one or more required dependencies.");
			getServer().getLogger().warning("Disabling TownyGUI...");
			pm.disablePlugin(this);
			return;
		}
		
		saveDefaultConfig();
		
		commandConfig.saveDefaultConfig();
		menuConfig.saveDefaultConfig();
	
		pm.registerEvents(new InventoryListener(), this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("tgui")) {
			
			if(args.length == 0)
				if(!(sender instanceof Player)) {
					System.out.println("The user interface cannot be opened from the console.");
					return true;
				} else
					new Menu((Player) sender, this.getConfig().getString("main_menu"));
			else if(args.length > 0)
				if(args[0].equals("reload")) {
					reload();
					TownyMessaging.sendMessage(sender, ChatColor.AQUA + "Reloaded TownyGUI successfully.");
				}
		}
		return true;
	}
	
	public boolean checkDependencies() {
		
		if(getServer().getPluginManager().getPlugin("Towny") == null)
			return false;
		else
			if(!getServer().getPluginManager().getPlugin("Towny").isEnabled())
				return false;
		return true;
	}
	
	public void reload() {
		
		reloadConfig();
		
		menuConfig.reloadConfig();
		commandConfig.reloadConfig();
	}
}