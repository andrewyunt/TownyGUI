package com.andrewyunt.townygui;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TownyGUI extends JavaPlugin {
	
	private PluginManager pm;
	
	@Override
	public void onEnable() {
		
		pm = this.getServer().getPluginManager();
		
		if(!this.checkDependencies()) {
			this.getServer().getLogger().warning("TownyGUI is missing one or more required dependencies.");
			this.getServer().getLogger().warning("Disabling TownyGUI...");
			pm.disablePlugin(this);
			return;
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("tgui")) {
			new InventoryGUI().openMainMenu((Player) sender);
		}
		
		return true;
	}
	
	public boolean checkDependencies() {
		if(this.getServer().getPluginManager().getPlugin("Towny") == null)
			return false;
		else
			if(!this.getServer().getPluginManager().getPlugin("Towny").isEnabled())
				return false;
		return true;
	}
}