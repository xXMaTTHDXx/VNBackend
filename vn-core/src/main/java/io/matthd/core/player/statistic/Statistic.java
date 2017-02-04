package io.matthd.core.player.statistic;

import org.bukkit.entity.Player;
import rx.Observable;

/**
 * Created by Matt on 2017-01-29.
 */
public interface Statistic {

    String getName();
    void increment(int amount);
    Observable<Player> observe();
}
