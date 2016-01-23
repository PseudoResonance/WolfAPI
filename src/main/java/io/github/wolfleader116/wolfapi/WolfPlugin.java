package io.github.wolfleader116.wolfapi;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WolfPlugin extends JavaPlugin {
	private String name = null;
	private String mainCommand = null;
	private String author = null;
	private List<SubCommand> commands = new ArrayList<SubCommand>();
	private List<String> config = new ArrayList<String>();
	
	protected static List<WolfPlugin> plugins = new ArrayList<WolfPlugin>();
	
	public static WolfPlugin[] getPluginList() {
		List<WolfPlugin> returnList = new ArrayList<WolfPlugin>();
		returnList.addAll(plugins);
		return returnList.toArray(new WolfPlugin[returnList.size()]);
	}
	
	protected void setPluginName(String name) {
		if (name == null) {
			this.name = "";
		} else {
			this.name = name;
		}
	}
	
	protected void setMainCommand(String mainCommand) {
		if (mainCommand == null) {
			this.mainCommand = "";
		} else {
			this.mainCommand = mainCommand;
		}
	}
	
	protected void setConfigFiles(List<String> files) {
		if (config == null) {
			this.config = new ArrayList<String>();
		} else {
			this.config = files;
		}
	}
	
	protected void setAuthor(String author) {
		if (author == null) {
			this.author = "";
		} else {
			this.author = author;
		}
	}
	
	protected void setCommands(List<SubCommand> commands) {
		if (commands == null) {
			this.commands = new ArrayList<SubCommand>();
		} else {
			this.commands = commands;
		}
	}
	
	public String getPluginName() {
		return this.name;
	}
	
	public String getMainCommand() {
		return this.mainCommand;
	}
	
	public List<String> getConfigFiles() {
		return this.config;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public List<SubCommand> getCommands() {
		return this.commands;
	}
	
	public void sendJSONMessage(Player p, ChatElement... elements) throws NullPointerException {
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
	
	public void broadcastJSONMessage(ChatElement... elements) {
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
	
	public void sendJSONMessage(Player p, List<ChatElement> elements) throws NullPointerException {
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
	
	public void broadcastJSONMessage(List<ChatElement> elements) {
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
	
	public void sendMessage(Player player, String message) {
		String pluginName = this.getPluginName();
		if (!(WolfAPI.plugin.getConfig().getBoolean("StripPluginNameColor"))) {
			pluginName = ChatColor.translateAlternateColorCodes('&', pluginName);
		} else {
			pluginName = ChatColor.stripColor(pluginName);
		}
		if (!(WolfAPI.plugin.getConfig().getBoolean("StripPluginMessageColor"))) {
			message = ChatColor.translateAlternateColorCodes('&', message);
		} else {
			message = ChatColor.stripColor(message);
		}
		String returnString = WolfAPI.plugin.getConfig().getString("PluginChatFormat");
		returnString = returnString.replaceAll("%name%", pluginName);
		returnString = returnString.replaceAll("%message%", message);
		player.sendMessage(ChatColor.BLUE + pluginName + ChatColor.BLUE + "> " + ChatColor.GREEN + message);
	}
	
	public void sendError(Errors error, Player p) {
		String pluginName = this.getPluginName();
		Location loc = p.getLocation();
		if (!(WolfAPI.plugin.getConfig().getBoolean("StripPluginNameColor"))) {
			pluginName = ChatColor.translateAlternateColorCodes('&', pluginName);
		} else {
			pluginName = ChatColor.stripColor(pluginName);
		}
		String returnString = WolfAPI.plugin.getConfig().getString("PluginErrorFormat");
		try {
			if (error == Errors.NO_PERMISSION) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				if (error.getInfo() != null) {
					returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessages.NoPermission"));
					String infoMessage = error.getInfo();
					if (!(WolfAPI.plugin.getConfig().getBoolean("StripPluginMessageColor"))) {
						infoMessage = ChatColor.translateAlternateColorCodes('&', infoMessage);
					} else {
						infoMessage = ChatColor.stripColor(infoMessage);
					}
					returnString = returnString.replaceAll("%info%", infoMessage);
					returnString = returnString.replaceAll("%command%", this.getMainCommand());
					returnString = returnString.replaceAll("%name%", pluginName);
				} else {
					returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessagesNoInfo.NoPermission"));
					returnString = returnString.replaceAll("%command%", this.getMainCommand());
					returnString = returnString.replaceAll("%name%", pluginName);
				}
				returnString = ChatColor.translateAlternateColorCodes('&', returnString);
				p.sendMessage(returnString);
			} else if (error == Errors.NOT_ONLINE) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				if (error.getInfo() != null) {
					returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessages.NotOnline"));
					String infoMessage = error.getInfo();
					if (!(WolfAPI.plugin.getConfig().getBoolean("StripPluginMessageColor"))) {
						infoMessage = ChatColor.translateAlternateColorCodes('&', infoMessage);
					} else {
						infoMessage = ChatColor.stripColor(infoMessage);
					}
					returnString = returnString.replaceAll("%info%", infoMessage);
					returnString = returnString.replaceAll("%command%", this.getMainCommand());
					returnString = returnString.replaceAll("%name%", pluginName);
				} else {
					returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessagesNoInfo.NotOnline"));
					returnString = returnString.replaceAll("%command%", this.getMainCommand());
					returnString = returnString.replaceAll("%name%", pluginName);
				}
				returnString = ChatColor.translateAlternateColorCodes('&', returnString);
				p.sendMessage(returnString);
			} else if (error == Errors.NEVER_JOINED) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				if (error.getInfo() != null) {
					returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessages.NeverJoined"));
					String infoMessage = error.getInfo();
					if (!(WolfAPI.plugin.getConfig().getBoolean("StripPluginMessageColor"))) {
						infoMessage = ChatColor.translateAlternateColorCodes('&', infoMessage);
					} else {
						infoMessage = ChatColor.stripColor(infoMessage);
					}
					returnString = returnString.replaceAll("%info%", infoMessage);
					returnString = returnString.replaceAll("%command%", this.getMainCommand());
					returnString = returnString.replaceAll("%name%", pluginName);
				} else {
					returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessagesNoInfo.NeverJoined"));
					returnString = returnString.replaceAll("%command%", this.getMainCommand());
					returnString = returnString.replaceAll("%name%", pluginName);
				}
				returnString = ChatColor.translateAlternateColorCodes('&', returnString);
				p.sendMessage(returnString);
			} else if (error == Errors.NOT_NUMBER) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				if (error.getInfo() != null) {
					returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessages.NotNumber"));
					String infoMessage = error.getInfo();
					if (!(WolfAPI.plugin.getConfig().getBoolean("StripPluginMessageColor"))) {
						infoMessage = ChatColor.translateAlternateColorCodes('&', infoMessage);
					} else {
						infoMessage = ChatColor.stripColor(infoMessage);
					}
					returnString = returnString.replaceAll("%info%", infoMessage);
					returnString = returnString.replaceAll("%command%", this.getMainCommand());
					returnString = returnString.replaceAll("%name%", pluginName);
				} else {
					returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessagesNoInfo.NotNumber"));
					returnString = returnString.replaceAll("%command%", this.getMainCommand());
					returnString = returnString.replaceAll("%name%", pluginName);
				}
				returnString = ChatColor.translateAlternateColorCodes('&', returnString);
				p.sendMessage(returnString);
			} else if (error == Errors.NOT_A_COMMAND) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				if (error.getInfo() != null) {
					returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessages.NotACommand"));
					String infoMessage = error.getInfo();
					if (!(WolfAPI.plugin.getConfig().getBoolean("StripPluginMessageColor"))) {
						infoMessage = ChatColor.translateAlternateColorCodes('&', infoMessage);
					} else {
						infoMessage = ChatColor.stripColor(infoMessage);
					}
					returnString = returnString.replaceAll("%info%", infoMessage);
					returnString = returnString.replaceAll("%command%", this.getMainCommand());
					returnString = returnString.replaceAll("%name%", pluginName);
				} else {
					returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessagesNoInfo.NotACommand"));
					returnString = returnString.replaceAll("%command%", this.getMainCommand());
					returnString = returnString.replaceAll("%name%", pluginName);
				}
				returnString = ChatColor.translateAlternateColorCodes('&', returnString);
				p.sendMessage(returnString);
			} else if (error == Errors.DEFAULT) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				if (error.getInfo() != null) {
					returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessages.Default"));
					String infoMessage = error.getInfo();
					if (!(WolfAPI.plugin.getConfig().getBoolean("StripPluginMessageColor"))) {
						infoMessage = ChatColor.translateAlternateColorCodes('&', infoMessage);
					} else {
						infoMessage = ChatColor.stripColor(infoMessage);
					}
					returnString = returnString.replaceAll("%info%", infoMessage);
					returnString = returnString.replaceAll("%command%", this.getMainCommand());
					returnString = returnString.replaceAll("%name%", pluginName);
				} else {
					returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessagesNoInfo.Default"));
					returnString = returnString.replaceAll("%command%", this.getMainCommand());
					returnString = returnString.replaceAll("%name%", pluginName);
				}
				returnString = ChatColor.translateAlternateColorCodes('&', returnString);
				p.sendMessage(returnString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendError(Errors error, Player p, String message) {
		String pluginName = this.getPluginName();
		Location loc = p.getLocation();
		if (!(WolfAPI.plugin.getConfig().getBoolean("StripPluginNameColor"))) {
			pluginName = ChatColor.translateAlternateColorCodes('&', pluginName);
		} else {
			pluginName = ChatColor.stripColor(pluginName);
		}
		String returnString = WolfAPI.plugin.getConfig().getString("PluginErrorFormat");
		try {
			if (error == Errors.NO_PERMISSION) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				if (error.getInfo() != null) {
					returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessages.NoPermission"));
					String infoMessage = error.getInfo();
					if (!(WolfAPI.plugin.getConfig().getBoolean("StripPluginMessageColor"))) {
						infoMessage = ChatColor.translateAlternateColorCodes('&', infoMessage);
					} else {
						infoMessage = ChatColor.stripColor(infoMessage);
					}
					returnString = returnString.replaceAll("%info%", infoMessage);
					returnString = returnString.replaceAll("%command%", this.getMainCommand());
					returnString = returnString.replaceAll("%name%", pluginName);
				} else {
					returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessagesNoInfo.NoPermission"));
					returnString = returnString.replaceAll("%command%", this.getMainCommand());
					returnString = returnString.replaceAll("%name%", pluginName);
				}
				p.sendMessage(returnString);
			} else if (error == Errors.NOT_ONLINE) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				if (error.getInfo() != null) {
					returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessages.NotOnline"));
					String infoMessage = error.getInfo();
					if (!(WolfAPI.plugin.getConfig().getBoolean("StripPluginMessageColor"))) {
						infoMessage = ChatColor.translateAlternateColorCodes('&', infoMessage);
					} else {
						infoMessage = ChatColor.stripColor(infoMessage);
					}
					returnString = returnString.replaceAll("%info%", infoMessage);
					returnString = returnString.replaceAll("%command%", this.getMainCommand());
					returnString = returnString.replaceAll("%name%", pluginName);
				} else {
					returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessagesNoInfo.NotOnline"));
					returnString = returnString.replaceAll("%command%", this.getMainCommand());
					returnString = returnString.replaceAll("%name%", pluginName);
				}
				p.sendMessage(returnString);
			} else if (error == Errors.NEVER_JOINED) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				if (error.getInfo() != null) {
					returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessages.NeverJoined"));
					String infoMessage = error.getInfo();
					if (!(WolfAPI.plugin.getConfig().getBoolean("StripPluginMessageColor"))) {
						infoMessage = ChatColor.translateAlternateColorCodes('&', infoMessage);
					} else {
						infoMessage = ChatColor.stripColor(infoMessage);
					}
					returnString = returnString.replaceAll("%info%", infoMessage);
					returnString = returnString.replaceAll("%command%", this.getMainCommand());
					returnString = returnString.replaceAll("%name%", pluginName);
				} else {
					returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessagesNoInfo.NeverJoined"));
					returnString = returnString.replaceAll("%command%", this.getMainCommand());
					returnString = returnString.replaceAll("%name%", pluginName);
				}
				returnString = ChatColor.translateAlternateColorCodes('&', returnString);
				p.sendMessage(returnString);
			} else if (error == Errors.NOT_NUMBER) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				if (error.getInfo() != null) {
					returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessages.NotNumber"));
					String infoMessage = error.getInfo();
					if (!(WolfAPI.plugin.getConfig().getBoolean("StripPluginMessageColor"))) {
						infoMessage = ChatColor.translateAlternateColorCodes('&', infoMessage);
					} else {
						infoMessage = ChatColor.stripColor(infoMessage);
					}
					returnString = returnString.replaceAll("%info%", infoMessage);
					returnString = returnString.replaceAll("%command%", this.getMainCommand());
					returnString = returnString.replaceAll("%name%", pluginName);
				} else {
					returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessagesNoInfo.NotNumber"));
					returnString = returnString.replaceAll("%command%", this.getMainCommand());
					returnString = returnString.replaceAll("%name%", pluginName);
				}
				returnString = ChatColor.translateAlternateColorCodes('&', returnString);
				p.sendMessage(returnString);
			} else if (error == Errors.NOT_A_COMMAND) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				if (error.getInfo() != null) {
					returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessages.NotACommand"));
					String infoMessage = error.getInfo();
					if (!(WolfAPI.plugin.getConfig().getBoolean("StripPluginMessageColor"))) {
						infoMessage = ChatColor.translateAlternateColorCodes('&', infoMessage);
					} else {
						infoMessage = ChatColor.stripColor(infoMessage);
					}
					returnString = returnString.replaceAll("%info%", infoMessage);
					returnString = returnString.replaceAll("%command%", this.getMainCommand());
					returnString = returnString.replaceAll("%name%", pluginName);
				} else {
					returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessagesNoInfo.NotACommand"));
					returnString = returnString.replaceAll("%command%", this.getMainCommand());
					returnString = returnString.replaceAll("%name%", pluginName);
				}
				returnString = ChatColor.translateAlternateColorCodes('&', returnString);
				p.sendMessage(returnString);
			} else if (error == Errors.DEFAULT) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				if (error.getInfo() != null) {
					returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessages.Default"));
					String infoMessage = error.getInfo();
					if (!(WolfAPI.plugin.getConfig().getBoolean("StripPluginMessageColor"))) {
						infoMessage = ChatColor.translateAlternateColorCodes('&', infoMessage);
					} else {
						infoMessage = ChatColor.stripColor(infoMessage);
					}
					returnString = returnString.replaceAll("%info%", infoMessage);
					returnString = returnString.replaceAll("%command%", this.getMainCommand());
					returnString = returnString.replaceAll("%name%", pluginName);
				} else {
					returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessagesNoInfo.Default"));
					returnString = returnString.replaceAll("%command%", this.getMainCommand());
					returnString = returnString.replaceAll("%name%", pluginName);
				}
				returnString = ChatColor.translateAlternateColorCodes('&', returnString);
				p.sendMessage(returnString);
			} else if (error == Errors.CUSTOM) {
				ParticleEffect.FIREWORKS_SPARK.sendToPlayer(p, loc, (float) 0.8, (float) 0.8, (float) 0.8, (float) 0, 5);
				if (error.getInfo() != null) {
					returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessages.Default"));
					String infoMessage = error.getInfo();
					if (!(WolfAPI.plugin.getConfig().getBoolean("StripPluginMessageColor"))) {
						infoMessage = ChatColor.translateAlternateColorCodes('&', infoMessage);
					} else {
						infoMessage = ChatColor.stripColor(infoMessage);
					}
					returnString = returnString.replaceAll("%info%", infoMessage);
					returnString = returnString.replaceAll("%command%", this.getMainCommand());
					returnString = returnString.replaceAll("%name%", pluginName);
				} else {
					returnString = returnString.replaceAll("%message%", WolfAPI.plugin.getConfig().getString("PluginErrorMessagesNoInfo.Default"));
					returnString = returnString.replaceAll("%command%", this.getMainCommand());
					returnString = returnString.replaceAll("%name%", pluginName);
				}
				returnString = ChatColor.translateAlternateColorCodes('&', returnString);
				p.sendMessage(returnString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void registerPlugin() throws PluginAlreadyRegisteredException {
		if (plugins.contains(this)) {
			throw new PluginAlreadyRegisteredException("Plugin " + this.getPluginName() + " is already registered");
		} else {
			plugins.add(this);
		}
	}
	
	protected void unregisterPlugin() throws PluginNotRegisteredException {
		if (plugins.contains(this)) {
			plugins.remove(this);
		} else {
			throw new PluginNotRegisteredException("Plugin " + this.getPluginName() + " is not registered");
		}
	}

}
