package org.sodemc.bytetags;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.sodemc.bytetags.commands.ByteTagsCommand;
import org.sodemc.bytetags.commands.LampTest;
import org.sodemc.bytetags.files.ByteTagDatabase;
import org.sodemc.bytetags.files.ByteTagsConfig;
import org.sodemc.bytetags.files.ByteTagsList;
import org.sodemc.bytetags.menus.ByteTagListener;
import revxrsal.commands.annotation.*;
import revxrsal.commands.bukkit.BukkitCommandActor;
import revxrsal.commands.bukkit.BukkitCommandHandler;
import revxrsal.commands.bukkit.EntitySelector;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.lang.annotation.ElementType;

public final class ByteTags extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ByteTagListener(), this);

        ByteTagsConfig.getInstance().load();
        ByteTagsList.getInstance().load();

        ByteTagDatabase.createTable();

        BukkitCommandHandler handler = BukkitCommandHandler.create(this);
        handler.register(this);
        handler.registerBrigadier();
    }

    @Override
    public void onDisable() {

    }

    @Command({"tags", "bytetags"})
    @Description("The main ByteTag command.")
    public void ByteCMD(Player sender) {

        sender.sendMessage(Component
                .text("                      ", NamedTextColor.DARK_GRAY).decorate(TextDecoration.STRIKETHROUGH)
                .append(Component.text("BYTETAGS", TextColor.fromHexString("#5ba8cf"))).decoration(TextDecoration.STRIKETHROUGH, false)
                .append(Component.text("                      ", NamedTextColor.DARK_GRAY)).decorate(TextDecoration.STRIKETHROUGH)
        );
        sender.sendMessage(Component
                .text("⏺", NamedTextColor.DARK_GRAY)
                .append(Component.text(" ʙʏᴛᴇᴛᴀɢꜱ ", NamedTextColor.WHITE))
                .append(Component.text("ᴍᴇɴᴜ", TextColor.fromHexString("#74e374")).hoverEvent(HoverEvent.showText(Component.text("ᴄʟɪᴄᴋ ᴛᴏ ", TextColor.fromHexString("#cd8cff")).append(Component.text("ᴏᴘᴇɴ ᴛʜᴇ ᴍᴇɴᴜ", TextColor.fromHexString("#ede9f0"))))))
                .append(Component.text(" - ", NamedTextColor.DARK_GRAY))
                .append(Component.text("Open's the ByteTags GUI interface.", TextColor.fromHexString("#808080")))
        );

        if (sender.hasPermission("bytetag.spy")) {
            sender.sendMessage(Component
                    .text("⏺", NamedTextColor.DARK_GRAY)
                    .append(Component.text(" ʙʏᴛᴇᴛᴀɢꜱ ", NamedTextColor.WHITE))
                    .append(Component.text("ꜱᴘʏ", TextColor.fromHexString("#74e374")).hoverEvent(HoverEvent.showText(Component.text("ᴄʟɪᴄᴋ ᴛᴏ ", TextColor.fromHexString("#cd8cff")).append(Component.text("ᴏᴘᴇɴ ᴛʜᴇ ᴍᴇɴᴜ", TextColor.fromHexString("#ede9f0"))))))
                    .append(Component.text(" - ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("Open's the ByteTags spy interface.", TextColor.fromHexString("#808080")))
            );
        }

        if (sender.hasPermission("bytetags.check")) {
            sender.sendMessage(Component
                    .text("⏺", NamedTextColor.DARK_GRAY)
                    .append(Component.text(" ʙʏᴛᴇᴛᴀɢꜱ ", NamedTextColor.WHITE))
                    .append(Component.text("ᴄʜᴇᴄᴋ", TextColor.fromHexString("#74e374")).hoverEvent(HoverEvent.showText(Component.text("ᴄʟɪᴄᴋ ᴛᴏ ", TextColor.fromHexString("#cd8cff")).append(Component.text("ꜱʜᴏᴡ ᴀɴ ᴇxᴀᴍᴘʟᴇ", TextColor.fromHexString("#ede9f0"))))))
                    .append(Component.text(" - ", NamedTextColor.DARK_GRAY))
                    .append(Component.text("Finds a player's tag", TextColor.fromHexString("#808080")))
                    .append(Component.text(" (if they have one)", TextColor.fromHexString("#808080"))).decoration(TextDecoration.ITALIC, true)
            );
        }

        sender.sendMessage(Component
                .text("⏺", NamedTextColor.DARK_GRAY)
                .append(Component.text(" ʙʏᴛᴇᴛᴀɢꜱ ", NamedTextColor.WHITE))
                .append(Component.text("ʀᴇᴍᴏᴠᴇ", TextColor.fromHexString("#74e374")).hoverEvent(HoverEvent.showText(Component.text("ᴄʟɪᴄᴋ ᴛᴏ ", TextColor.fromHexString("#cd8cff")).append(Component.text("ꜱʜᴏᴡ ᴀɴ ᴇxᴀᴍᴘʟᴇ", TextColor.fromHexString("#ede9f0"))))))
                .append(Component.text(" - ", NamedTextColor.DARK_GRAY))
                .append(Component.text("Remove a player's tag.", TextColor.fromHexString("#808080")))
                .append(Component.text(" (if they have one)", TextColor.fromHexString("#808080"))).decoration(TextDecoration.ITALIC, true)
        );

    }

    public static ByteTags getInstance() {
        return getPlugin(ByteTags.class);
    }
}
