package io.matthd.core.player.group.commands;

import io.matthd.core.Core;
import io.matthd.core.player.group.VNGroup;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import tech.rayline.core.command.ArgumentRequirementException;
import tech.rayline.core.command.CommandException;
import tech.rayline.core.command.RDCommand;

/**
 * Created by Matt on 2017-01-31.
 */
public class DeleteCommand extends RDCommand {

    protected DeleteCommand() {
        super("delete");
    }

    @Override
    protected void handleCommand(Player player, String[] args) throws CommandException {
        if (args.length < 1) throw new ArgumentRequirementException(ChatColor.DARK_RED + "[group]");

        String name = args[0];

        VNGroup group = Core.getInstance().getGroupManager().getGroupByName(name);

        if (!Core.getInstance().getGroupManager().groupExists(group)) {
            player.sendMessage(ChatColor.DARK_RED + "Group does not exist!");
            return;
        }
        Core.getInstance().getGroupManager().deleteGroup(group);
        player.sendMessage(ChatColor.GREEN + "Deleted group " + name + "!");
    }
}
