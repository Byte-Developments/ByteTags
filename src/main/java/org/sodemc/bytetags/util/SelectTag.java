package org.sodemc.bytetags.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.sodemc.bytetags.files.ByteTagDatabase;
import org.sodemc.bytetags.files.ByteTagsConfig;
import org.sodemc.bytetags.menus.AdvancementManager;

public class SelectTag {

    public static boolean SelectByteTag(Player SelectPlayer, String NewTag) {
        if (ByteTagDatabase.findTagByUUID(SelectPlayer.getUniqueId().toString()) != null && ByteTagDatabase.findTagByUUID(SelectPlayer.getUniqueId().toString()).equals(NewTag)) {
            SelectPlayer.sendMessage(MiniMessage.miniMessage().deserialize(ByteTagsConfig.getInstance().getValue("Messages.Already-Selected"), Placeholder.parsed("prefix", ByteTagsConfig.getInstance().getValue("Messages.Prefix")), Placeholder.unparsed("tag", ByteTagsConfig.getInstance().getValue("Tags." + NewTag + ".Tag"))));
        }
        else if (SelectPlayer.isOp() || SelectPlayer.hasPermission("Tags." + NewTag + ".Permission")) {
            TextComponent SelectedMessage = (TextComponent) MiniMessage.miniMessage().deserialize(ByteTagsConfig.getInstance().getValue("Messages.Tag-Selected"), Placeholder.unparsed("tag", ByteTagsConfig.getInstance().getValue("Tags." + NewTag + ".Tag")), Placeholder.parsed("prefix", ByteTagsConfig.getInstance().getValue("Messages.Prefix")));

            final AdvancementManager.Style AdvancementStyle;
            try {
                AdvancementStyle = AdvancementManager.Style.valueOf(ByteTagsConfig.getInstance().getValue("Menu.Notification.Style"));
            }
            catch (final Throwable t) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Invalid style for notification.");

                return false;
            }

            AdvancementManager.DisplayCustomAdvancement(SelectPlayer, ByteTagsConfig.getInstance().getValue("Menu.Notification.Icon"), MiniMessage.miniMessage().deserialize(ByteTagsConfig.getInstance().getValue("Menu.Notification.Body"), AdvancementStyle);

            if (ByteTagDatabase.findTagByUUID(SelectPlayer.getUniqueId().toString()) != null) {
                ByteTagDatabase.updateData(NewTag, SelectPlayer.getUniqueId().toString(), "Tag");

                SelectPlayer.sendMessage(SelectedMessage);
            }
            else {
                ByteTagDatabase.insertData(SelectPlayer.getName(), NewTag, SelectPlayer.getUniqueId().toString());

                SelectPlayer.sendMessage(SelectedMessage);
            }
        }
        else {
            SelectPlayer.sendMessage(MiniMessage.miniMessage().deserialize(ByteTagsConfig.getInstance().getValue("Messages.No-Permission"), Placeholder.parsed("prefix", ByteTagsConfig.getInstance().getValue("Messages.Prefix")), Placeholder.unparsed("tag", ByteTagsConfig.getInstance().getValue("Tags." + NewTag + ".Tag"))));
        }

        return true;
    }
}