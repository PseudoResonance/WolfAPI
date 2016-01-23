package io.github.wolfleader116.wolfapi.commands;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.wolfleader116.wolfapi.ChatComponent;
import io.github.wolfleader116.wolfapi.ChatElement;
import io.github.wolfleader116.wolfapi.SubCommand;
import io.github.wolfleader116.wolfapi.SubCommandExecutor;
import io.github.wolfleader116.wolfapi.WolfAPI;

public class DownloadSC implements SubCommandExecutor {
	
	private static final Logger log = Logger.getLogger("Minecraft");

	public boolean onCommand(CommandSender sender, SubCommand subcommand, String[] args) {
		if (subcommand.getName().equalsIgnoreCase("download")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				player.sendMessage(WolfAPI.getMenuColor1() + "===---" + WolfAPI.getMenuColor2() + "WolfPlugin Downloads" + WolfAPI.getMenuColor1() + "---===");
				player.sendMessage(WolfAPI.getMenuInfoColor() + "WolfAPI plugin created by WolfLeader116");
				WolfAPI.plugin.sendJSONMessage(player, new ChatElement(WolfAPI.getMenuInfoColor() + "For WolfPlugin downloads, "), new ChatElement(ChatColor.RED + "click here", ChatComponent.OPEN_URL.value("https://drone.io/github.com/WolfLeader116"), ChatComponent.SHOW_TEXT.value(ChatColor.RED + "Click Here to Download WolfLeader116's Plugins!")), new ChatElement(WolfAPI.getMenuInfoColor() + "!"));
			} else {
				log.info("===---" + "WolfPlugin Downloads" + "---===");
				log.info("WolfAPI plugin created by WolfLeader116");
				log.info("Visit https://drone.io/github.com/WolfLeader116 to download WolfLeader116's WolfPlugins!");
			}
		}
		return false;
	}

}
