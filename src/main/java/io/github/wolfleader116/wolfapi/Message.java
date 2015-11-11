package io.github.wolfleader116.wolfapi;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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

}
