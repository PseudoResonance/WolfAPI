package io.github.wolfleader116.wolfapi;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

public class ScoreboardOLD {
	
	private static String title = "";
	private static Map<String, List<String>> info = new HashMap<String, List<String>>();
	private static List<String> notify = new ArrayList<String>();
	private static org.bukkit.scoreboard.Scoreboard sc = Bukkit.getScoreboardManager().getNewScoreboard();
	private static String song = "";
	private static Map<String, Objective> objectives = new HashMap<String, Objective>();
	private static List<TypewriterText> tt = new ArrayList<TypewriterText>();
	
	protected static void setTitle(String title) {
		ScoreboardOLD.title = title;
		update();
	}
	
	protected static void setInfo(Map<String, List<String>> info) {
		ScoreboardOLD.info = info;
		update();
	}
	
	public static void setSong(String song) {
		ScoreboardOLD.song = song;
		update();
	}
	
	public static void update(Player p) {
		playerUpdate(p);
	}
	
	public static void update() {
		for (String player : notify) {
			final Player p = Bukkit.getServer().getPlayer(player);
			playerUpdate(p);
		}
	}
	
	private static void playerUpdate(final Player p) {
		if (p != null) {
			if (!(objectives.containsKey(p.getName()))) {
				objectives.put(p.getName(), sc.registerNewObjective("wolfapi", "dummy"));
			}
			Objective o = objectives.get(p.getName());
			Pattern pattern = Pattern.compile("&([0123456789abcdefklmnor])", Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(title);
			String outputTitle = matcher.replaceAll("§$1");
			if (outputTitle.length() > 32) {
				outputTitle = outputTitle.substring(0, 31);
			}
			o.setDisplayName(outputTitle);
			o.setDisplaySlot(DisplaySlot.SIDEBAR);
			int current = info.size();
			int blank = -1;
			for (String key : info.keySet()) {
				if (WolfAPI.plugin.getConfig().getString("Scoreboard." + key + ".Type") == null || WolfAPI.plugin.getConfig().getString("Scoreboard." + key + ".Type").equalsIgnoreCase("Text")) {
					String i = WolfAPI.plugin.getConfig().getStringList("Scoreboard." + key + ".Text").get(0);
					current--;
					String output = i;
					if (i.equalsIgnoreCase("")) {
						blank++;
						switch(blank) {
						case 0:
							output = "§0";
						case 1:
							output = "§1";
						case 2:
							output = "§2";
						case 3:
							output = "§3";
						case 4:
							output = "§4";
						case 5:
							output = "§5";
						case 6:
							output = "§6";
						case 7:
							output = "§7";
						case 8:
							output = "§8";
						case 9:
							output = "§9";
						case 10:
							output = "§a";
						case 11:
							output = "§b";
						case 12:
							output = "§c";
						case 13:
							output = "§d";
						case 14:
							output = "§e";
						case 15:
							output = "§f";
						case 16:
							output = "§r";
						default:
							output = "";
						}
					}
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
					output = output.replaceAll("%players%", String.valueOf(players));
					output = output.replaceAll("%staff%", String.valueOf(staff));
					output = output.replaceAll("%song%", song);
					output = output.replaceAll("%money%", String.valueOf(WolfAPI.economy.getBalance(p)));
					if (output.length() > 32) {
						output = output.substring(0, 31);
					}
					Score add = o.getScore(output);
					add.setScore(current);
				} else if (WolfAPI.plugin.getConfig().getString("Scoreboard." + key + ".Type").equalsIgnoreCase("TypeWriter")) {
					current--;
					List<String> list = WolfAPI.plugin.getConfig().getStringList("Scoreboard." + key + ".Text");
					List<String> outputList = new ArrayList<String>();
					for (String out : list) {
						String output = out;
						if (out.equalsIgnoreCase("")) {
							blank++;
							switch(blank) {
							case 0:
								output = "§0";
							case 1:
								output = "§1";
							case 2:
								output = "§2";
							case 3:
								output = "§3";
							case 4:
								output = "§4";
							case 5:
								output = "§5";
							case 6:
								output = "§6";
							case 7:
								output = "§7";
							case 8:
								output = "§8";
							case 9:
								output = "§9";
							case 10:
								output = "§a";
							case 11:
								output = "§b";
							case 12:
								output = "§c";
							case 13:
								output = "§d";
							case 14:
								output = "§e";
							case 15:
								output = "§f";
							case 16:
								output = "§r";
							default:
								output = "";
							}
						}
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
						output = output.replaceAll("%players%", String.valueOf(players));
						output = output.replaceAll("%staff%", String.valueOf(staff));
						output = output.replaceAll("%song%", song);
						output = output.replaceAll("%money%", String.valueOf(WolfAPI.economy.getBalance(p)));
						if (output.length() > 32) {
							output = output.substring(0, 31);
						}
						outputList.add(output);
					}
					boolean newT = true;
					for (TypewriterText ty : tt) {
						if (ty.input.containsAll(outputList)) {
							newT = true;
							break;
						} else {
							newT = false;
						}
					}
					if (newT) {
						TypewriterText t = new TypewriterText(WolfAPI.plugin, outputList, WolfAPI.plugin.getConfig().getString("Pointer").toCharArray()[0], WolfAPI.plugin.getConfig().getInt("CharacterChangeDelay"), WolfAPI.plugin.getConfig().getInt("PointerDelay"), WolfAPI.plugin.getConfig().getInt("EndDelay"), WolfAPI.plugin.getConfig().getInt("StartDelay"));
						tt.add(current, newT);
					}
					String output = t.current();
					if (output.length() > 32) {
						output = output.substring(0, 31);
					}
					Score add = o.getScore(output);
					add.setScore(current);
				}
			}
			if (!(new Config(WolfAPI.plugin.getDataFolder() + File.pathSeparator + "playerdata" + File.pathSeparator + WolfAPI.getDataFile(p.getName()), WolfAPI.plugin).getConfig().getBoolean("settings.scoreboard"))) {
				int d = WolfAPI.plugin.getConfig().getInt("ScoreboardDelay");
				if (d == 0) {
					p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
				} else {
					p.setScoreboard(sc);
					Bukkit.getScheduler().scheduleSyncDelayedTask(WolfAPI.plugin, new Runnable() {
						public void run() {
							if (p != null) {
								p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
							}
						}
					}, d);
				}
			}
		}
	}

}
