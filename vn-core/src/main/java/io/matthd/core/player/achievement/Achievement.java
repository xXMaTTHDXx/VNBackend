package io.matthd.core.player.achievement;

import org.bukkit.entity.Player;
import rx.Observable;

/**
 * Created by Matt on 2017-01-29.
 */
public interface Achievement {

    String getName();
    Observable<Player> observe();
    void onAchieve(Player player);
}
