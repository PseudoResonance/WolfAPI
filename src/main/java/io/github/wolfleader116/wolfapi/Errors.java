package io.github.wolfleader116.wolfapi;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public enum Errors {

	NO_PERMISSION,
	NOT_ONLINE,
	NEVER_JOINED,
	NOT_NUMBER,
	DEFAULT,
	CUSTOM;
	
	public static void sendError(Errors error, Player p, String prefix) {
		Location loc = p.getLocation();
		try {
			if (error == Errors.NO_PERMISSION) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				p.sendMessage(ChatColor.BLUE + ChatColor.stripColor(prefix) + ChatColor.BLUE + "> " + ChatColor.RED + "You do not have permission to do this!");
			} else if (error == Errors.NOT_ONLINE) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				p.sendMessage(ChatColor.BLUE + ChatColor.stripColor(prefix) + ChatColor.BLUE + "> " + ChatColor.RED + "That player is not online!");
			} else if (error == Errors.NEVER_JOINED) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				p.sendMessage(ChatColor.BLUE + ChatColor.stripColor(prefix) + ChatColor.BLUE + "> " + ChatColor.RED + "That player has never joined the server!");
			} else if (error == Errors.NOT_NUMBER) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				p.sendMessage(ChatColor.BLUE + ChatColor.stripColor(prefix) + ChatColor.BLUE + "> " + ChatColor.RED + "That is not a number!");
			} else if (error == Errors.DEFAULT) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				p.sendMessage(ChatColor.BLUE + ChatColor.stripColor(prefix) + ChatColor.BLUE + "> " + ChatColor.RED + "There has been an error! Please contact server administrators.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendError(Errors error, Player p, String prefix, String message) {
		Location loc = p.getLocation();
		try {
			if (error == Errors.NO_PERMISSION) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				p.sendMessage(ChatColor.BLUE + ChatColor.stripColor(prefix) + ChatColor.BLUE + "> " + ChatColor.RED + "You do not have permission to do this!");
			} else if (error == Errors.NOT_ONLINE) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				p.sendMessage(ChatColor.BLUE + ChatColor.stripColor(prefix) + ChatColor.BLUE + "> " + ChatColor.RED + "That player is not online!");
			} else if (error == Errors.NEVER_JOINED) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				p.sendMessage(ChatColor.BLUE + ChatColor.stripColor(prefix) + ChatColor.BLUE + "> " + ChatColor.RED + "That player has never joined the server!");
			} else if (error == Errors.NOT_NUMBER) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				p.sendMessage(ChatColor.BLUE + ChatColor.stripColor(prefix) + ChatColor.BLUE + "> " + ChatColor.RED + "That is not a number!");
			} else if (error == Errors.DEFAULT) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				p.sendMessage(ChatColor.BLUE + ChatColor.stripColor(prefix) + ChatColor.BLUE + "> " + ChatColor.RED + "There has been an error! Please contact server administrators.");
			} else if (error == Errors.CUSTOM) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				p.sendMessage(ChatColor.BLUE + ChatColor.stripColor(prefix) + ChatColor.BLUE + "> " + ChatColor.RED + message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
