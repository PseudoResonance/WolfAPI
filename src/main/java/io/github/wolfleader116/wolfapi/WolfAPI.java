package io.github.wolfleader116.wolfapi;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

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
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.xxmicloxx.noteblockapi.NBSDecoder;
import com.xxmicloxx.noteblockapi.RadioSongPlayer;
import com.xxmicloxx.noteblockapi.Song;
import com.xxmicloxx.noteblockapi.SongPlayer;

import io.github.wolfleader116.wolfapi.commands.DownloadSC;
import io.github.wolfleader116.wolfapi.commands.PluginsC;
import io.github.wolfleader116.wolfapi.commands.ScoreboardC;
import io.github.wolfleader116.wolfapi.commands.ScoreboardSC;
import io.github.wolfleader116.wolfapi.commands.WolfAPIC;
import io.github.wolfleader116.wolfapi.tabcompleters.WolfAPITC;
import io.puharesource.mc.titlemanager.api.ActionbarTitleObject;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderHook;
import me.confuser.barapi.BarAPI;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;

public class WolfAPI extends WolfPlugin implements Listener {
	
	private static final Logger log = Logger.getLogger("Minecraft");

	public static WolfPlugin plugin;
	
	private static SongPlayer songPlayer;
	private static int songNumber = 0;
	
	private static boolean barBroadcast = true;
	private static boolean titleBroadcast = true;
	
	private static String currentSong = "None";
	
	private static List<String> noMusic = new ArrayList<String>();
	
	private static List<String> playerDataFiles = new ArrayList<String>();
	
