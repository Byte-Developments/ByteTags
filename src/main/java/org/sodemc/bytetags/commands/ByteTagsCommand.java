package org.sodemc.bytetags.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sodemc.bytetags.files.ByteTagDatabase;
import org.sodemc.bytetags.files.ByteTagsConfig;
import org.sodemc.bytetags.menus.ByteTagMenu;
import org.sodemc.bytetags.util.TagsItemFinal;

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

        if (Arrays.stream(args).toList().isEmpty()) {
            player.sendMessage(Component.text("Bad Arguments.", NamedTextColor.RED));
            return true;
        }

        if (args[0].equals("menu")) {
            ByteTagMenu.OpenTagMenu(player);
        }
        else if (args[0].equals("db")) {
            ByteTagDatabase.insertData("AIRobotics_", "TestAmeth", player.getUniqueId().toString());
            //ByteTagDatabase.updateData("Tag456", "UUID123", "Tag");
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