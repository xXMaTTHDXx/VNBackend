package io.matthd.core.player.group.commands;

import io.matthd.core.Core;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import tech.rayline.core.command.CommandException;
import tech.rayline.core.command.RDCommand;

/**
 * Created by Matt on 2017-01-31.
 */
public class GroupCommand extends RDCommand {


    protected GroupCommand() {
        super("group", new PermCommand(), new SetCommand(), new CreateCommand());
    }

    @Override
    protected void handleCommand(Player player, String[] args) throws CommandException {

        if (!Core.getInstance().getGroupManager().hasPermission(player, "core.groups")) {
            player.sendMessage(ChatColor.DARK_RED + "Not enough permissions");
            return;
        }
    }
}
