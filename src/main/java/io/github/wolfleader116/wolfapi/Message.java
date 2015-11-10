package io.github.wolfleader116.wolfapi;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Message {
	
	public static void sendMessage(Player p, Object... objects) throws NullPointerException {
		if (p != null) {
			String end = "{text:\"\",extra:[";
			boolean first = true;
			for (Object next : objects) {
				if (!(first)) {
					end = end + ",";
				}
				if (next instanceof String) {
					next = ((String) next).replaceAll("§", "\u00A7");
					end = end + "{text:\"" + next + "\"}";
				} else if (next instanceof Hover) {
					String text = ((Hover) next).text();
					String value = ((Hover) next).value();
					text = text.replaceAll("§", "\u00A7");
					value = value.replaceAll("§", "\u00A7");
					if (((Hover) next).type() == HoverActions.SHOW_TEXT) {
						end = end + "{text:\"" + text + "\",hoverEvent:{action:show_text,value:\"" + value + "\"}}";
					} else if (((Hover) next).type() == HoverActions.SHOW_ITEM) {
						end = end + "{text:\"" + text + "\",hoverEvent:{action:show_item,value:\"" + value + "\"}}";
					} else if (((Hover) next).type() == HoverActions.SHOW_ENTITY) {
						end = end + "{text:\"" + text + "\",hoverEvent:{action:show_entity,value:\"" + value + "\"}}";
					} else if (((Hover) next).type() == HoverActions.SHOW_ACHIEVEMENT) {
						end = end + "{text:\"" + text + "\",hoverEvent:{action:show_achievement,value:\"" + value + "\"}}";
					}
				} else if (next instanceof Click) {
					String text = ((Click) next).text();
					String value = ((Click) next).value();
					text = text.replaceAll("§", "\u00A7");
					value = value.replaceAll("§", "\u00A7");
					if (((Click) next).type() == ClickActions.RUN_COMMAND) {
						end = end + "{text:\"" + text + "\",clickEvent:{action:run_command,value:\"" + value + "\"}}";
					} else if (((Click) next).type() == ClickActions.SUGGEST_COMMAND) {
						end = end + "{text:\"" + text + "\",clickEvent:{action:suggest_command,value:\"" + value + "\"}}";
					} else if (((Click) next).type() == ClickActions.OPEN_URL) {
						end = end + "{text:\"" + text + "\",clickEvent:{action:open_url,value:\"" + value + "\"}}";
					}
				} else if (next instanceof Insert) {
					String text = ((Insert) next).text();
					String value = ((Insert) next).value();
					text = text.replaceAll("§", "\u00A7");
					value = value.replaceAll("§", "\u00A7");
					end = end + "{text:\"" + text + "\",insertion:\"" + value + "\"}";
				}
			}
			end = end + "]}";
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tellraw " + p.getName() + " " + end);
		} else {
			throw new NullPointerException();
		}
	}
	
	public static void broadcast(Object... objects) {
		String end = "{text:\"\",extra:[";
		boolean first = true;
		for (Object next : objects) {
			if (!(first)) {
				end = end + ",";
			}
			if (next instanceof String) {
				next = ((String) next).replaceAll("§", "\u00A7");
				end = end + "{text:\"" + next + "\"}";
			} else if (next instanceof Hover) {
				String text = ((Hover) next).text();
				String value = ((Hover) next).value();
				text = text.replaceAll("§", "\u00A7");
				value = value.replaceAll("§", "\u00A7");
				if (((Hover) next).type() == HoverActions.SHOW_TEXT) {
					end = end + "{text:\"" + text + "\",hoverEvent:{action:show_text,value:\"" + value + "\"}}";
				} else if (((Hover) next).type() == HoverActions.SHOW_ITEM) {
					end = end + "{text:\"" + text + "\",hoverEvent:{action:show_item,value:\"" + value + "\"}}";
				} else if (((Hover) next).type() == HoverActions.SHOW_ENTITY) {
					end = end + "{text:\"" + text + "\",hoverEvent:{action:show_entity,value:\"" + value + "\"}}";
				} else if (((Hover) next).type() == HoverActions.SHOW_ACHIEVEMENT) {
					end = end + "{text:\"" + text + "\",hoverEvent:{action:show_achievement,value:\"" + value + "\"}}";
				}
			} else if (next instanceof Click) {
				String text = ((Click) next).text();
				String value = ((Click) next).value();
				text = text.replaceAll("§", "\u00A7");
				value = value.replaceAll("§", "\u00A7");
				if (((Click) next).type() == ClickActions.RUN_COMMAND) {
					end = end + "{text:\"" + text + "\",clickEvent:{action:run_command,value:\"" + value + "\"}}";
				} else if (((Click) next).type() == ClickActions.SUGGEST_COMMAND) {
					end = end + "{text:\"" + text + "\",clickEvent:{action:suggest_command,value:\"" + value + "\"}}";
				} else if (((Click) next).type() == ClickActions.OPEN_URL) {
					end = end + "{text:\"" + text + "\",clickEvent:{action:open_url,value:\"" + value + "\"}}";
				}
			} else if (next instanceof Insert) {
				String text = ((Insert) next).text();
				String value = ((Insert) next).value();
				text = text.replaceAll("§", "\u00A7");
				value = value.replaceAll("§", "\u00A7");
				end = end + "{text:\"" + text + "\",insertion:\"" + value + "\"}";
			}
		}
		end = end + "]}";
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tellraw " + p.getName() + " " + end);
		}
	}

}
