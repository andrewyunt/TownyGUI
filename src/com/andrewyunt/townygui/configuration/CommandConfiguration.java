package com.andrewyunt.townygui.configuration;

import com.andrewyunt.townygui.TownyGUI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.logging.Level;

public class CommandConfiguration {
	
	private FileConfiguration config = null;
	private File configFile = null;
	
	public void reloadConfig() {
		
		if (configFile == null)
			configFile = new File(TownyGUI.getInstance().getDataFolder(), "commands.yml");
		
		config = YamlConfiguration.loadConfiguration(configFile);
		
		Reader defConfigStream = null;
		
		try {
			defConfigStream = new InputStreamReader(TownyGUI.getInstance().getResource("commands.yml"), "UTF8");
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
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
			configFile = new File(TownyGUI.getInstance().getDataFolder(), "commands.yml");
		
		if (!configFile.exists())
			TownyGUI.getInstance().saveResource("commands.yml", false);
	}
}