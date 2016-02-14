package io.github.wolfleader116.wolfapi;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

public class ConfigReloadEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	private WolfPlugin p;
	
	public ConfigReloadEvent(WolfPlugin p) {
		this.p = p;
	}
	
	public Plugin getPlugin() {
		return p;
	}

	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
