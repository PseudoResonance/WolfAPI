package io.github.wolfleader116.wolfapi;

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

}
