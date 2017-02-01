package io.matthd.core.player.group.commands;

import io.matthd.core.Core;
import io.matthd.core.player.group.VNGroup;
import io.matthd.core.player.group.mongo.MongoGroup;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import tech.rayline.core.command.ArgumentRequirementException;
import tech.rayline.core.command.CommandException;
import tech.rayline.core.command.RDCommand;

import java.util.ArrayList;

/**
 * Created by Matt on 2017-01-31.
 */
public class CreateCommand extends RDCommand {

    protected CreateCommand() {
        super("create");
    }

    @Override
    protected void handleCommand(Player player, String[] args) throws CommandException {
        //create [group]

        if (args.length < 1) throw new ArgumentRequirementException(ChatColor.DARK_RED + "[group]");

        String name = args[0];

        VNGroup group = Core.getInstance().getGroupManager().getGroupByName(name);

        if (Core.getInstance().getGroupManager().groupExists(group)) {
            player.sendMessage(ChatColor.DARK_RED + "Group already exists!");
            return;
        }
        Core.getInstance().getGroupManager().createGroup(new MongoGroup(name, "", "", new ArrayList<>(), ""));
        player.sendMessage(ChatColor.GREEN + "Created group " + name + "!");
    }
}
