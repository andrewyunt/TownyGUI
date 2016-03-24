package com.andrewyunt.townygui.listeners;

import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

import com.andrewyunt.townygui.Menu;
import com.andrewyunt.townygui.TownyGUI;
import com.andrewyunt.townygui.utilities.CommandBuilder;
import com.gmail.filoghost.hiddenstring.HiddenStringUtils;

public class InventoryListener implements Listener {
	
	private Set<String> arguments;
	
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
			
			try {
				arguments = TownyGUI.plugin.commandConfig.getConfig().getConfigurationSection("commands." + action + ".arguments").getKeys(false);
			} catch(NullPointerException e) {
				action = action.replace("/", "");
				Bukkit.getServer().dispatchCommand(player, action);
				event.setCancelled(true);
				return;
			}
			
			new CommandBuilder(arguments, action).beginConversation((CommandSender) player);
		}
		
		event.setCancelled(true);
	}
}