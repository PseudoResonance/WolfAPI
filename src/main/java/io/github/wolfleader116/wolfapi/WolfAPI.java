package io.github.wolfleader116.wolfapi;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WolfAPI extends JavaPlugin {

	public static WolfAPI plugin;
	
	@Override
	public void onEnable() {
		plugin = this;
	}
	
	@Override
	public void onDisable() {
		plugin = null;
	}
	
	public static void message(String message, Player player, String prefix) {
		player.sendMessage(ChatColor.BLUE + ChatColor.stripColor(prefix) + ChatColor.BLUE + "> " + ChatColor.GREEN + message);
	}
	
}