	public static Economy economy = null;
	public static Chat chat = null;
	
	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}
		return (economy != null);
	}
	
	private boolean setupChat() {
		RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
		if (chatProvider != null) {
			chat = chatProvider.getProvider();
		}
		return (chat != null);
	}
	
	@Override
	public void onEnable() {
		new File(plugin.getDataFolder() + File.pathSeparator + "songs").mkdir();
		this.saveDefaultConfig();
		if (this.getConfig().getInt("Version") != 2) {
			File configFile = new File(WolfAPI.plugin.getDataFolder(), "config.yml");
			configFile.delete();
			this.saveDefaultConfig();
			this.reloadConfig();
		}
		plugin = this;
		setupEconomy();
		setupChat();
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
		this.getCommand("scoreboard").setExecutor(new ScoreboardC());
		List<SubCommand> subCommands = new ArrayList<SubCommand>();
		subCommands.add(new SubCommand("download", "Gives the download link for this mod.", new DownloadSC()));
		subCommands.add(new SubCommand("sc", "Toggles the scoreboard visibility", new ScoreboardSC()));
		subCommands.add(new SubCommand("scoreboard", "Toggles the scoreboard visibility", new ScoreboardSC()));
		this.setCommands(subCommands);
		File dir = new File(plugin.getDataFolder() + File.pathSeparator + "playerdata");
		List<String> filelist = new ArrayList<String>(Arrays.asList(dir.list()));
		playerDataFiles = filelist;
		for (String f : filelist) {
			Config c = new Config(plugin.getDataFolder() + File.pathSeparator + "playerdata" + File.pathSeparator + f, plugin);
			if (!(c.getConfig().getBoolean("music"))) {
				noMusic.add(c.getConfig().getString("name"));
			}
		}
		if (!(Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceHolderAPI"))) {
			log.info("WolfAPI - PlaceHolderAPI was not found on the server! Only basic placeholder support is enabled.");
		} else {
			enablePlaceholders();
		}
		WolfBoard.setTitle(plugin.getConfig().getStringList("ScoreboardTitle"));
		Map<Integer, ScoreboardLine> rawLines = new HashMap<Integer, ScoreboardLine>();
		for (String key : plugin.getConfig().getConfigurationSection("Scoreboard").getKeys(false)) {
			String type = plugin.getConfig().getString("Scoreboard." + key + ".Type");
			if (type.equalsIgnoreCase("TypeWriter")) {
				TypewriterInfo ti = new TypewriterInfo(plugin.getConfig().getLong("Scoreboard." + key + ".UpdateDelay"), plugin.getConfig().getStringList("Scoreboard." + key + ".Text"), plugin.getConfig().getLong("Scoreboard." + key + ".PointerDelay"), plugin.getConfig().getString("Scoreboard." + key + ".Pointer").toCharArray()[0], plugin.getConfig().getLong("Scoreboard." + key + ".EndDelay"), plugin.getConfig().getLong("Scoreboard." + key + ".StartDelay"));
				int num = Integer.valueOf(Pattern.compile("line", Pattern.CASE_INSENSITIVE).matcher(key).replaceAll(""));
				rawLines.put(num, ti);
			} else if (type.equalsIgnoreCase("Text")) {
				TextInfo sl = new TextInfo(plugin.getConfig().getLong("Scoreboard." + key + ".UpdateDelay"), plugin.getConfig().getStringList("Scoreboard." + key + ".Text"));
				int num = Integer.valueOf(Pattern.compile("line", Pattern.CASE_INSENSITIVE).matcher(key).replaceAll(""));
				rawLines.put(num, sl);
			}
		}
		WolfBoard.setRawLines(rawLines);
		try {
			registerPlugin();
		} catch (PluginAlreadyRegisteredException e) {
			e.printStackTrace();
		}
		if (!(Bukkit.getServer().getPluginManager().isPluginEnabled("Vault"))) {
			log.info("WolfAPI - Vault was not found on the server! Money support is disabled.");
		}
		if (!(Bukkit.getServer().getPluginManager().isPluginEnabled("TitleManager"))) {
			log.info("WolfAPI - TitleManager was not found on the server! Title support is disabled.");
		}
		if (!(Bukkit.getServer().getPluginManager().isPluginEnabled("BarAPI"))) {
			log.info("WolfAPI - BarAPI was not found on the server! BossBar support is disabled.");
		}
		if (plugin.getConfig().getBoolean("BarBroadcast")) {
			barBroadcast = true;
		} else {
			barBroadcast = false;
		}
		if (plugin.getConfig().getBoolean("TitleBroadcast")) {
			titleBroadcast = true;
		} else {
			titleBroadcast = false;
		}
		if (plugin.getConfig().getBoolean("EnableMusic")) {
			songNumber = plugin.getConfig().getInt("LastSong");
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
				public void run() {
					firstLoop();
				}
			}, 200);
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
	
	public static void enablePlaceholders() {
		boolean hookedplayers = PlaceholderAPI.registerPlaceholderHook(WolfAPI.plugin, new PlaceholderHook() {
			@Override
			public String onPlaceholderRequest(Player p, String identifier) {
				if (identifier.equals("onlineplayers")) {
					int players = 0;
					for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
						if (pl != null) {
							if (p.canSee(pl)) {
								players++;
							}
						}
					}
					return String.valueOf(players);
				}
				return null;
				}
			});
		if (!(hookedplayers)) {
			log.info("Error hooking into PlaceholderAPI!");
		}
		boolean hookedstaff = PlaceholderAPI.registerPlaceholderHook(WolfAPI.plugin, new PlaceholderHook() {
			@Override
			public String onPlaceholderRequest(Player p, String identifier) {
				if (identifier.equals("onlinestaff")) {
					int staff = 0;
					for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
						if (pl != null) {
							for (String g : WolfAPI.plugin.getConfig().getStringList("StaffGroups")) {
								if (WolfAPI.chat.playerInGroup(pl, g)) {
									if (p.canSee(pl)) {
										staff++;
									}
								}
							}
						}
					}
					return String.valueOf(staff);
				}
				return null;
				}
			});
		if (!(hookedstaff)) {
			log.info("Error hooking into PlaceholderAPI!");
		}
		boolean hookedsong = PlaceholderAPI.registerPlaceholderHook(WolfAPI.plugin, new PlaceholderHook() {
			@Override
			public String onPlaceholderRequest(Player p, String identifier) {
				if (identifier.equals("currentsong")) {
					return currentSong;
				}
				return null;
				}
			});
		if (!(hookedsong)) {
			log.info("Error hooking into PlaceholderAPI!");
		}
	}
	
	public static String getCurrentSong() {
		return currentSong;
	}
	
	public static List<String> getDataFiles() {
		return playerDataFiles;
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
	
	public static boolean isMusic() {
		if (WolfAPI.plugin.getConfig().getBoolean("EnableMusic")) {
			return true;
		} else {
			return false;
		}
	}
	
	public void endLoop(final SongPlayer sp) {
		if (sp.isPlaying()) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
				public void run() {
					endLoop(sp);
				}
			}, 5);
		} else {
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
				public void run() {
					startLoop();
				}
			}, 100);
		}
	}

	public void firstLoop() {
		try {
			String songname = name();
			Song s = NBSDecoder.parse(new File(getDataFolder() + File.pathSeparator + "songs", songname + ".nbs"));
			SongPlayer sp = new RadioSongPlayer(s);
			sp.setAutoDestroy(true);
			for (Player online : Bukkit.getServer().getOnlinePlayers()) {
				if (noMusic.contains(online.getName())) {
					sp.addPlayer(online);
					if ((Bukkit.getServer().getPluginManager().isPluginEnabled("BarAPI")) && barBroadcast) {
						String message = plugin.getConfig().getString("BarBroadcastMessage");
						message = message.replaceAll("%songname%", songname);
						BarAPI.setMessage(online, message, plugin.getConfig().getInt("BarBroadcastLength"));
					}
					if ((Bukkit.getServer().getPluginManager().isPluginEnabled("TitleManager")) && titleBroadcast) {
						String message = plugin.getConfig().getString("TitleBroadcastMessage");
						message = message.replaceAll("%songname%", songname);
						sendActionbarMessage(online, message);
					}
				}
			}
			currentSong = songname;
			sp.setPlaying(true);
			endLoop(sp);
			songPlayer = sp;
		} catch (Exception e) {
			songNumber = 0;
			plugin.getConfig().set("LastSong", 0);
			plugin.saveConfig();
			String songname = name();
			if (new File(getDataFolder() + File.pathSeparator + "songs", songname + ".nbs").exists() != true) {
				log.severe("NO VALID SONG FILES FOUND!");
			} else {
				Song s = NBSDecoder.parse(new File(getDataFolder() + File.pathSeparator + "songs", songname + ".nbs"));
				SongPlayer sp = new RadioSongPlayer(s);
				sp.setAutoDestroy(true);
				for (Player online : Bukkit.getServer().getOnlinePlayers()) {
					if (noMusic.contains(online.getName())) {
						sp.addPlayer(online);
						if ((Bukkit.getServer().getPluginManager().isPluginEnabled("BarAPI")) && barBroadcast) {
							String message = plugin.getConfig().getString("BarBroadcastMessage");
							message = message.replaceAll("%songname%", songname);
							BarAPI.setMessage(online, message, plugin.getConfig().getInt("BarBroadcastLength"));
						}
						if ((Bukkit.getServer().getPluginManager().isPluginEnabled("TitleManager")) && titleBroadcast) {
							String message = plugin.getConfig().getString("TitleBroadcastMessage");
							message = message.replaceAll("%songname%", songname);
							sendActionbarMessage(online, message);
						}
					}
				}
				currentSong = songname;
				sp.setPlaying(true);
				endLoop(sp);
				songPlayer = sp;
			}
		}
	}

	public void startLoop() {
		try {
			songNumber++;
			plugin.getConfig().set("LastSong", songNumber);
			plugin.saveConfig();
			String songname = name();
			Song s = NBSDecoder.parse(new File(getDataFolder() + File.pathSeparator + "songs", songname + ".nbs"));
			SongPlayer sp = new RadioSongPlayer(s);
			sp.setAutoDestroy(true);
			for (Player online : Bukkit.getServer().getOnlinePlayers()) {
				if (noMusic.contains(online.getName())) {
					sp.addPlayer(online);
					if ((Bukkit.getServer().getPluginManager().isPluginEnabled("BarAPI")) && barBroadcast) {
						String message = plugin.getConfig().getString("BarBroadcastMessage");
						message = message.replaceAll("%songname%", songname);
						BarAPI.setMessage(online, message, plugin.getConfig().getInt("BarBroadcastLength"));
					}
					if ((Bukkit.getServer().getPluginManager().isPluginEnabled("TitleManager")) && titleBroadcast) {
						String message = plugin.getConfig().getString("TitleBroadcastMessage");
						message = message.replaceAll("%songname%", songname);
						sendActionbarMessage(online, message);
					}
				}
			}
			currentSong = songname;
			sp.setPlaying(true);
			endLoop(sp);
			songPlayer = sp;
		} catch (Exception e) {
			songNumber = 0;
			plugin.getConfig().set("LastSong", 0);
			plugin.saveConfig();
			String songname = name();
			if (new File(getDataFolder() + File.pathSeparator + "songs", songname + ".nbs").exists() != true) {
				log.severe("NO VALID SONG FILES FOUND!");
			} else {
				Song s = NBSDecoder.parse(new File(getDataFolder() + File.pathSeparator + "songs", songname + ".nbs"));
				SongPlayer sp = new RadioSongPlayer(s);
				sp.setAutoDestroy(true);
				for (Player online : Bukkit.getServer().getOnlinePlayers()) {
					if (noMusic.contains(online.getName())) {
						sp.addPlayer(online);
						if ((Bukkit.getServer().getPluginManager().isPluginEnabled("BarAPI")) && barBroadcast) {
							String message = plugin.getConfig().getString("BarBroadcastMessage");
							message = message.replaceAll("%songname%", songname);
							BarAPI.setMessage(online, message, plugin.getConfig().getInt("BarBroadcastLength"));
						}
						if ((Bukkit.getServer().getPluginManager().isPluginEnabled("TitleManager")) && titleBroadcast) {
							String message = plugin.getConfig().getString("TitleBroadcastMessage");
							message = message.replaceAll("%songname%", songname);
							sendActionbarMessage(online, message);
						}
					}
				}
				currentSong = songname;
				sp.setPlaying(true);
				endLoop(sp);
				songPlayer = sp;
			}
		}
	}

	private void sendActionbarMessage(Player player, String message) {
		new ActionbarTitleObject(message).send(player);
	}

	public static String name() {
		if (songNumber == -1) {
			File f = new File(plugin.getDataFolder() + File.pathSeparator + "songs");
			List<String> songlist = new ArrayList<String>(Arrays.asList(f.list()));
			String[] songs = songlist.toArray(new String[0]);
			String song = songs[0].replace(".nbs", "");
			return song;
		} else {
			File f = new File(plugin.getDataFolder() + File.pathSeparator + "songs");
			List<String> songlist = new ArrayList<String>(Arrays.asList(f.list()));
			String[] songs = songlist.toArray(new String[0]);
			String song = songs[songNumber].replace(".nbs", "");
			return song;
		}
	}
	
	public static String getDataFile(String p) {
		for (String f : playerDataFiles) {
			Config c = new Config(plugin.getDataFolder() + File.pathSeparator + "playerdata" + File.pathSeparator + f, plugin);
			if (c.getConfig().getString("name").equalsIgnoreCase(p)) {
				return f;
			}
		}
		return null;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(final PlayerJoinEvent e) {
		final String name = e.getPlayer().getName();
		Config c = new Config(plugin.getDataFolder() + File.pathSeparator + "playerdata" + File.pathSeparator + e.getPlayer().getUniqueId().toString(), plugin);
		if (!(c.getConfig().getString("name").equalsIgnoreCase(name))) {
			List<String> oldnames = c.getConfig().getStringList("previousnames");
			oldnames.add(c.getConfig().getString("name"));
			c.getConfig().set("previousnames", oldnames);
			c.getConfig().set("name", name);
		}
		Long now = System.currentTimeMillis() - 10800000;
		c.getConfig().set("lastjoinleave", now);
		if (c.getConfig().getLong("firstjoin") == 0) {
			c.getConfig().set("firstjoin", now);
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			public void run() {
				songPlayer.addPlayer(Bukkit.getServer().getPlayer(name));
				WolfBoard.addPlayer(e.getPlayer());
			}
		}, 20);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuit(PlayerQuitEvent e) {
		final String name = e.getPlayer().getName();
		Config c = new Config(plugin.getDataFolder() + File.pathSeparator + "playerdata" + File.pathSeparator + e.getPlayer().getUniqueId().toString(), plugin);
		c.getConfig().set("lastjoinleave", System.currentTimeMillis() - 10800000);
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			public void run() {
				WolfBoard.removePlayer(name);
			}
		}, 20);
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
	
	@EventHandler
	public void onConfigReload(ConfigReloadEvent e) {
		if (e.getPlugin().equals(plugin)) {
			WolfBoard.setTitle(plugin.getConfig().getStringList("ScoreboardTitle"));
			Map<Integer, ScoreboardLine> rawLines = new HashMap<Integer, ScoreboardLine>();
			for (String key : plugin.getConfig().getConfigurationSection("Scoreboard").getKeys(false)) {
				String type = plugin.getConfig().getString("Scoreboard." + key + ".Type");
				if (type.equalsIgnoreCase("TypeWriter")) {
					TypewriterInfo ti = new TypewriterInfo(plugin.getConfig().getLong("Scoreboard." + key + ".UpdateDelay"), plugin.getConfig().getStringList("Scoreboard." + key + ".Text"), plugin.getConfig().getLong("Scoreboard." + key + ".PointerDelay"), plugin.getConfig().getString("Scoreboard." + key + ".Pointer").toCharArray()[0], plugin.getConfig().getLong("Scoreboard." + key + ".EndDelay"), plugin.getConfig().getLong("Scoreboard." + key + ".StartDelay"));
					int num = Integer.valueOf(Pattern.compile("line", Pattern.CASE_INSENSITIVE).matcher(key).replaceAll(""));
					rawLines.put(num, ti);
				} else if (type.equalsIgnoreCase("Text")) {
					TextInfo sl = new TextInfo(plugin.getConfig().getLong("Scoreboard." + key + ".UpdateDelay"), plugin.getConfig().getStringList("Scoreboard." + key + ".Text"));
					int num = Integer.valueOf(Pattern.compile("line", Pattern.CASE_INSENSITIVE).matcher(key).replaceAll(""));
					rawLines.put(num, sl);
				}
			}
			WolfBoard.setRawLines(rawLines);
		}
	}
	
}