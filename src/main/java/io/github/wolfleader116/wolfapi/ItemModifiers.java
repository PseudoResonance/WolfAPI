package io.github.wolfleader116.wolfapi;

import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemModifiers {
	
	public ItemStack setSoulbound(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		List<String> lores = meta.getLore();
		lores.add("");
		lores.add("§5§iSoulbound §f- §aCannot be dropped upon death.");
		meta.setLore(lores);
		item.setItemMeta(meta);
		return item;
	}
	
	public ItemStack setFinal(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		List<String> lores = meta.getLore();
		lores.add("");
		lores.add("§5§iFinal §f- §aCannot be modified or repaired.");
		meta.setLore(lores);
		item.setItemMeta(meta);
		return item;
	}
	
	public ItemStack setUnbreakable(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		List<String> lores = meta.getLore();
		lores.add("");
		lores.add("§5§iUnbreakable §f- §aCannot be broken.");
		meta.setLore(lores);
		item.setItemMeta(meta);
		return item;
	}

}
