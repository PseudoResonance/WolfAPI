package io.github.wolfleader116.wolfapi;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TextInfo extends ScoreboardLine {
	
	TextInfo(long updateDelay, List<String> text) {
		this.setUpdateDelay(updateDelay);
		this.setRawText(text);
	}
	
	public void setData(long updateDelay, List<String> text) {
		this.setUpdateDelay(updateDelay);
		this.setRawText(text);
	}

	@Override
	public TextRun startTimer(Player p) {
		List<String> text = this.getRawText();
		List<String> newText = new ArrayList<String>();
		for (int i = 0; i < text.size(); i++) {
			if (text.get(i).equalsIgnoreCase("")) {
				String newString = WolfBoard.getNextBlank(p.getUniqueId());
				if (newString.length() > 32) {
					newString = newString.substring(0, 31);
				}
				newText.add(i, newString);
			} else {
				if (Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceHolderAPI")) {
					String newString = "Hi";
					if (newString.length() > 32) {
						newString = newString.substring(0, 31);
					}
					newText.add(i, newString);
				} else {
					String newString = text.get(i);
					int players = 0;
					for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
						if (pl != null) {
							if (p.canSee(pl)) {
								players++;
							}
						}
					}
					int staff = 0;
					for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
						if (pl != null) {
							for (String g : WolfAPI.plugin.getConfig().getStringList("StaffGroups")) {
								if (WolfAPI.chat.playerInGroup(pl, g)) {
									if (p.canSee(pl)) {
										staff++;
									}
								}
							}
						}
					}
					newString = newString.replaceAll("%players%", String.valueOf(players));
					newString = newString.replaceAll("%staff%", String.valueOf(staff));
					newString = newString.replaceAll("%song%", WolfAPI.getCurrentSong());
					newString = newString.replaceAll("%money%", String.valueOf(WolfAPI.economy.getBalance(p)));
					if (newString.length() > 32) {
						newString = newString.substring(0, 31);
					}
					newText.add(i, newString);
				}
			}
		}
		this.setText(newText);
		return new TextRun(this.getUpdateDelay(), this.getText());
	}
	
}
