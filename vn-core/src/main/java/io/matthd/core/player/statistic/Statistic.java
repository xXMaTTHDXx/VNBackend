package io.matthd.core.player.statistic;

import org.bukkit.entity.Player;
import rx.Observable;

/**
 * Created by Matt on 2017-01-29.
 */
public interface Statistic {

    String getName();
    Observable<Player> observe();
    void increment(Player player);

    Object getValue();
    void setValue(Object value);
}
