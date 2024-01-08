package org.sodemc.bytetags.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.entity.Player;
import org.sodemc.bytetags.files.ByteTagDatabase;
import org.sodemc.bytetags.files.ByteTagsConfig;

public class SelectTag {

    public static void SelectByteTag(Player SelectPlayer, String NewTag) {
        if (ByteTagDatabase.findTagByUUID(SelectPlayer.getUniqueId().toString()) != null && ByteTagDatabase.findTagByUUID(SelectPlayer.getUniqueId().toString()).equals(NewTag)) {
            SelectPlayer.sendMessage(MiniMessage.miniMessage().deserialize(ByteTagsConfig.getInstance().getValue("Messages.Already-Selected"), Placeholder.parsed("prefix", ByteTagsConfig.getInstance().getValue("Messages.Prefix")), Placeholder.unparsed("tag", ByteTagsConfig.getInstance().getValue("Tags." + NewTag + ".Tag"))));
        }
        else if (SelectPlayer.isOp() || SelectPlayer.hasPermission("Tags." + NewTag + ".Permission")) {
            TextComponent SelectedMessage = (TextComponent) MiniMessage.miniMessage().deserialize(ByteTagsConfig.getInstance().getValue("Messages.Tag-Selected"), Placeholder.unparsed("tag", ByteTagsConfig.getInstance().getValue("Tags." + NewTag + ".Tag")), Placeholder.parsed("prefix", ByteTagsConfig.getInstance().getValue("Messages.Prefix")));

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
    }
}