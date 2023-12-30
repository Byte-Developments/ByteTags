package org.sodemc.bytetags.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sodemc.bytetags.files.ByteTagsConfig;
import org.sodemc.bytetags.menus.ByteTagMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ByteTagsCommand implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");

            return true;
        }

        Player player = (Player) sender;

        if (args[0].equals("menu")) {
            ByteTagMenu.ByteTagOpen(player);
        }
        else if (args[0].equals("co")) {
            for (String LoopCoValue : ByteTagsConfig.getInstance().getValList("Tags").stream().toList()) {
                player.sendMessage(ByteTagsConfig.getInstance().getValue("Tags." + LoopCoValue + ".Material"));
                player.sendMessage(ByteTagsConfig.getInstance().getValue("Tags." + LoopCoValue + ".Name"));
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1) {
            return Arrays.asList("broadcast");
        }

        return new ArrayList<>();
    }
}