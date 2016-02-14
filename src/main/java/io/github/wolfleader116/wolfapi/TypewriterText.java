package io.github.wolfleader116.wolfapi;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/*
 * Scoreboard typewriter effect API
 * @author WolfLeader116
 */

public class TypewriterText {

	private char pointerChar;
	private long updateDelay;
	private long pointerDelay;
	private long endDelay;
	private long startDelay;
	private JavaPlugin plugin;
	private boolean pointer = false;
	protected List<String> input;
	private int current = 0;
	private String currentOutput = "";
	private String[] currentParts;
	
	/*
	 * ScoreboardTypewriter object
	 * @param plugin Main plugin class
	 * @param input List of strings to be used
	 * @param pointerChar Char that flashes at end of string (pointer)
	 * @param updateDelay Delay between each time a new character is added/removed
	 * @param pointerDelay Delay between updating the pointer
	 * @param endDelay Delay between finishing adding every character to the string and starting to remove characters
	 * @param startDelay Delay between finishing removing every character to the string and starting to add characters for the next string
	*/
	public TypewriterText(JavaPlugin plugin, List<String> input, char pointerChar, long updateDelay, long pointerDelay, long endDelay, long startDelay) {
		this.plugin = plugin;
		this.input = input;
		this.pointerChar = pointerChar;
		this.updateDelay = updateDelay;
		this.pointerDelay = pointerDelay;
		this.endDelay = endDelay;
		this.startDelay = startDelay;
		if (this.pointerDelay == 0) {
			this.pointerDelay = 1;
		}
		timer();
	}
	
	/*
	 * Gets the current output
	 */
	public String current() {
		if (currentOutput.equals("")) {
			if (pointer) {
				return String.valueOf(pointerChar);
			} else {
				return "";
			}
		} else {
			if (pointer) {
				return currentOutput + pointerChar;
			} else {
				return currentOutput;
			}
		}
	}
	
	private void timer() {
		currentParts = getCurrentParts(input.get(current));
		BukkitRunnable add = new TypewriterAdd(this, currentParts);
		add.runTaskTimer(plugin, this.updateDelay, this.updateDelay);
		BukkitRunnable pointer = new TypewriterPointer(this);
		pointer.runTaskTimer(plugin, this.pointerDelay, this.pointerDelay);
	}
	
	void timer(int op) {
		if (op == 0) {
			BukkitRunnable run = new TypewriterAdd(this, currentParts);
			run.runTaskTimer(plugin, this.startDelay, this.updateDelay);
		} else if (op == 1) {
			timer(2);
		} else if (op == 2) {
			BukkitRunnable run = new TypewriterRemove(this, currentParts);
			run.runTaskTimer(plugin, this.endDelay, this.updateDelay);
		} else if (op == 3) {
			current++;
			if (input.size() <= current) {
				current = 0;
			}
			currentParts = getCurrentParts(input.get(current));
			timer(0);
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
	
	void setOutput(String output) {
		currentOutput = output;
		ScoreboardOLD.update();
	}
	
	void setPointer(boolean pointer) {
		this.pointer = pointer;
		ScoreboardOLD.update();
	}

}

class TypewriterPointer extends BukkitRunnable {

	private TypewriterText st;
	private boolean pointer = false;
	
	TypewriterPointer(TypewriterText st) {
		this.st = st;
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

class TypewriterAdd extends BukkitRunnable {

	private TypewriterText st;
	private String currentOutput = "";
	private String[] currentParts;
	private int currentPart = -1;
	
	TypewriterAdd(TypewriterText st, String[] currentParts) {
		this.st = st;
		this.currentParts = currentParts;
	}

	@Override
	public void run() {
		currentPart++;
		if (currentParts.length <= currentPart) {
			st.timer(1);
			this.cancel();
		} else {
			currentOutput = currentOutput + currentParts[currentPart];
			st.setOutput(currentOutput);
		}
	}
	
}

class TypewriterRemove extends BukkitRunnable {

	private TypewriterText st;
	private int currentPart;
	private String[] currentParts;
	private String output = "";
	
	TypewriterRemove(TypewriterText st, String[] currentParts) {
		this.st = st;
		this.currentParts = currentParts;
		this.currentPart = currentParts.length - 1;
	}

	@Override
	public void run() {
		currentPart--;
		if (currentPart <= -1) {
			st.setOutput("");
			st.timer(3);
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