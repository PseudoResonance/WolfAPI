package io.github.wolfleader116.wolfapi;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.scheduler.BukkitRunnable;

public class TextRun {

	private String type = "";
	private long updateDelay = 0;
	private List<String> text = new ArrayList<String>();
	private long pointerDelay = 20;
	private char pointer = '_';
	private long endDelay = 100;
	private long startDelay = 20;
	private boolean pointerStatus = false;
	private String output = "";
	private String[] currentParts;
	private int currentStringNumber = 0;
	private long timeUntilPointerChange = 0;
	boolean completed = false;
	
	TextRun(long updateDelay, List<String> text) {
		this.updateDelay = updateDelay;
		this.text = text;
		this.type = "Text";
		textTimer();
	}
	
	TextRun(long updateDelay, List<String> text, long pointerDelay, char pointer, long endDelay, long startDelay, boolean pointerStatus, long nextPointerChange) {
		this.updateDelay = updateDelay;
		this.text = text;
		this.pointerDelay = pointerDelay;
		this.pointer = pointer;
		this.endDelay = endDelay;
		this.startDelay = startDelay;
		this.pointerStatus = pointerStatus;
		this.timeUntilPointerChange = nextPointerChange;
		this.type = "Typewriter";
		typewriterTimer();
	}
	
	void setOutput(String output) {
		this.output = output;
	}
	
	void setNextPointerChange(long time) {
		this.timeUntilPointerChange = time;
	}
	
	public long getTimeUntilNextPointerChange() {
		return this.timeUntilPointerChange;
	}
	
	public boolean getPointerStatus() {
		return this.pointerStatus;
	}

	public char getPointer() {
		return pointer;
	}

	public long getStartDelay() {
		return startDelay;
	}

	public long getEndDelay() {
		return endDelay;
	}

	public long getPointerDelay() {
		return pointerDelay;
	}
	
	void setPointer(boolean pointerStatus) {
		this.pointerStatus = pointerStatus;
	}
	
	public String getOutput() {
		if (this.type.equalsIgnoreCase("text")) {
			return this.output;
		} else if (this.type.equalsIgnoreCase("typewriter")) {
			if (output.equals("")) {
				if (pointerStatus) {
					return String.valueOf(pointer);
				} else {
					return "";
				}
			} else {
				if (pointerStatus) {
					return output + pointer;
				} else {
					return output;
				}
			}
		} else {
			return null;
		}
	}
	
	public boolean isCompleted() {
		return completed;
	}
	
	private void textTimer() {
		BukkitRunnable text = new Text(this, this.text);
		text.runTaskTimer(WolfAPI.plugin, 0, this.updateDelay);
	}
	
	private void typewriterTimer() {
		currentParts = getCurrentParts(this.text.get(currentStringNumber));
		BukkitRunnable add = new Add(this, currentParts);
		add.runTaskTimer(WolfAPI.plugin, this.updateDelay, this.updateDelay);
		BukkitRunnable pointer = new Pointer(this, this.pointerStatus);
		pointer.runTaskTimer(WolfAPI.plugin, this.timeUntilPointerChange, this.pointerDelay);
	}
	
	void typewriterTimer(int op) {
		if (op == 0) {
			BukkitRunnable run = new Add(this, currentParts);
			run.runTaskTimer(WolfAPI.plugin, this.startDelay, this.updateDelay);
		} else if (op == 1) {
			typewriterTimer(2);
		} else if (op == 2) {
			BukkitRunnable run = new Remove(this, currentParts);
			run.runTaskTimer(WolfAPI.plugin, this.endDelay, this.updateDelay);
		} else if (op == 3) {
			completed = true;
		}
	}
	
