package io.github.wolfleader116.wolfapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.md_5.bungee.api.ChatColor;

public class WolfBoard {
	
	private static Map<Integer, ScoreboardLine> lines = new HashMap<Integer, ScoreboardLine>();
	private int currentBlank = -1;
	private int currentBlankCount = 1;
	
	public static void setLines(Map<Integer, ScoreboardLine> lines) {
		WolfBoard.lines = lines;
	}
	
	public void updateData() {
		currentBlank = -1;
		currentBlankCount = 1;
		for (int key : lines.keySet()) {
			List<String> text = lines.get(key).getRawText();
			for (int i = 0; i < text.size(); i++) {
				if (text.get(i).equalsIgnoreCase("")) {
					text.add(i, getNextBlank());
				}
			}
		}
	}
	
	public String getNextBlank() {
		currentBlank++;
		if (currentBlank >= 22) {
			currentBlankCount++;
			currentBlank = 0;
		}
		String output = "";
		for (int i = 1; i <= currentBlankCount; i++) {
			switch(currentBlank) {
			case 0:
				output = output + ChatColor.AQUA.toString();
			case 1:
				output = output + ChatColor.BLACK.toString();
			case 2:
				output = output + ChatColor.BLUE.toString();
			case 3:
				output = output + ChatColor.BOLD.toString();
			case 4:
				output = output + ChatColor.DARK_AQUA.toString();
			case 5:
				output = output + ChatColor.DARK_BLUE.toString();
			case 6:
				output = output + ChatColor.DARK_GRAY.toString();
			case 7:
				output = output + ChatColor.DARK_GREEN.toString();
			case 8:
				output = output + ChatColor.DARK_PURPLE.toString();
			case 9:
				output = output + ChatColor.DARK_RED.toString();
			case 10:
				output = output + ChatColor.GOLD.toString();
			case 11:
				output = output + ChatColor.GRAY.toString();
			case 12:
				output = output + ChatColor.GREEN.toString();
			case 13:
				output = output + ChatColor.ITALIC.toString();
			case 14:
				output = output + ChatColor.LIGHT_PURPLE.toString();
			case 15:
				output = output + ChatColor.MAGIC.toString();
			case 16:
				output = output + ChatColor.RED.toString();
			case 17:
				output = output + ChatColor.RESET.toString();
			case 18:
				output = output + ChatColor.STRIKETHROUGH.toString();
			case 19:
				output = output + ChatColor.UNDERLINE.toString();
			case 20:
				output = output + ChatColor.WHITE.toString();
			case 21:
				output = output + ChatColor.YELLOW.toString();
			}
		}
		return output;
	}

}
