package io.matthd.core.player.achievement;

import io.matthd.core.Core;
import io.matthd.core.events.NetworkJoinEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import rx.Observable;

/**
 * Created by Matt on 2017-02-01.
 */
public class FirstJoinAchievement implements Achievement {

    public FirstJoinAchievement() {
        observe();
    }

    @Override
    public String getName() {
        return "First Join";
    }

    @Override
    public Observable<Player> observe() {
        Observable<Player> observable;

        observable = Core.getInstance().observeEvent(NetworkJoinEvent.class)
                .filter(event -> !Core.getInstance().getAchievementHandler().hasAchievement(event.getPlayer(), this))
                .map(NetworkJoinEvent::getPlayer);

        observable.subscribe(this::onAchieve);
        return observable;
    }

    @Override
    public void onAchieve(Player player) {
        player.sendMessage(ChatColor.GREEN + ">> Achievement Unlocked: " + ChatColor.GOLD + getName() + ChatColor.GREEN + " <<");
        Core.getInstance().getPlayerManager().getPlayer(player).getData().getAchievements().add(this);
    }
}
