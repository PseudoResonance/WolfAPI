package io.github.wolfleader116.wolfapi;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class WolfAPI extends JavaPlugin implements Listener {
	
	private static final Logger log = Logger.getLogger("Minecraft");
	
	private HashMap<Player, ItemStack> soulbound = new HashMap<Player, ItemStack>();

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
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.isCancelled()) && e.getWhoClicked() instanceof Player) {
			ItemStack clicked = e.getCurrentItem();
			Inventory inventory = e.getClickedInventory();
			List<String> lores = clicked.getItemMeta().getLore();
			for (String lore : lores) {
				log.severe(lore);
				if (ChatColor.stripColor(lore).equals("Final - Cannot be modified or repaired.")) {
					log.severe("Is final");
					if (inventory instanceof AnvilInventory) {
						log.severe("Is anvil");
						message("That item is final! It cannot be modified or repaired!", (Player) e.getWhoClicked(), "WolfAPI");
						e.setCancelled(true);
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
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDeath(PlayerDeathEvent e) {
		List<ItemStack> items = e.getDrops();
		for (ItemStack item : items) {
			List<String> lores = item.getItemMeta().getLore();
			for (String lore : lores) {
				if (ChatColor.stripColor(lore).contains("Soulbound - Cannot be dropped upon death.")) {
					e.getDrops().remove(item);
					soulbound.put(e.getEntity(), item);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerSpawn(PlayerRespawnEvent e) {
		if (soulbound.containsKey(e.getPlayer())) {
			e.getPlayer().getInventory().addItem(soulbound.get(e.getPlayer()));
			soulbound.remove(e.getPlayer());
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuit(PlayerQuitEvent e) {
		if (soulbound.containsKey(e.getPlayer())) {
			e.getPlayer().getInventory().addItem(soulbound.get(e.getPlayer()));
		}
	}
	
}