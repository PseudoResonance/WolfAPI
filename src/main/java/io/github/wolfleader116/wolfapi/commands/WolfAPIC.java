package io.github.wolfleader116.wolfapi.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import io.github.wolfleader116.wolfapi.SendHelp;
import io.github.wolfleader116.wolfapi.WolfAPI;

public class WolfAPIC implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("wolfapi")) {
			SendHelp.sendMessage(sender, WolfAPI.plugin, args);
		}
		return false;
	}
}
