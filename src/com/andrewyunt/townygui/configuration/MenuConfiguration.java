package com.andrewyunt.townygui.configuration;

import com.andrewyunt.townygui.TownyGUI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class MenuConfiguration {
	
	private FileConfiguration config = null;
	private File configFile = null;
	
	public void reloadConfig() {
		
		if (configFile == null)
			configFile = new File(TownyGUI.getInstance().getDataFolder(), "menus.yml");
		
		config = YamlConfiguration.loadConfiguration(configFile);
		
		Reader defConfigStream = null;
		TownyGUI townyGUI = TownyGUI.getInstance();
		defConfigStream = new InputStreamReader(townyGUI.getResource("menus.yml"), StandardCharsets.UTF_8);

		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			config.setDefaults(defConfig);
		}
	}
	
	public FileConfiguration getConfig() {
		
		if (config == null)
			reloadConfig();
		
		return config;
	}
	
	public void saveConfig() {
		
		if (config == null || configFile == null)
			return;
		
		try {
			getConfig().save(configFile);
		} catch(IOException ex) {
			TownyGUI.getInstance().getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
		}
	}
	
	public void saveDefaultConfig() {
		
		if (configFile == null)
			configFile = new File(TownyGUI.getInstance().getDataFolder(), "menus.yml");
		
		if (!configFile.exists())
			TownyGUI.getInstance().saveResource("menus.yml", false);
	}
}