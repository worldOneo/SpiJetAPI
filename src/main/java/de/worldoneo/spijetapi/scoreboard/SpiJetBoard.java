package de.worldoneo.spijetapi.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class SpiJetBoard {

    private final Scoreboard scoreboard;
    private final Objective objective;

    public SpiJetBoard(String title) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("Board", "dummy");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        setDisplayName(title);
    }

    public String getDisplayName() {
        return objective.getDisplayName();
    }

    public void setDisplayName(String displayName) {
        objective.setDisplayName(displayName);
    }

    public void setEntry(int score, String text) {
        for (String entry : scoreboard.getEntries()) {
            if (objective.getScore(entry).getScore() == score) {
                if (entry.equals(text)) {
                    return;
                }
                scoreboard.resetScores(entry);
                break;
            }
        }
        objective.getScore(text).setScore(score);
    }

    public int addEntry(String text) {
        int i = scoreboard.getEntries().size();
        setEntry(i, text);
        return i;
    }

    public void removeEntry(int score) {
        for (String entry : scoreboard.getEntries()) {
            if (objective.getScore(entry).getScore() == score) {
                scoreboard.resetScores(entry);
                break;
            }
        }
    }

    public void removeEntry(String string) {
        scoreboard.resetScores(string);
    }

    public void clear() {
        for (String entry : scoreboard.getEntries()) {
            scoreboard.resetScores(entry);
        }
    }

    public Set<String> getEntries() {
        return Collections.unmodifiableSet(scoreboard.getEntries());
    }

    public void send(Player... players) {
        if (players != null) {
            send(Arrays.asList(players));
        }
    }

    public void send(List<Player> players) {
        for (Player player : players) {
            player.setScoreboard(scoreboard);
        }
    }
}
