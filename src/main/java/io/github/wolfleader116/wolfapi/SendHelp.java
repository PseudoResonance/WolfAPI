package io.github.wolfleader116.wolfapi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SendHelp {
	
	private static final Logger log = Logger.getLogger("Minecraft");
	
	public static boolean sendMessage(CommandSender receiver, WolfPlugin plugin, String[] args) {
		if (args.length == 0) {
			SendHelp.sendInfo(receiver, WolfAPI.plugin);
		} else if (args.length >= 1) {
			if (args[0].equalsIgnoreCase("help")) {
				SendHelp.sendHelp(receiver, WolfAPI.plugin);
				return true;
			} else if (args[0].equalsIgnoreCase("reset")) {
				SendHelp.sendReset(receiver, WolfAPI.plugin);
				return true;
			} else if (args[0].equalsIgnoreCase("reload")) {
				SendHelp.sendReload(receiver, WolfAPI.plugin);
				return true;
			} else {
				for (SubCommand sub : plugin.getCommands()) {
					if (args[0].equalsIgnoreCase(sub.getName())) {
						List<String> argsList = new ArrayList<String>();
						for (String arg : args) {
							argsList.add(arg);
						}
						String[] returnArgs = argsList.toArray(new String[argsList.size()]);
						sub.getSubCommandExecutor().onCommand(receiver, sub, returnArgs);
						return true;
					}
				}
				if (receiver instanceof Player) {
					Player player = (Player) receiver;
					WolfAPI.plugin.sendError(Errors.NOT_A_COMMAND, player);
				} else {
					String NAC = WolfAPI.plugin.getConfig().getString("PluginErrorMessagesNoInfo.NotACommand");
					NAC = NAC.replaceAll("%command%", plugin.getMainCommand());
					log.info(NAC);
				}
			}
		}
		return false;
	}
	
	public static void sendInfo(CommandSender receiver, WolfPlugin plugin) {
		if (receiver instanceof Player) {
			Player player = (Player) receiver;
			player.sendMessage(WolfAPI.getMenuColor1() + "===---" + WolfAPI.getMenuColor2() + plugin.getPluginName() + " Info" + WolfAPI.getMenuColor1() + "---===");
			player.sendMessage(WolfAPI.getMenuInfoColor() + plugin.getPluginName() + " plugin created by " + plugin.getAuthor());
			player.sendMessage(WolfAPI.getMenuInfoColor() + "Use " + ChatColor.RED + "/" + plugin.getMainCommand() + " help " + WolfAPI.getMenuInfoColor() + "for a list of " + plugin.getPluginName() + " commands.");
		} else {
			log.info("===---" + plugin.getPluginName() + " Info" + "---===");
			log.info(plugin.getPluginName() + " plugin created by " + plugin.getAuthor());
			log.info("Use /" + plugin.getMainCommand() + " help for a list of " + plugin.getPluginName() + " commands.");
		}
	}
	
	public static void sendHelp(CommandSender receiver, WolfPlugin plugin) {
		List<SubCommand> subcommands = plugin.getCommands();
		if (receiver instanceof Player) {
			Player player = (Player) receiver;
			player.sendMessage(WolfAPI.getMenuColor1() + "===---" + WolfAPI.getMenuColor2() + plugin.getPluginName() + " Help" + WolfAPI.getMenuColor1() + "---===");
			player.sendMessage(ChatColor.RED + "/" + plugin.getMainCommand() + " " + WolfAPI.getMenuInfoColor() + "Shows the info page");
			if (player.hasPermission(plugin.getName().toLowerCase() + ".reset") && !(plugin.getConfigFiles().isEmpty())) {
				player.sendMessage(ChatColor.RED + "/" + plugin.getMainCommand() + " reset " + WolfAPI.getMenuInfoColor() + "Resets the config");
			}
			if (player.hasPermission(plugin.getName().toLowerCase() + ".reload") && !(plugin.getConfigFiles().isEmpty())) {
				player.sendMessage(ChatColor.RED + "/" + plugin.getMainCommand() + " reload " + WolfAPI.getMenuInfoColor() + "Reloads the config");
			}
			for (SubCommand subCommand : subcommands) {
				if (player.hasPermission(subCommand.getPermission())) {
					player.sendMessage(ChatColor.RED + "/" + plugin.getMainCommand() + " " + subCommand.getName() + " " + WolfAPI.getMenuInfoColor() + subCommand.getDescription());
				} else if (subCommand.getPermission() == "") {
					player.sendMessage(ChatColor.RED + "/" + plugin.getMainCommand() + " " + subCommand.getName() + " " + WolfAPI.getMenuInfoColor() + subCommand.getDescription());
				}
			}
		} else {
			log.info("===---" + plugin.getPluginName() + " Help" + "---===");
			log.info("/" + plugin.getMainCommand() + " Shows the info page");
			if (!(plugin.getConfigFiles().isEmpty())) {
				log.info("/" + plugin.getMainCommand() + " reset Resets the config");
				log.info("/" + plugin.getMainCommand() + " reload Reloads the config");
			}
			for (SubCommand subCommand : subcommands) {
				log.info("/" + plugin.getMainCommand() + " " + subCommand.getName() + " " + subCommand.getDescription());
			}
		}
	}
	
	public static void sendReset(CommandSender receiver, WolfPlugin plugin) {
		List<String> configFiles = plugin.getConfigFiles();
		if (receiver instanceof Player) {
			Player player = (Player) receiver;
			if (player.hasPermission(plugin.getName().toLowerCase() + ".reset")) {
				if (configFiles.isEmpty()) {
					plugin.sendError(Errors.CUSTOM, player, "There are no config files!");
				} else {
					try {
						for (String config : configFiles) {
							if (!(config.startsWith("."))) {
								if (!(config.endsWith(".yml"))) {
									config = config + ".yml";
								}
								String file = config;
								if (file.startsWith(File.pathSeparator)) {
									file = file.replaceFirst(File.pathSeparator, "");
								}
								File f = new File(plugin.getDataFolder(), file);
								if (f.exists()) {
									f.delete();
								}
								if (config.equalsIgnoreCase("config.yml")) {
									plugin.saveDefaultConfig();
									plugin.reloadConfig();
								} else {
									new Config(config, plugin).saveDefaultConfig();
									new Config(config, plugin).reload();
								}
							}
						}
					} catch (Exception e) {
						plugin.sendError(Errors.CUSTOM, player, "There was an error resetting config files!");
					}
					plugin.sendMessage(player, "Reset the config files.");
				}
			} else {
				plugin.sendError(Errors.NO_PERMISSION, player);
			}
		} else {
			if (configFiles.isEmpty()) {
				log.warning("There are no config files for the plugin " + plugin.getPluginName());
			} else {
				try {
					for (String config : configFiles) {
						if (!(config.startsWith("."))) {
							if (!(config.endsWith(".yml"))) {
								config = config + ".yml";
							}
							String file = config;
							if (file.startsWith(File.pathSeparator)) {
								file = file.replaceFirst(File.pathSeparator, "");
							}
							File f = new File(plugin.getDataFolder(), file);
							if (f.exists()) {
								f.delete();
							}
							if (config.equalsIgnoreCase("config.yml")) {
								plugin.saveDefaultConfig();
								plugin.reloadConfig();
							} else {
								new Config(config, plugin).saveDefaultConfig();
								new Config(config, plugin).reload();
							}
						}
					}
				} catch (Exception e) {
					log.warning("There was an error resetting config files for plugin " + plugin.getPluginName());
				}
				log.info("Reset config for plugin " + plugin.getPluginName());
			}
		}
	}
	
	public static void sendReload(CommandSender receiver, WolfPlugin plugin) {
		List<String> configFiles = plugin.getConfigFiles();
		if (receiver instanceof Player) {
			Player player = (Player) receiver;
			if (player.hasPermission(plugin.getName().toLowerCase() + ".reload")) {
				if (configFiles.isEmpty()) {
					plugin.sendError(Errors.CUSTOM, player, "There are no config files!");
				} else {
					try {
						for (String config : configFiles) {
							if (!(config.startsWith("."))) {
								if (!(config.endsWith(".yml"))) {
									config = config + ".yml";
								}
								if (config.equalsIgnoreCase("config.yml")) {
									plugin.reloadConfig();
								} else {
									new Config(config, plugin).reload();
								}
							}
						}
					} catch (Exception e) {
						plugin.sendError(Errors.CUSTOM, player, "There was an error reloading config files!");
					}
					plugin.sendMessage(player, "Reloaded the config files.");
				}
			} else {
				plugin.sendError(Errors.NO_PERMISSION, player);
			}
		} else {
			if (configFiles.isEmpty()) {
				log.warning("There are no config files for the plugin " + plugin.getPluginName());
			} else {
				try {
					for (String config : configFiles) {
						if (!(config.startsWith("."))) {
							if (!(config.endsWith(".yml"))) {
								config = config + ".yml";
							}
							if (config.equalsIgnoreCase("config.yml")) {
								plugin.reloadConfig();
							} else {
								new Config(config, plugin).reload();
							}
						}
					}
				} catch (Exception e) {
					log.warning("There was an error reloading config files for plugin " + plugin.getPluginName());
				}
				log.info("Reloaded config for plugin " + plugin.getPluginName());
			}
		}
	}

}
