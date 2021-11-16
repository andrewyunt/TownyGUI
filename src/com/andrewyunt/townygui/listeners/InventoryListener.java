package com.andrewyunt.townygui.listeners;

import com.andrewyunt.townygui.IconMenu;
import com.andrewyunt.townygui.TownyGUI;
import com.andrewyunt.townygui.utilities.CommandBuilder;
import com.gmail.filoghost.hiddenstring.HiddenStringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Set;

public class InventoryListener implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {

		ItemStack item = event.getCurrentItem();
		
		Player player = (Player) event.getWhoClicked();
		
		if (event.getInventory().getHolder() != null)
			return;
		
		if (item == null ){
			return;
		}
		if (!item.hasItemMeta()){
			System.out.println("No meta");
			return;
		}
		
		ItemMeta meta = item.getItemMeta();
		
		if (!meta.hasLore()){
			System.out.println("No Lore");
			return;
		}
		
		List<String> lore = meta.getLore();
		System.out.println(lore.get(0));
		if (!HiddenStringUtils.hasHiddenString(lore.get(0))){
			if(TownyGUI.debug)
				System.out.println("No hidden string");
			return;
		}
		
		String action = HiddenStringUtils.extractHiddenString(lore.get(0));
		if(TownyGUI.debug)
			System.out.println("Action: " + action);
		boolean command;
		
		command = action.startsWith("/");
		
		if (!command) {
			new IconMenu(player, action);
		} else {
			player.closeInventory();
			
			Set<String> arguments;
			try {
				arguments = TownyGUI.getInstance().commandConfig.getConfig().getConfigurationSection(
						"commands." + action + ".arguments").getKeys(false);
			} catch (NullPointerException e) {
				action = action.replace("/", "");
				TownyGUI.getInstance().server.dispatchCommand(player, action);
				event.setCancelled(true);
				return;
			}
			
			new CommandBuilder(arguments, action).beginConversation((CommandSender) player);
		}
		
		event.setCancelled(true);
	}
}