package io.github.wolfleader116.wolfapi;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Deprecated
public class Message {
	
	public static void sendJSONMessage(Player p, ChatElement... elements) throws NullPointerException {
		if (p != null) {
			String end = "[\"\"";
			if (elements.length == 0) {
				end = end + "]";
			} else {
				for (ChatElement e : elements) {
					end = end + e.build();
				}
				end = end + "]";
			}
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tellraw " + p.getName() + " " + end);
		} else {
			throw new NullPointerException();
		}
	}
	
	public static void broadcastJSONMessage(ChatElement... elements) {
		String end = "[\"\"";
		if (elements.length == 0) {
			end = end + "]";
		} else {
			for (ChatElement e : elements) {
				end = end + e.build();
			}
			end = end + "]";
		}
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tellraw " + p.getName() + " " + end);
		}
	}
	
	public static void sendJSONMessage(Player p, List<ChatElement> elements) throws NullPointerException {
		if (p != null) {
			String end = "[\"\"";
			if (elements.size() == 0) {
				end = end + "]";
			} else {
				for (ChatElement e : elements) {
					end = end + e.build();
				}
				end = end + "]";
			}
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tellraw " + p.getName() + " " + end);
		} else {
			throw new NullPointerException();
		}
	}
	
	public static void broadcastJSONMessage(List<ChatElement> elements) {
		String end = "[\"\"";
		if (elements.size() == 0) {
			end = end + "]";
		} else {
			for (ChatElement e : elements) {
				end = end + e.build();
			}
			end = end + "]";
		}
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tellraw " + p.getName() + " " + end);
		}
	}
	
	public static void message(Player player, String message, String pluginName) {
		if (!(WolfAPI.plugin.getConfig().getBoolean("StripPluginNameColor"))) {
			pluginName = pluginName.replaceAll("/&([0123456789abcdefklmnor])/ig", "§$1");
		} else {
			pluginName = ChatColor.stripColor(pluginName);
		}
		if (!(WolfAPI.plugin.getConfig().getBoolean("StripPluginMessageColor"))) {
			message = message.replaceAll("/&([0123456789abcdefklmnor])/ig", "§$1");
		} else {
			message = ChatColor.stripColor(message);
		}
		String returnString = WolfAPI.plugin.getConfig().getString("PluginChatFormat");
		returnString = returnString.replaceAll("%name%", pluginName);
		returnString = returnString.replaceAll("%message%", message);
		player.sendMessage(ChatColor.BLUE + pluginName + ChatColor.BLUE + "> " + ChatColor.GREEN + message);
	}
	
	public static void sendError(Errors error, Player p, String pluginName) {
		Location loc = p.getLocation();
		if (!(WolfAPI.plugin.getConfig().getBoolean("StripPluginNameColor"))) {
			pluginName = pluginName.replaceAll("/&([0123456789abcdefklmnor])/ig", "§$1");
		} else {
			pluginName = ChatColor.stripColor(pluginName);
		}
		String returnString = WolfAPI.plugin.getConfig().getString("PluginErrorFormat");
		returnString = returnString.replaceAll("%name%", pluginName);
		try {
			if (error == Errors.NO_PERMISSION) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessages.NoPermission"));
				p.sendMessage(returnString);
			} else if (error == Errors.NOT_ONLINE) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessages.NotOnline"));
				p.sendMessage(returnString);
			} else if (error == Errors.NEVER_JOINED) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessages.NeverJoined"));
				p.sendMessage(returnString);
			} else if (error == Errors.NOT_NUMBER) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessages.NotNumber"));
				p.sendMessage(returnString);
			} else if (error == Errors.NOT_A_COMMAND) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessages.NotACommand"));
				p.sendMessage(returnString);
			} else if (error == Errors.DEFAULT) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessages.Default"));
				p.sendMessage(returnString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendError(Errors error, Player p, String pluginName, String message) {
		Location loc = p.getLocation();
		if (!(WolfAPI.plugin.getConfig().getBoolean("StripPluginNameColor"))) {
			pluginName = pluginName.replaceAll("/&([0123456789abcdefklmnor])/ig", "§$1");
		} else {
			pluginName = ChatColor.stripColor(pluginName);
		}
		if (!(WolfAPI.plugin.getConfig().getBoolean("StripPluginMessageColor"))) {
			message = message.replaceAll("/&([0123456789abcdefklmnor])/ig", "§$1");
		} else {
			message = ChatColor.stripColor(message);
		}
		String returnString = WolfAPI.plugin.getConfig().getString("PluginErrorFormat");
		returnString = returnString.replaceAll("%name%", pluginName);
		returnString = returnString.replaceAll("%message%", message);
		try {
			if (error == Errors.NO_PERMISSION) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				p.sendMessage(ChatColor.BLUE + pluginName + ChatColor.BLUE + "> " + ChatColor.RED + "You do not have permission to do this!");
			} else if (error == Errors.NOT_ONLINE) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				p.sendMessage(ChatColor.BLUE + pluginName + ChatColor.BLUE + "> " + ChatColor.RED + "That player is not online!");
			} else if (error == Errors.NEVER_JOINED) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				p.sendMessage(ChatColor.BLUE + pluginName + ChatColor.BLUE + "> " + ChatColor.RED + "That player has never joined the server!");
			} else if (error == Errors.NOT_NUMBER) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				p.sendMessage(ChatColor.BLUE + pluginName + ChatColor.BLUE + "> " + ChatColor.RED + "That is not a number!");
			} else if (error == Errors.NOT_A_COMMAND) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				p.sendMessage(ChatColor.BLUE + pluginName + ChatColor.BLUE + "> " + ChatColor.RED + "There has been an error! Please contact server administrators.");
			} else if (error == Errors.DEFAULT) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				p.sendMessage(ChatColor.BLUE + pluginName + ChatColor.BLUE + "> " + ChatColor.RED + "There has been an error! Please contact server administrators.");
			} else if (error == Errors.CUSTOM) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				p.sendMessage(ChatColor.BLUE + pluginName + ChatColor.BLUE + "> " + ChatColor.RED + message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
