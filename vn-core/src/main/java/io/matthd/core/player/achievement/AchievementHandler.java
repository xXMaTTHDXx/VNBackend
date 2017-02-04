package io.matthd.core.player.achievement;

import io.matthd.core.Core;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 2017-01-29.
 */
public class AchievementHandler {
    private List<Achievement> allAchievements = new ArrayList<>();

    public AchievementHandler() {
        init();
    }

    public Achievement getFromString(String name) {
        for (Achievement a : allAchievements) {
            if (a.getName().equalsIgnoreCase(name)) {
                return a;
            }
        }
        return null;
    }

    public void init() {
        allAchievements.clear();
        allAchievements.add(new FirstJoinAchievement());
    }

    public boolean hasAchievement(Player player, Achievement achievement) {
        System.out.println(player == null);
        System.out.println(achievement == null);
        System.out.println(Core.getInstance().getPlayerManager() == null);
        System.out.println(Core.getInstance().getPlayerManager().getPlayer(player) == null);
        System.out.println(Core.getInstance().getPlayerManager().getPlayer(player).getData() == null);
        System.out.println(Core.getInstance().getPlayerManager().getPlayer(player).getData().getAchievements() == null);

        return Core.getInstance().getPlayerManager().getPlayer(player).getData().getAchievements().contains(achievement);
    }

    public void addAchievement(Achievement achievement) {
        if (allAchievements.contains(achievement)) {
            return;
        }

        allAchievements.add(achievement);
    }
}
