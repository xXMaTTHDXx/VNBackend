package io.matthd.core.player.statistic;

import io.matthd.core.Core;
import io.matthd.core.player.VNPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 2017-01-29.
 */
public class StatisticHandler {

    private List<Statistic> allStatistics = new ArrayList<>();

    private Core plugin;

    public StatisticHandler(Core plugin) {
        this.plugin = plugin;
        //TODO add achievements
    }

    public List<Statistic> getAllAchievements() {
        return allStatistics;
    }

    public Statistic getByName(String name) {
        for (Statistic a : allStatistics) {
            if (a.getName().equalsIgnoreCase(name)) {
                return a;
            }
        }
        return null;
    }

    public List<Statistic> deserialize(String statisticString) {
        List<Statistic> playerStatistics = new ArrayList<>();
        String[] split = statisticString.split(",");

        for (String name : split) {
            Statistic statistic = getByName(name);
            Object value = name.split(":")[1];

            if (statistic != null) {
                statistic.setValue(value);
                playerStatistics.add(statistic);
            }
        }
        return playerStatistics;
    }

    public String serialize(List<Statistic> statistics) {
        StringBuilder builder = new StringBuilder();

        for (Statistic statistic : statistics) {
            builder.append(statistic.getName()).append(":").append(statistic.getValue()).append(",");
        }
        return builder.toString();
    }

    public boolean hasStatistic(Player player, Statistic statistic) {
        VNPlayer gamePlayer = plugin.getPlayerManager().getPlayer(player);
        return gamePlayer.getData().getStatistics().contains(statistic);
    }

    public Object getStatisticValue(Player player, Statistic statistic) {
        VNPlayer gamePlayer = plugin.getPlayerManager().getPlayer(player);
        return gamePlayer.getData().getStatistics().get(gamePlayer.getData().getStatistics().indexOf(statistic)).getValue();
    }
}
