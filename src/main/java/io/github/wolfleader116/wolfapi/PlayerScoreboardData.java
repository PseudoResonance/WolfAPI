package io.github.wolfleader116.wolfapi;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class PlayerScoreboardData {
	
	private final Scoreboard sc;
	private final Objective ob;
	private boolean visibility;
	
	PlayerScoreboardData(Scoreboard sc, Objective ob, boolean visibility) {
		this.sc = sc;
		this.ob = ob;
		this.visibility = visibility;
		if (visibility) {
			this.ob.setDisplaySlot(DisplaySlot.SIDEBAR);
		} else {
			Long delay = WolfAPI.plugin.getConfig().getLong("ScoreboardDelay");
			if (delay == 0) {
				this.sc.clearSlot(DisplaySlot.SIDEBAR);
			} else if (delay > 0) {
				BukkitRunnable dt = new DisplayTimer(this);
				dt.runTaskLater(WolfAPI.plugin, delay);
			}
		}
	}
	
	public Scoreboard getScoreboard() {
		return this.sc;
	}
	
	public Objective getObjective() {
		return this.ob;
	}
	
	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
		if (visibility) {
			this.ob.setDisplaySlot(DisplaySlot.SIDEBAR);
		} else {
			Long delay = WolfAPI.plugin.getConfig().getLong("ScoreboardDelay");
			if (delay == 0) {
				this.sc.clearSlot(DisplaySlot.SIDEBAR);
			} else if (delay > 0) {
				BukkitRunnable dt = new DisplayTimer(this);
				dt.runTaskLater(WolfAPI.plugin, delay);
			}
		}
	}
	
	public void update() {
		if (visibility) {
			this.ob.setDisplaySlot(DisplaySlot.SIDEBAR);
		} else {
			Long delay = WolfAPI.plugin.getConfig().getLong("ScoreboardDelay");
			if (delay == 0) {
				this.sc.clearSlot(DisplaySlot.SIDEBAR);
			} else if (delay > 0) {
				this.ob.setDisplaySlot(DisplaySlot.SIDEBAR);
				BukkitRunnable dt = new DisplayTimer(this);
				dt.runTaskLater(WolfAPI.plugin, delay);
			}
		}
	}
	
	public boolean getVisibility() {
		return this.visibility;
	}

}

class DisplayTimer extends BukkitRunnable {

	private PlayerScoreboardData psd;
	
	DisplayTimer(PlayerScoreboardData psd) {
		this.psd = psd;
	}

	@Override
	public void run() {
		this.psd.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
	}
	
}
