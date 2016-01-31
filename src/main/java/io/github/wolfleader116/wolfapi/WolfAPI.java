package io.github.wolfleader116.wolfapi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import io.github.wolfleader116.wolfapi.commands.DownloadSC;
import io.github.wolfleader116.wolfapi.commands.PluginsC;
import io.github.wolfleader116.wolfapi.commands.WolfAPIC;
import io.github.wolfleader116.wolfapi.tabcompleters.WolfAPITC;

public class WolfAPI extends WolfPlugin implements Listener {

	public static WolfPlugin plugin;
	
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		if (this.getConfig().getInt("Version") != 1) {
			File configFile = new File(WolfAPI.plugin.getDataFolder(), "config.yml");
			configFile.delete();
			this.saveDefaultConfig();
			this.reloadConfig();
		}
		plugin = this;
		Bukkit.getPluginManager().registerEvents(this, this);
		this.setPluginName("WolfAPI");
		List<String> configFiles = new ArrayList<String>();
		configFiles.add("config");
		this.setConfigFiles(configFiles);
		this.setAuthor("WolfLeader116");
		this.setMainCommand("wolfapi");
		this.getCommand("wolfapi").setExecutor(new WolfAPIC());
		this.getCommand("wolfapi").setTabCompleter(new WolfAPITC());
		this.getCommand("pl").setExecutor(new PluginsC());
		this.getCommand("allpl").setExecutor(new PluginsC());
		List<SubCommand> subCommands = new ArrayList<SubCommand>();
		subCommands.add(new SubCommand("download", "Gives the download link for this mod.", new DownloadSC()));
		this.setCommands(subCommands);
		try {
			registerPlugin();
		} catch (PluginAlreadyRegisteredException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDisable() {
		try {
			unregisterPlugin();
		} catch (PluginNotRegisteredException e) {
			e.printStackTrace();
		}
		WolfPlugin.plugins = null;
		plugin = null;
	}
	
	public static List<WolfPlugin> getPlugins() {
		return plugins;
	}
	
	public static ChatColor getMenuColor1() {
		return ChatColor.getByChar(WolfAPI.plugin.getConfig().getString("PluginMenuTitleColor1"));
	}
	
	public static ChatColor getMenuColor2() {
		return ChatColor.getByChar(WolfAPI.plugin.getConfig().getString("PluginMenuTitleColor2"));
	}
	
	public static ChatColor getMenuInfoColor() {
		return ChatColor.getByChar(WolfAPI.plugin.getConfig().getString("PluginMenuInfoColor"));
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent e) {
		ItemStack clicked = e.getCurrentItem();
		if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY || e.getAction() == InventoryAction.COLLECT_TO_CURSOR || e.getAction() == InventoryAction.HOTBAR_SWAP || e.getAction() == InventoryAction.PLACE_ALL || e.getAction() == InventoryAction.PLACE_ONE || e.getAction() == InventoryAction.PLACE_SOME || e.getAction() == InventoryAction.SWAP_WITH_CURSOR) {
			if (clicked.hasItemMeta()) {
				ItemMeta meta = clicked.getItemMeta();
				if (meta.hasLore()) {
					List<String> lores = meta.getLore();
					for (String lore : lores) {
						if (ChatColor.stripColor(lore).equals("Final - Cannot be modified or repaired.")) {
							if (e.getClickedInventory() instanceof AnvilInventory) {
								sendMessage((Player) e.getWhoClicked(), "That item is final! It cannot be modified or repaired!");
								e.setCancelled(true);
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerItemDamage(PlayerItemDamageEvent e) {
		ItemStack item = e.getItem();
		List<String> lores = item.getItemMeta().getLore();
		for (String lore : lores) {
			if (ChatColor.stripColor(lore).contains("Unbreakable - Cannot be broken.")) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		List<ItemStack> soul = new ArrayList<ItemStack>();
		List<ItemStack> items = e.getDrops();
		for (ItemStack item : items) {
			List<String> lores = item.getItemMeta().getLore();
			for (String lore : lores) {
				if (ChatColor.stripColor(lore).contains("Soulbound - Cannot be dropped upon death.")) {
					e.getDrops().remove(item);
					soul.add(item);
				}
			}
		}
		for (ItemStack item : soul) {
			e.getEntity().getInventory().addItem(item);
		}
	}
	
}