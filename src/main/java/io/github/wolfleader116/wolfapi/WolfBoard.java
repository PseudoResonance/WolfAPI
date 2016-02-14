package io.github.wolfleader116.wolfapi;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class WolfBoard {
	
	private static Map<String, Map<Integer, ScoreboardLine>> lines = new HashMap<String, Map<Integer, ScoreboardLine>>();
	private static Map<Integer, ScoreboardLine> rawLines = new HashMap<Integer, ScoreboardLine>();
	private static Map<UUID, Integer> currentBlanks = new HashMap<UUID, Integer>();
	private static Map<UUID, Integer> currentBlankCounts = new HashMap<UUID, Integer>();
	private static List<String> title = new ArrayList<String>();
	private static String currentTitle = "";
	private static Map<String, PlayerScoreboardData> scoreboards = new HashMap<String, PlayerScoreboardData>();
	private static Map<String, Map<Integer, String>> text = new HashMap<String, Map<Integer, String>>();
	
	protected static void setLine(int line, Player p, String text) {
		scoreboards.get(p.getName()).getScoreboard().resetScores(WolfBoard.text.get(p.getName()).get(line));
		scoreboards.get(p.getName()).getObjective().getScore(text).setScore(line);
		WolfBoard.text.get(p.getName()).put(line, text);
	}
	
	public static void setVisibility(Player p, boolean visibility) {
		scoreboards.get(p.getName()).setVisibility(visibility);
	}
	
	public static void startClock(int line, String player) {
		Map<Integer, ScoreboardLine> lines = WolfBoard.lines.get(player);
		scoreboards.get(player).getScoreboard().resetScores(player);
		ScoreboardLine sl = lines.get(line);
		if (sl instanceof TypewriterInfo) {
			TypewriterInfo ti = (TypewriterInfo) sl;
			TextRun tr = ((TypewriterInfo) sl).startTimer(Bukkit.getServer().getPlayer(player));
			BukkitRunnable typewriterUpdate = new LineUpdate(tr, line, player);
			typewriterUpdate.runTaskTimer(WolfAPI.plugin, 0, ti.getUpdateDelay());
		}
	}
	
	public static void startClocks(String player) {
		Map<Integer, ScoreboardLine> lines = WolfBoard.lines.get(player);
		scoreboards.get(player).getScoreboard().resetScores(player);
		for (int i : lines.keySet()) {
			ScoreboardLine sl = lines.get(i);
			if (sl instanceof TypewriterInfo) {
				TypewriterInfo ti = (TypewriterInfo) sl;
				TextRun tr = ((TypewriterInfo) sl).startTimer(Bukkit.getServer().getPlayer(player));
				BukkitRunnable typewriterUpdate = new LineUpdate(tr, i, player);
				typewriterUpdate.runTaskTimer(WolfAPI.plugin, 0, ti.getUpdateDelay());
			}
		}
	}
	
	public static void setupScoreboard(Player p) {
		Scoreboard sc = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		Objective ob = sc.getObjective("wolfapi");
		ob.setDisplayName(currentTitle);
		if (!(new Config(WolfAPI.plugin.getDataFolder() + File.pathSeparator + "playerdata" + File.pathSeparator + WolfAPI.getDataFile(p.getName()), WolfAPI.plugin).getConfig().getBoolean("settings.scoreboard"))) {
			scoreboards.put(p.getName(), new PlayerScoreboardData(sc, ob, false));
		} else {
			scoreboards.put(p.getName(), new PlayerScoreboardData(sc, ob, true));
		}
		p.setScoreboard(sc);
	}
	
	public static void setRawLines(Map<Integer, ScoreboardLine> rawLines) {
		WolfBoard.rawLines = rawLines;
		Map<String, Map<Integer, ScoreboardLine>> output = new HashMap<String, Map<Integer, ScoreboardLine>>();
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			output.put(p.getName(), rawLines);
		}
		lines = output;
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			setupScoreboard(p);
		}
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			startClocks(p.getName());
		}
	}
	
	protected static void setCurrentTitle(String currentTitle) {
		WolfBoard.currentTitle = currentTitle;
		for (String player : scoreboards.keySet()) {
			scoreboards.get(player).getObjective().setDisplayName(currentTitle);
		}
	}
	
	public static void setTitle(List<String> title) {
		WolfBoard.title = title;
	}
	
	public static List<String> getTitle() {
		return WolfBoard.title;
	}
	
	public static void addPlayer(Player p) {
		lines.put(p.getName(), rawLines);
		setupScoreboard(p);
		startClocks(p.getName());
	}
	
	public static void removePlayer(String p) {
		lines.remove(p);
		scoreboards.remove(p);
	}
	
	public static String getNextBlank(UUID p) {
		if (!(currentBlanks.containsKey(p))) {
			currentBlanks.put(p, -1);
		}
		if (!(currentBlankCounts.containsKey(p))) {
			currentBlankCounts.put(p, 1);
		}
		currentBlanks.put(p, currentBlanks.get(p) + 1);
		if (currentBlanks.get(p) >= 22) {
			currentBlankCounts.put(p, currentBlankCounts.get(p) + 1);
			currentBlanks.put(p, 0);
		}
		if (currentBlankCounts.get(p) >= 17) {
			currentBlankCounts.put(p, 1);
		}
		String output = "";
		for (int i = 1; i <= currentBlankCounts.get(p); i++) {
			switch(currentBlanks.get(p)) {
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

class TitleUpdate extends BukkitRunnable {

	private List<String> title;
	private int current = -1;
	
	TitleUpdate(List<String> title) {
		this.title = title;
	}
	
	TitleUpdate(List<String> title, int current) {
		this.title = title;
		this.current = current;
	}

	@Override
	public void run() {
		current++;
		String output = title.get(current);
		if (output.equalsIgnoreCase("")) {
			output = ChatColor.RESET.toString();
		} else {
			if (output.length() >= 32) {
				output = output.substring(0, 31);
			}
		}
		WolfBoard.setCurrentTitle(output);
	}
	
}

class LineUpdate extends BukkitRunnable {

	private TextRun tr;
	private int line = 0;
	private String p = "";
	
	LineUpdate(TextRun tr, int line, String p) {
		this.tr = tr;
		this.line = line;
		this.p = p;
	}

	@Override
	public void run() {
		String newText = tr.getOutput();
		if (newText.length() >= 32) {
			newText = newText.substring(0, 31);
		}
		WolfBoard.setLine(line, Bukkit.getServer().getPlayer(p), newText);
		if (tr.isCompleted()) {
			WolfBoard.startClock(line, p);
		}
	}
	
}
