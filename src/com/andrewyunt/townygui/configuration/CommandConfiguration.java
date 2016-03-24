package com.andrewyunt.townygui.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.andrewyunt.townygui.TownyGUI;

public class CommandConfiguration {
	
	private FileConfiguration config = null;
	private File configFile = null;
	
	public void reloadConfig() {
		
	    if(configFile == null)
	    	configFile = new File(TownyGUI.plugin.getDataFolder(), "commands.yml");
	    
	    config = YamlConfiguration.loadConfiguration(configFile);

	    // Look for defaults in the jar
	    Reader defConfigStream = null;
	    
		try {
			defConfigStream = new InputStreamReader(TownyGUI.plugin.getResource("commands.yml"), "UTF8");
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	    if(defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        config.setDefaults(defConfig);
	    }
	}
	
	public FileConfiguration getConfig() {
	    
		if(config == null)
	        reloadConfig();
	    
	    return config;
	}
	
	public void saveConfig() {
		
	    if(config == null || configFile == null)
	        return;
	    
	    try {
	        getConfig().save(configFile);
	    } catch(IOException ex) {
	        TownyGUI.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
	    }
	}
	
	public void saveDefaultConfig() {
		
	    if(configFile == null)
	        configFile = new File(TownyGUI.plugin.getDataFolder(), "commands.yml");
	    
	    if(!configFile.exists())
	         TownyGUI.plugin.saveResource("commands.yml", false);
	}
}