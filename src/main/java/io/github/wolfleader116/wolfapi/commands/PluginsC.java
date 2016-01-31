package io.github.wolfleader116.wolfapi.commands;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import io.github.wolfleader116.wolfapi.Errors;
import io.github.wolfleader116.wolfapi.WolfAPI;
import io.github.wolfleader116.wolfapi.WolfPlugin;

public class PluginsC implements CommandExecutor {
	
	private static final Logger log = Logger.getLogger("Minecraft");

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("pl")) {
			if (WolfAPI.plugin.getConfig().getBoolean("ReplacePluginCommand")) {
				if (sender instanceof Player) {
					if (sender.hasPermission("wolfapi.pl")) {
						String pluginlist = "";
						int pluginsfound = 0;
						WolfPlugin[] plugins = WolfPlugin.getPluginList();
						for(int i = 0; i < plugins.length; i++) {
							pluginsfound = pluginsfound + 1;
							if (Bukkit.getServer().getPluginManager().isPluginEnabled(plugins[i])) {
								String add = "";
								if (pluginlist == "") {
									add = ChatColor.GREEN + plugins[i].getName();
								} else {
									add = ChatColor.RESET + ", " + ChatColor.GREEN + plugins[i].getName();
								}
								pluginlist = pluginlist + add;
							} else {
								String add = "";
								if (pluginlist == "") {
									add = ChatColor.RED + plugins[i].getName();
								} else {
									add = ChatColor.RESET + ", " + ChatColor.RED + plugins[i].getName();
								}
								pluginlist = pluginlist + add;
							}
						}
						sender.sendMessage(ChatColor.RED + "Please note that all of these plugins will soon be publicly available, but are already open source at WolfLeader116's GitHub. Read the license before using!");
						sender.sendMessage("Plugins (" + pluginsfound + "): " + pluginlist);
					}
				} else {
					String pluginlist = "";
					int pluginsfound = 0;
					WolfPlugin[] plugins = WolfPlugin.getPluginList();
					for(int i = 0; i < plugins.length; i++) {
						pluginsfound = pluginsfound + 1;
						if (Bukkit.getServer().getPluginManager().isPluginEnabled(plugins[i])) {
							String add = "";
							if (pluginlist == "") {
								add = "[E] " + plugins[i].getName();
							} else {
								add = ", [E] " + plugins[i].getName();
							}
							pluginlist = pluginlist + add;
						} else {
							String add = "";
							if (pluginlist == "") {
								add = "[D] " + plugins[i].getName();
							} else {
								add = ", [D] " + plugins[i].getName();
							}
							pluginlist = pluginlist + add;
						}
					}
					log.info("Please note that all of these plugins will soon be publicly available, but are already open source at WolfLeader116's GitHub. Read the license before using!");
					log.info("Plugins (" + pluginsfound + "): " + pluginlist);
				}
			} else {
				if (sender instanceof Player) {
					Bukkit.dispatchCommand(sender, "bukkit:pl");
				} else {
					Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "bukkit:pl");
				}
			}
		} else if (cmd.getName().equalsIgnoreCase("allpl")) {
			if (WolfAPI.plugin.getConfig().getBoolean("ReplacePluginCommand")) {
				if (sender instanceof Player) {
					if (sender.hasPermission("wolfapi.allpl")) {
						String pluginlist = "";
						int pluginsfound = 0;
						Plugin[] plugins = Bukkit.getServer().getPluginManager().getPlugins();
						for(int i = 0; i < plugins.length; i++) {
							pluginsfound = pluginsfound + 1;
							if (Bukkit.getServer().getPluginManager().isPluginEnabled(plugins[i])) {
								String add = "";
								if (pluginlist == "") {
									add = ChatColor.GREEN + plugins[i].getName();
								} else {
									add = ChatColor.RESET + ", " + ChatColor.GREEN + plugins[i].getName();
								}
								pluginlist = pluginlist + add;
							} else {
								String add = "";
								if (pluginlist == "") {
									add = ChatColor.RED + plugins[i].getName();
								} else {
									add = ChatColor.RESET + ", " + ChatColor.RED + plugins[i].getName();
								}
								pluginlist = pluginlist + add;
							}
						}
						sender.sendMessage("Plugins (" + pluginsfound + "): " + pluginlist);
					}
				} else {
					String pluginlist = "";
					int pluginsfound = 0;
					Plugin[] plugins = Bukkit.getServer().getPluginManager().getPlugins();
					for(int i = 0; i < plugins.length; i++) {
						pluginsfound = pluginsfound + 1;
						if (Bukkit.getServer().getPluginManager().isPluginEnabled(plugins[i])) {
							String add = "";
							if (pluginlist == "") {
								add = "[E] " + plugins[i].getName();
							} else {
								add = ", [E] " + plugins[i].getName();
							}
							pluginlist = pluginlist + add;
						} else {
							String add = "";
							if (pluginlist == "") {
								add = "[D] " + plugins[i].getName();
							} else {
								add = ", [D] " + plugins[i].getName();
							}
							pluginlist = pluginlist + add;
						}
					}
					log.info("Plugins (" + pluginsfound + "): " + pluginlist);
				}
			} else {
				if (sender instanceof Player) {
					WolfAPI.plugin.sendError(Errors.NOT_A_COMMAND, (Player) sender);
				} else {
					log.warning("That is not a command!");
				}
			}
		}
		return false;
	}
}
