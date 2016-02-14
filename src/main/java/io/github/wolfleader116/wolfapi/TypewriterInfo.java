package io.github.wolfleader116.wolfapi;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.scheduler.BukkitRunnable;

public class TypewriterInfo extends ScoreboardLine {
	
	private long pointerDelay = 20;
	private char pointer = '_';
	private long endDelay = 100;
	private long startDelay = 20;
	private boolean pointerStatus = false;
	private String output = "";
	private String[] currentParts;
	private int currentStringNumber = 0;
	private int operation = 0;
	
	public void setData(long updateDelay, List<String> text, long pointerDelay, char pointer, long endDelay, long startDelay) {
		this.pointerDelay = pointerDelay;
		this.pointer = pointer;
		this.endDelay = endDelay;
		this.startDelay = startDelay;
		this.setUpdateDelay(updateDelay);
		this.setText(text);
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
	
	public int getOperation() {
		return this.operation;
	}
	
	public boolean getPointerStatus() {
		return this.pointerStatus;
	}
	
	void setOutput(String output) {
		this.output = output;
	}
	
	void setPointer(boolean pointerStatus) {
		this.pointerStatus = pointerStatus;
	}
	
	public String getOutput() {
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
	}
	
	public void startTimer() {
		timer();
	}
	
	public void setPosition(boolean pointerStatus) {
		timer(operation);
		BukkitRunnable pointer = new Pointer(this, pointerStatus);
		pointer.runTaskTimer(WolfAPI.plugin, this.pointerDelay, this.pointerDelay);
	}
	
	private void timer() {
		currentParts = getCurrentParts(this.getText().get(currentStringNumber));
		BukkitRunnable add = new Add(this, currentParts);
		add.runTaskTimer(WolfAPI.plugin, this.getUpdateDelay(), this.getUpdateDelay());
		BukkitRunnable pointer = new Pointer(this);
		pointer.runTaskTimer(WolfAPI.plugin, this.pointerDelay, this.pointerDelay);
		operation = 0;
	}
	
	void timer(int op) {
		if (op == 0) {
			BukkitRunnable run = new Add(this, currentParts);
			run.runTaskTimer(WolfAPI.plugin, this.startDelay, this.getUpdateDelay());
			operation = op;
		} else if (op == 1) {
			timer(2);
			operation = op;
		} else if (op == 2) {
			BukkitRunnable run = new Remove(this, currentParts);
			run.runTaskTimer(WolfAPI.plugin, this.endDelay, this.getUpdateDelay());
			operation = op;
		} else if (op == 3) {
			currentStringNumber++;
			if (this.getText().size() <= currentStringNumber) {
				currentStringNumber = 0;
			}
			currentParts = getCurrentParts(this.getText().get(currentStringNumber));
			operation = op;
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

}

class Pointer extends BukkitRunnable {

	private TypewriterInfo st;
	private boolean pointer = false;
	
	Pointer(TypewriterInfo st) {
		this.st = st;
	}
	
	Pointer(TypewriterInfo st, boolean pointer) {
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

	private TypewriterInfo st;
	private String currentOutput = "";
	private String[] currentParts;
	private int currentPart = -1;
	private int current = -1;
	
	Add(TypewriterInfo st, String[] currentParts) {
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

	private TypewriterInfo st;
	private int currentPart;
	private String[] currentParts;
	private String output = "";
	
	Remove(TypewriterInfo st, String[] currentParts) {
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