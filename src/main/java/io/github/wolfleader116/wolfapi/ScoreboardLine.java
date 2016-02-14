package io.github.wolfleader116.wolfapi;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public abstract class ScoreboardLine {
	
	private long updateDelay = 0;
	private List<String> text = new ArrayList<String>();
	private List<String> rawText = new ArrayList<String>();

	public long getUpdateDelay() {
		return updateDelay;
	}

	public void setUpdateDelay(long updateDelay) {
		this.updateDelay = updateDelay;
	}

	public List<String> getText() {
		return text;
	}

	public void setText(List<String> text) {
		this.text = text;
	}

	public List<String> getRawText() {
		return rawText;
	}

	public void setRawText(List<String> rawText) {
		this.rawText = rawText;
	}
	
	public abstract TextRun startTimer(Player p);

}