	private String[] getCurrentParts(String input) {
		List<String> output = new ArrayList<String>();
		char[] chars = input.toCharArray();
		String add = "";
		boolean addNext = false;
		boolean addNextColor = false;
		int i = 0;
		for (char c : chars) {
			i = i + 1;
			if (addNextColor) {
				addNextColor = false;
				add = add + String.valueOf(c);
				addNext = true;
			} else if (addNext) {
				addNext = false;
				if (String.valueOf(c).equalsIgnoreCase("§")) {
					add = add + String.valueOf(c);
					addNextColor = true;
				} else {
					add = add + String.valueOf(c);
					output.add(add);
					add = "";
				}
			} else if (String.valueOf(c).equalsIgnoreCase("§")) {
				add = add + String.valueOf(c);
				addNextColor = true;
			} else {
				output.add(String.valueOf(c));
			}
		}
		if (add != "") {
			output.add(add);
		}
		String last = output.get(output.size() - 1);
		String secondLast = output.get(output.size() - 2);
		Pattern pattern = Pattern.compile("§([0123456789abcdefklmnor])", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher = pattern.matcher(last);
		int matches = 0;
		while (matcher.find()) {
			matches++;
		}
		if (matches * 2 == last.length()) {
			output.remove(output.size() - 1);
			output.remove(output.size() - 1);
			output.add(secondLast + last);
		}
		return output.toArray(new String[output.size()]);
	}
}

class Pointer extends BukkitRunnable {

	private TextRun st;
	private boolean pointer = false;
	
	Pointer(TextRun st, boolean pointer) {
		this.st = st;
		this.pointer = pointer;
	}

	@Override
	public void run() {
		if (pointer) {
			pointer = false;
			st.setPointer(false);
		} else {
			pointer = true;
			st.setPointer(true);
		}
	}
	
}

class Add extends BukkitRunnable {

	private TextRun st;
	private String currentOutput = "";
	private String[] currentParts;
	private int currentPart = -1;
	private int current = -1;
	
	Add(TextRun st, String[] currentParts) {
		this.st = st;
		this.currentParts = currentParts;
	}

	@Override
	public void run() {
		currentPart++;
		if (currentParts.length <= currentPart) {
			st.typewriterTimer(1);
			this.cancel();
		} else {
			if (current + 1 == currentPart) {
				currentOutput = currentOutput + currentParts[currentPart];
				current++;
			} else {
				String out = "";
				for (int i = 0; i <= currentPart; i++) {
					out = out + currentParts[i];
				}
				current = currentPart;
				currentOutput = out;
			}
			st.setOutput(currentOutput);
		}
	}
	
}

class Remove extends BukkitRunnable {

	private TextRun st;
	private int currentPart;
	private String[] currentParts;
	private String output = "";
	
	Remove(TextRun st, String[] currentParts) {
		this.st = st;
		this.currentParts = currentParts;
		this.currentPart = currentParts.length - 1;
	}

	@Override
	public void run() {
		currentPart--;
		if (currentPart <= -1) {
			st.setOutput("");
			st.typewriterTimer(3);
			this.cancel();
		} else {
			output = "";
			for (int i = 0; i <= currentPart; i++) {
				output = output + currentParts[i];
			}
			st.setOutput(output);
		}
	}
	
}

class PointerCountdown extends BukkitRunnable {

	private TextRun st;
	private long countdown;
	private long countdownTime;
	
	PointerCountdown(TextRun st, long countdownTime) {
		this.st = st;
		this.countdown = countdownTime;
		this.countdownTime = countdownTime;
	}

	@Override
	public void run() {
		countdown--;
		if (countdown == 0) {
			countdown = countdownTime;
		}
		st.setNextPointerChange(countdown);
	}
	
}

class Text extends BukkitRunnable {

	private TextRun st;
	private List<String> text;
	private int current = -1;
	
	Text(TextRun st, List<String> text) {
		this.st = st;
		this.text = text;
	}

	@Override
	public void run() {
		current++;
		if (text.size() <= current) {
			st.completed = true;
		} else {
			st.setOutput(text.get(current));
		}
	}
	
}