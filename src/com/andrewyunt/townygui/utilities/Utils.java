package com.andrewyunt.townygui.utilities;

import com.andrewyunt.townygui.TownyGUI;
import com.gmail.filoghost.hiddenstring.HiddenStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
	
	public static int getInventorySize(int max) {
		
		if(max <= 0)
			return 9;
		
		int quotient = (int)Math.ceil(max / 9.0);
		
		return (int) Math.ceil(max / 9.0) > 5 ? 54: quotient * 9;
	}
	
	public static List<String> colorizeStringList(List<String> input) {
		if(TownyGUI.debug)
			System.out.println("Input" + input);
		List<String> colorized = input.stream().map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList());
		if(TownyGUI.debug)
			System.out.println("Colorized" + colorized);
		return colorized;
	}
	
	public static List<String> hideStringInLore(List<String> inputLore, String hiddenString) {
		
		List<String> outputLore = new ArrayList<>();
		
		outputLore.add(HiddenStringUtils.encodeString(hiddenString));
		
		for(String line : inputLore)
			outputLore.add(line);
		
		return outputLore;
	}
	
	public static ItemStack addGlow(ItemStack is) {
		
		String version = Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3]
				.replace("v", "").replace("_", "").replace("R", "");
		
		if (Integer.parseInt(version) < 181)
			return is;
		
		ItemMeta meta = is.getItemMeta();
		
		meta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
		meta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS);
		
		is.setItemMeta(meta);
		
		return is;
	}
}