package com.andrewyunt.townygui;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.andrewyunt.townygui.configuration.CommandConfiguration;
import com.andrewyunt.townygui.configuration.MenuConfiguration;
import com.andrewyunt.townygui.listeners.InventoryListener;
import com.andrewyunt.townygui.utilities.Utils;
import com.palmergames.bukkit.towny.TownyMessaging;

public class TownyGUI extends JavaPlugin {
	
	public static TownyGUI plugin;
	
	public Utils utils;
	
	public MenuConfiguration menuConfig;
	public CommandConfiguration commandConfig;
	
	private PluginManager pm;
	
	public final Server server = getServer();
	private final Logger logger = server.getLogger();
	
	@Override
	public void onEnable() {
		
		plugin = this;
		
		menuConfig = new MenuConfiguration();
		commandConfig = new CommandConfiguration();
		utils = new Utils();
		
		pm = server.getPluginManager();
		
		if(!checkDependencies()) {
			logger.warning("TownyGUI is missing one or more required dependencies.");
			logger.warning("Disabling TownyGUI...");
			pm.disablePlugin(this);
			return;
		}
		
		commandConfig.saveDefaultConfig();
		menuConfig.saveDefaultConfig();
	
		pm.registerEvents(new InventoryListener(), this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("tgui"))
			if(args.length == 0)
				if(!(sender instanceof Player)) {
					System.out.println("The user interface cannot be opened from the console.");
					return true;
				} else
					if(sender.hasPermission("towny.gui.open"))
						new Menu((Player) sender, "Main");
					else
						TownyMessaging.sendErrorMsg(sender, "");
			else if(args.length > 0)
				if(args[0].equals("reload")) {
					reload();
					TownyMessaging.sendMessage(sender, ChatColor.AQUA + "Reloaded TownyGUI successfully.");
				}
		return true;
	}
	
	private boolean checkDependencies() {
		
		if(pm.getPlugin("Towny") == null)
			return false;
		else
			if(!pm.getPlugin("Towny").isEnabled())
				return false;
		return true;
	}
	
	private void reload() {
		
		menuConfig.reloadConfig();
		commandConfig.reloadConfig();
	}
}