package io.matthd.core.player.achievement;

import io.matthd.core.Core;
import io.matthd.core.player.VNPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 2017-01-29.
 */
public class AchievementHandler {
    private List<Achievement> allAchievements = new ArrayList<>();

    private Core plugin;

    public AchievementHandler(Core plugin) {
        this.plugin = plugin;
        //TODO add achievements
    }

    public List<Achievement> getAllAchievements() {
        return allAchievements;
    }

    public Achievement getByName(String name) {
        for (Achievement a : allAchievements) {
            if (a.getName().equalsIgnoreCase(name)) {
                return a;
            }
        }
        return null;
    }

    public List<Achievement> deserialize(String achievementString) {
        List<Achievement> playerAchievements = new ArrayList<>();
        String[] split = achievementString.split(",");

        for (String name : split) {
            Achievement achievement = getByName(name);

            if (achievement != null) {
                playerAchievements.add(achievement);
            }
        }
        return playerAchievements;
    }

    public String serialize(List<Achievement> achievements) {
        StringBuilder builder = new StringBuilder();

        for (Achievement achievement : achievements) {
            builder.append(achievement.getName()).append(",");
        }
        return builder.toString();
    }

    public boolean hasAchievement(Player player, Achievement achievement) {
        VNPlayer gamePlayer = plugin.getPlayerManager().getPlayer(player);
        return gamePlayer.getData().getAchievements().contains(achievement);
    }
}
