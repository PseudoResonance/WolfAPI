package io.github.wolfleader116.wolfapi;

import org.bukkit.command.CommandSender;

public interface SubCommandExecutor {
	
    public boolean onCommand(CommandSender sender, SubCommand subcommand, String[] args);

}
