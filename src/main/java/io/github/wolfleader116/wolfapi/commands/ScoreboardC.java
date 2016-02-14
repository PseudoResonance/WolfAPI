package io.github.wolfleader116.wolfapi.commands;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.wolfleader116.wolfapi.Config;
import io.github.wolfleader116.wolfapi.Errors;
import io.github.wolfleader116.wolfapi.WolfAPI;
import io.github.wolfleader116.wolfapi.WolfBoard;

public class ScoreboardC implements CommandExecutor {
	
	private static final Logger log = Logger.getLogger("Minecraft");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("scoreboard")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (args.length >= 1) {
					if (player.hasPermission("wolfapi.scoreboard.others")) {
						Player p = Bukkit.getServer().getPlayer(args[0]);
						if (p != null) {
							Config c = new Config(WolfAPI.plugin.getDataFolder() + File.pathSeparator + "playerdata" + File.pathSeparator + WolfAPI.getDataFile(p.getName()), WolfAPI.plugin);
							if (c.getConfig().getBoolean("settings.scoreboard")) {
								c.getConfig().set("settings.scoreboard", false);
								c.save();
								WolfAPI.plugin.sendMessage(p, "Disabled " + p.getName() + "'s scoreboard!");
								WolfBoard.setVisibility(player, false);
							} else {
								c.getConfig().set("settings.scoreboard", true);
								c.save();
								WolfAPI.plugin.sendMessage(p, "Enabled " + p.getName() + "'s scoreboard!");
								WolfBoard.setVisibility(player, true);
							}
						} else {
							WolfAPI.plugin.sendError(Errors.NOT_ONLINE, player, args[0]);
						}
					} else {
						WolfAPI.plugin.sendError(Errors.NO_PERMISSION, player);
					}
				} else {
					if (player.hasPermission("wolfapi.scoreboard")) {
						Config c = new Config(WolfAPI.plugin.getDataFolder() + File.pathSeparator + "playerdata" + File.pathSeparator + WolfAPI.getDataFile(args[0]), WolfAPI.plugin);
						if (c.getConfig().getBoolean("settings.scoreboard")) {
							c.getConfig().set("settings.scoreboard", false);
							c.save();
							WolfAPI.plugin.sendMessage(player, "Disabled your scoreboard!");
							WolfBoard.setVisibility(player, false);
						} else {
							c.getConfig().set("settings.scoreboard", true);
							c.save();
							WolfAPI.plugin.sendMessage(player, "Enabled your scoreboard!");
							WolfBoard.setVisibility(player, true);
						}
					} else {
						WolfAPI.plugin.sendError(Errors.NO_PERMISSION, player);
					}
				}
			} else {
				if (args.length >= 1) {
					Player player = Bukkit.getServer().getPlayer(args[0]);
					if (player != null) {
						Config c = new Config(WolfAPI.plugin.getDataFolder() + File.pathSeparator + "playerdata" + File.pathSeparator + WolfAPI.getDataFile(player.getName()), WolfAPI.plugin);
						if (c.getConfig().getBoolean("settings.scoreboard")) {
							c.getConfig().set("settings.scoreboard", false);
							c.save();
							log.info("Disabled " + player.getName() + "'s scoreboard");
							WolfBoard.setVisibility(player, false);
						} else {
							c.getConfig().set("settings.scoreboard", true);
							c.save();
							log.info("Enabled " + player.getName() + "'s scoreboard");
							WolfBoard.setVisibility(player, true);
						}
					} else {
						log.warning("That player is offline!");
					}
				} else {
					log.info("Please specify a player whose scoreboard visibility will be toggled!");
				}
			}
		}
		return false;
	}

}
