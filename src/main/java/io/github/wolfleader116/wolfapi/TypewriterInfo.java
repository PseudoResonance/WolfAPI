package io.github.wolfleader116.wolfapi;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TypewriterInfo extends ScoreboardLine {
	
	private long pointerDelay = 20;
	private char pointer = '_';
	private long endDelay = 100;
	private long startDelay = 20;
	private boolean pointerStatus = false;
	private long nextPointerChange = 0;
	
	TypewriterInfo(long updateDelay, List<String> text, long pointerDelay, char pointer, long endDelay, long startDelay) {
		this.pointerDelay = pointerDelay;
		this.pointer = pointer;
		this.endDelay = endDelay;
		this.startDelay = startDelay;
		this.setUpdateDelay(updateDelay);
		this.setRawText(text);
	}
	
	public void setData(long updateDelay, List<String> text, long pointerDelay, char pointer, long endDelay, long startDelay) {
		this.pointerDelay = pointerDelay;
		this.pointer = pointer;
		this.endDelay = endDelay;
		this.startDelay = startDelay;
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
		return new TextRun(this.getUpdateDelay(), this.getText(), pointerDelay, pointer, endDelay, startDelay, pointerStatus, nextPointerChange);
	}

	public long getPointerDelay() {
		return pointerDelay;
	}

	public void setPointerDelay(long pointerDelay) {
		this.pointerDelay = pointerDelay;
	}

	public long getEndDelay() {
		return endDelay;
	}

	public void setEndDelay(long endDelay) {
		this.endDelay = endDelay;
	}

	public long getStartDelay() {
		return startDelay;
	}

	public void setStartDelay(long startDelay) {
		this.startDelay = startDelay;
	}

	public char getPointer() {
		return pointer;
	}

	public void setPointer(char pointer) {
		this.pointer = pointer;
	}

}