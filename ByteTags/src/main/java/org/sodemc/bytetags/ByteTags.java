package org.sodemc.bytetags;

import com.mojang.brigadier.tree.LiteralCommandNode;
import me.lucko.commodore.Commodore;
import me.lucko.commodore.CommodoreProvider;
import me.lucko.commodore.file.CommodoreFileReader;
import org.bukkit.plugin.java.JavaPlugin;
import org.sodemc.bytetags.commands.ByteTagsCommand;
import org.sodemc.bytetags.files.ByteTagDatabase;
import org.sodemc.bytetags.files.ByteTagsConfig;
import org.sodemc.bytetags.files.ByteTagsList;
import org.sodemc.bytetags.menus.ByteTagListener;

import java.io.IOException;
import java.io.InputStream;

public final class ByteTags extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("bytetags").setExecutor(new ByteTagsCommand());
        getServer().getPluginManager().registerEvents(new ByteTagListener(), this);

        ByteTagsConfig.getInstance().load();
        ByteTagsList.getInstance().load();


        ByteTagDatabase.createTable();

        try {
            ByteTagsCommand.RegisterCMD(this, getCommand("bytetags"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {

    }


    public static ByteTags getInstance() {
        return getPlugin(ByteTags.class);
    }
}

//sender.sendMessage(Component
//        .text("                      ", NamedTextColor.DARK_GRAY).decorate(TextDecoration.STRIKETHROUGH)
//        .append(Component.text("BYTETAGS", TextColor.fromHexString("#5ba8cf"))).decoration(TextDecoration.STRIKETHROUGH, false)
//        .append(Component.text("                      ", NamedTextColor.DARK_GRAY)).decorate(TextDecoration.STRIKETHROUGH)
//        );
//        sender.sendMessage(Component
//        .text("⏺", NamedTextColor.DARK_GRAY)
//        .append(Component.text(" ʙʏᴛᴇᴛᴀɢꜱ ", NamedTextColor.WHITE))
//        .append(Component.text("ᴍᴇɴᴜ", TextColor.fromHexString("#74e374")).hoverEvent(HoverEvent.showText(Component.text("ᴄʟɪᴄᴋ ᴛᴏ ", TextColor.fromHexString("#cd8cff")).append(Component.text("ᴏᴘᴇɴ ᴛʜᴇ ᴍᴇɴᴜ", TextColor.fromHexString("#ede9f0"))))))
//        .append(Component.text(" - ", NamedTextColor.DARK_GRAY))
//        .append(Component.text("Open's the ByteTags GUI interface.", TextColor.fromHexString("#808080")))
//        );
//
//        if (sender.hasPermission("bytetag.spy")) {
//        sender.sendMessage(Component
//        .text("⏺", NamedTextColor.DARK_GRAY)
//        .append(Component.text(" ʙʏᴛᴇᴛᴀɢꜱ ", NamedTextColor.WHITE))
//        .append(Component.text("ꜱᴘʏ", TextColor.fromHexString("#74e374")).hoverEvent(HoverEvent.showText(Component.text("ᴄʟɪᴄᴋ ᴛᴏ ", TextColor.fromHexString("#cd8cff")).append(Component.text("ᴏᴘᴇɴ ᴛʜᴇ ᴍᴇɴᴜ", TextColor.fromHexString("#ede9f0"))))))
//        .append(Component.text(" - ", NamedTextColor.DARK_GRAY))
//        .append(Component.text("Open's the ByteTags spy interface.", TextColor.fromHexString("#808080")))
//        );
//        }
//
//        if (sender.hasPermission("bytetags.check")) {
//        sender.sendMessage(Component
//        .text("⏺", NamedTextColor.DARK_GRAY)
//        .append(Component.text(" ʙʏᴛᴇᴛᴀɢꜱ ", NamedTextColor.WHITE))
//        .append(Component.text("ᴄʜᴇᴄᴋ", TextColor.fromHexString("#74e374")).hoverEvent(HoverEvent.showText(Component.text("ᴄʟɪᴄᴋ ᴛᴏ ", TextColor.fromHexString("#cd8cff")).append(Component.text("ꜱʜᴏᴡ ᴀɴ ᴇxᴀᴍᴘʟᴇ", TextColor.fromHexString("#ede9f0"))))))
//        .append(Component.text(" - ", NamedTextColor.DARK_GRAY))
//        .append(Component.text("Finds a player's tag", TextColor.fromHexString("#808080")))
//        .append(Component.text(" (if they have one)", TextColor.fromHexString("#808080"))).decoration(TextDecoration.ITALIC, true)
//        );
//        }
//
//        sender.sendMessage(Component
//        .text("⏺", NamedTextColor.DARK_GRAY)
//        .append(Component.text(" ʙʏᴛᴇᴛᴀɢꜱ ", NamedTextColor.WHITE))
//        .append(Component.text("ʀᴇᴍᴏᴠᴇ", TextColor.fromHexString("#74e374")).hoverEvent(HoverEvent.showText(Component.text("ᴄʟɪᴄᴋ ᴛᴏ ", TextColor.fromHexString("#cd8cff")).append(Component.text("ꜱʜᴏᴡ ᴀɴ ᴇxᴀᴍᴘʟᴇ", TextColor.fromHexString("#ede9f0"))))))
//        .append(Component.text(" - ", NamedTextColor.DARK_GRAY))
//        .append(Component.text("Remove a player's tag.", TextColor.fromHexString("#808080")))
//        .append(Component.text(" (if they have one)", TextColor.fromHexString("#808080"))).decoration(TextDecoration.ITALIC, true)
//        );