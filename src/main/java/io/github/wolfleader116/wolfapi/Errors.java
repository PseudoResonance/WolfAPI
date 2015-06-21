package io.github.wolfleader116.wolfapi;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public enum Errors {

	NO_PERMISSION,
	NOT_ONLINE,
	NEVER_JOINED,
	NOT_NUMBER,
	DEFAULT;
	
	public static void sendError(Errors error, Player p, String prefix) {
		Location loc = p.getLocation();
		if (error == Errors.NO_PERMISSION) {
			Particles.plugin.sendParticles(p, Particles.FIREWORKS_SPARK, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
			p.sendMessage(ChatColor.BLUE + ChatColor.stripColor(prefix) + ChatColor.BLUE + "> " + ChatColor.RED + "You do not have permission to do this!");
		} else if (error == Errors.NOT_ONLINE) {
			Particles.plugin.sendParticles(p, Particles.FIREWORKS_SPARK, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
			p.sendMessage(ChatColor.BLUE + ChatColor.stripColor(prefix) + ChatColor.BLUE + "> " + ChatColor.RED + "That player is not online!");
		} else if (error == Errors.NEVER_JOINED) {
			Particles.plugin.sendParticles(p, Particles.FIREWORKS_SPARK, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
			p.sendMessage(ChatColor.BLUE + ChatColor.stripColor(prefix) + ChatColor.BLUE + "> " + ChatColor.RED + "That player has never joined the server!");
		} else if (error == Errors.NOT_NUMBER) {
			Particles.plugin.sendParticles(p, Particles.FIREWORKS_SPARK, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
			p.sendMessage(ChatColor.BLUE + ChatColor.stripColor(prefix) + ChatColor.BLUE + "> " + ChatColor.RED + "That is not a number!");
		} else if (error == Errors.DEFAULT) {
			Particles.plugin.sendParticles(p, Particles.FIREWORKS_SPARK, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
			p.sendMessage(ChatColor.BLUE + ChatColor.stripColor(prefix) + ChatColor.BLUE + "> " + ChatColor.RED + "There has been an error! Please contact server administrators.");
		}
	}

}
