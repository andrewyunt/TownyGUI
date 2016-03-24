package com.andrewyunt.townygui.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

import com.andrewyunt.townygui.Menu;
import com.gmail.filoghost.hiddenstring.HiddenStringUtils;

public class InventoryListener implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		
		Player player = (Player) event.getWhoClicked();
		
		if(!(event.getInventory().getHolder() == null))
			return;
		
		if(event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta())
			return;
		
		ItemMeta meta = event.getCurrentItem().getItemMeta();
		List<String> lore = meta.getLore();
		
		if(!HiddenStringUtils.hasHiddenString(lore.get(0)))
			return;
		
		String action = HiddenStringUtils.extractHiddenString(lore.get(0));
		
		boolean command;
		
		if(action.startsWith("/"))
			command = true;
		else
			command = false;
		
		if(!command)
			new Menu(player, action);
		else {
			player.closeInventory();
			switch(action) {
			case "/towny map":
				Bukkit.getServer().dispatchCommand(player, "towny map");
			}
		}
		
		event.setCancelled(true);
	}
}