package com.andrewyunt.townygui.utilities;

import com.gmail.filoghost.hiddenstring.HiddenStringUtils;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
	
	public int getInventorySize(int max) {
		
	    if(max <= 0)
	    	return 9;
	    
	    int quotient = (int)Math.ceil(max / 9.0);
	    
	    return quotient > 5 ? 54: quotient * 9;
	}
	
	public List<String> colorizeStringList(List<String> input) {
		
		List<String> colorized = input.stream().map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList());

		return colorized;
	}
	
	public List<String> hideStringInLore(List<String> inputLore, String hiddenString) {
		
		List<String> outputLore = new ArrayList<>();
		
		outputLore.add(HiddenStringUtils.encodeString(hiddenString));

		for(String line : inputLore)
			outputLore.add(line);

		return outputLore;
	}
	
	public ItemStack addGlow(ItemStack is) {
		
		ItemMeta meta = is.getItemMeta();
		
		meta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		
		is.setItemMeta(meta);
		
		return is;
	}
}