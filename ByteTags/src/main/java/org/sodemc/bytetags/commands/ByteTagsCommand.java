package org.sodemc.bytetags.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.lucko.commodore.Commodore;
import me.lucko.commodore.CommodoreProvider;
import me.lucko.commodore.file.CommodoreFileReader;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sodemc.bytetags.files.ByteTagDatabase;
import org.sodemc.bytetags.files.ByteTagsConfig;
import org.sodemc.bytetags.menus.ByteTagMenu;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;
import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

public final class ByteTagsCommand implements CommandExecutor, TabExecutor {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 3) {
            return ByteTagsConfig.getInstance().getValList("Tags")
                .stream()
                .map(l -> "TAGS." + l)
                .collect(Collectors.toList()
            );
        }

        return null;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;

        Component InvalidArg = Component.text("Invalid arguments provided.", NamedTextColor.RED);
        Component InvalidPlayer = Component.text("Invalid player provided.", NamedTextColor.RED);

        TextComponent NoPermMsg = (TextComponent) MiniMessage.miniMessage().deserialize(ByteTagsConfig.getInstance().getValue("Messages.No-Permission"));

        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("menu")) {
                ByteTagMenu.OpenTagMenu(player);
            }
            else if (args[0].equalsIgnoreCase("help")) {
                player.sendMessage(Component.text("Help cmd arg.", NamedTextColor.LIGHT_PURPLE));
            }
            else if (args[0].equalsIgnoreCase("tag")) {
                if (args.length >= 2) {
                    if (args[1] != null) {
                        if (args[1].equalsIgnoreCase("check")) {
                            if (args[2] == null) {
                                if (player.hasPermission("bytetags.tag.check.self")) {
                                    player.sendMessage("Checking self.");
                                }
                                else {
                                    player.sendMessage(NoPermMsg);
                                }
                            }
                            else {
                                if (player.hasPermission("bytetags.tag.check.others")) {
                                    player.sendMessage("Checking others.");
                                }
                                else {
                                    player.sendMessage(NoPermMsg);
                                }
                            }
                        }
                        else if (args[1].equalsIgnoreCase("remove")) {
                            if (args[2] == null) {
                                if (player.hasPermission("bytetags.tag.remove.self")) {
                                    player.sendMessage("Remove self.");
                                }
                                else {
                                    player.sendMessage(NoPermMsg);
                                }
                            }
                            else {
                                if (player.hasPermission("bytetags.tag.remove.others")) {
                                    if (Bukkit.getPlayer(args[2]).getClientBrandName() != null && args[2] != null) {
                                        player.sendMessage("Remove others.");
                                    }
                                    else {
                                        player.sendMessage(InvalidPlayer);
                                    }
                                }
                                else {
                                    player.sendMessage(NoPermMsg);
                                }
                            }
                        }
                        else if (args[1].equalsIgnoreCase("set")) {
                            if (args[2] != null) {
                                if (Bukkit.getPlayer(args[3]).getClientBrandName() != null && args[3] != null) {
                                    player.sendMessage("Set");
                                }
                                else {
                                    player.sendMessage(InvalidPlayer);
                                }
                            }
                            else {
                                player.sendMessage(InvalidArg);
                            }
                        }
                    }
                    else {
                        player.sendMessage(InvalidArg);
                    }
                }
                else {
                    player.sendMessage(InvalidArg);
                }
            }
        }
        else {
            player.sendMessage(InvalidArg);
        }

        return true;
    }

    public static void RegisterCMD(Plugin plugin, PluginCommand PluginCMD) throws Exception {

        Commodore commodore = CommodoreProvider.getCommodore(plugin);

        try (InputStream is = plugin.getResource("bytetags.commodore")) {
            if (is == null) {
                throw new Exception("Brigadier command missing from Jar. This is not your fault.");
            }

            LiteralCommandNode<?> commandNode = CommodoreFileReader.INSTANCE.parse(is);

            commodore.register(PluginCMD, commandNode);
        }
    }

}