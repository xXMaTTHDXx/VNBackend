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
public class PermCommand extends RDCommand {
    protected PermCommand() {
        super("addperm");
    }

    @Override
    protected void handleCommand(Player player, String[] args) throws CommandException {
        //addperm [group] [perm]
        //addperm [group] [perm],[perm]

        if (args.length < 2) throw new ArgumentRequirementException(ChatColor.DARK_RED + "[group] [perms]");

        String name = args[0];

        VNGroup group = Core.getInstance().getGroupManager().getGroupByName(name);

        if (!Core.getInstance().getGroupManager().groupExists(group)) {
            player.sendMessage(ChatColor.DARK_RED + "Group does not exist!");
            return;
        }

        String[] perms = args[1].split(",");

        if (perms.length == 0) {
            //Single perm;

            group.addPermission(args[0]);
        }
        else {
            for (String perm : perms) {
                group.addPermission(perm);
            }
        }
        player.sendMessage(ChatColor.GREEN + "Added permissions to group: " + group.getRawName());
    }
}
