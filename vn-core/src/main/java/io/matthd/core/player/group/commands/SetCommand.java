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
public class SetCommand extends RDCommand {

    protected SetCommand() {
        super("set");
    }

    @Override
    protected void handleCommand(Player player, String[] args) throws CommandException {
        //set [group] <what> [val]

        if (args.length < 3) throw new ArgumentRequirementException(ChatColor.DARK_RED + "[group] [toSet] [value]");

        String groupName = args[0];
        String toSet = args[1].toLowerCase();
        String value = args[2];

        VNGroup group = Core.getInstance().getGroupManager().getGroupByName(groupName);

        if (!Core.getInstance().getGroupManager().groupExists(group)) return;

        switch (toSet) {
            case "prefix":
                group.setPrefix(value);
                player.sendMessage(ChatColor.GREEN + "Set " + group.getRawName() + "'s Prefix to: " + group.getPrefix());
                break;
            case "suffix":
                group.setSuffix(value);
                player.sendMessage(ChatColor.GREEN + "Set " + group.getRawName() + "'s Suffix to: " + group.getSuffix());
                break;
        }
    }
}
