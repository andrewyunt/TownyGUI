package com.andrewyunt.townygui;

import com.andrewyunt.townygui.configuration.CommandConfiguration;
import com.andrewyunt.townygui.configuration.MenuConfiguration;
import com.andrewyunt.townygui.listeners.InventoryListener;
import com.palmergames.bukkit.towny.TownyMessaging;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class TownyGUI extends JavaPlugin implements CommandExecutor {
	
	public MenuConfiguration menuConfig;
	public CommandConfiguration commandConfig;
	
	public final Server server = getServer();
	
	private static TownyGUI instance;
	public static final boolean debug = false;
	
	@Override
	public void onEnable() {
		
		instance = this;
		
		menuConfig = new MenuConfiguration();
		commandConfig = new CommandConfiguration();
		
		commandConfig.saveDefaultConfig();
		menuConfig.saveDefaultConfig();
	
		getServer().getPluginManager().registerEvents(new InventoryListener(), this);
		Objects.requireNonNull(getServer().getPluginCommand("tgui")).setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("tgui")) {
			if (args.length == 0) {
				if (!(sender instanceof Player)) {
					System.out.println("The user interface cannot be opened from the console.");
					return true;
				} else {
					if (sender.hasPermission("towny.gui.open")) {
						new IconMenu((Player) sender, "Main");
					} else {
						TownyMessaging.sendErrorMsg(sender, "Keine Berechtigung");
					}
				}
			} else {
				if (args[0].equals("reload")) {
					reload();
					TownyMessaging.sendMessage(sender, ChatColor.AQUA + "Reloaded TownyGUI successfully.");
				}
			}
		}
		
		return true;
	}
	
	private void reload() {
		menuConfig.reloadConfig();
		commandConfig.reloadConfig();
	}

	public static TownyGUI getInstance() {
		return instance;
	}
}