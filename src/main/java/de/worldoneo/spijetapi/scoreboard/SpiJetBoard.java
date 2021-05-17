package de.worldoneo.spijetapi.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class SpiJetBoard {
    private final Scoreboard scoreboard;
    private final Objective objective;


    @SuppressWarnings("deprecation")
    //Scoreboard#registerNewObjective(String, String) is the only method available in legacy.
    public SpiJetBoard(String title) {
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        if (scoreboardManager == null)
            throw new IllegalStateException("No ScoreboardManager available as no worlds are loaded.");
        this.scoreboard = scoreboardManager.getNewScoreboard();
        this.objective = this.scoreboard.registerNewObjective("Board", "dummy");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.setDisplayName(title);
    }

    public String getDisplayName() {
        return this.objective.getDisplayName();
    }

    public void setDisplayName(String displayName) {
        this.objective.setDisplayName(displayName);
    }

    public void setEntry(int score, String text) {
        for (String entry : this.scoreboard.getEntries()) {
            if (this.objective.getScore(entry).getScore() == score) {
                if (entry.equals(text)) {
                    return;
                }
                this.scoreboard.resetScores(entry);
                break;
            }
        }
        this.objective.getScore(text).setScore(score);
    }

    public int addEntry(String text) {
        int i = this.scoreboard.getEntries().size();
        this. setEntry(i, text);
        return i;
    }

    public void removeEntry(int score) {
        for (String entry : this.scoreboard.getEntries()) {
            if (this.objective.getScore(entry).getScore() == score) {
                this.scoreboard.resetScores(entry);
                break;
            }
        }
    }

    public void removeEntry(String string) {
        this.scoreboard.resetScores(string);
    }

    public void clear() {
        for (String entry : this.scoreboard.getEntries()) {
            this.scoreboard.resetScores(entry);
        }
    }

    public Set<String> getEntries() {
        return this.scoreboard.getEntries();
    }

    public void send(Player... players) {
        if (players != null) {
            this.send(Arrays.asList(players));
        }
    }

    public void send(List<Player> players) {
        for (Player onlinePlayer : players) {
            onlinePlayer.setScoreboard(this.scoreboard);
        }
    }
}
