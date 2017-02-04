package io.matthd.core.player.group.commands;

import io.matthd.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import tech.rayline.core.command.ArgumentRequirementException;
import tech.rayline.core.command.CommandException;
import tech.rayline.core.command.RDCommand;

/**
 * Created by Matt on 2017-02-02.
 */
public class GroupAddCommand extends RDCommand {


    protected GroupAddCommand() {
        super("add");
    }

    @Override
    protected void handleCommand(Player player, String[] args) throws CommandException {
        //add [user] [group]

        if (args.length != 2) throw new ArgumentRequirementException("[user] [group]");

        String userName = args[0];
        String groupName = args[1];

        if (!Core.getInstance().getGroupManager().groupExists(Core.getInstance().getGroupManager().getGroupByName(groupName))) {
            player.sendMessage(ChatColor.DARK_RED + "Group does not exist!");
            return;
        }

        Player pl = Bukkit.getPlayer(userName);
        if (pl == null) {
            player.sendMessage(ChatColor.DARK_RED + "Player is not online!");
            return;
        }

        Core.getInstance().getPlayerManager().getPlayer(pl).getData().setGroup(Core.getInstance().getGroupManager().getGroupByName(groupName));
        pl.sendMessage(ChatColor.GREEN + "Your group has been changed too: " + groupName);
    }
}
