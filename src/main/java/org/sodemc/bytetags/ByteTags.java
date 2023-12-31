package org.sodemc.bytetags;

import org.bukkit.plugin.java.JavaPlugin;
import org.sodemc.bytetags.commands.ByteTagsCommand;
import org.sodemc.bytetags.files.ByteTagsConfig;
import org.sodemc.bytetags.files.ByteTagsList;
import org.sodemc.bytetags.menus.ByteTagListener;

public final class ByteTags extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("bytetag").setExecutor(new ByteTagsCommand());
        getServer().getPluginManager().registerEvents(new ByteTagListener(), this);

        ByteTagsConfig.getInstance().load();
        ByteTagsList.getInstance().load();
    }

    @Override
    public void onDisable() {

    }

    public static ByteTags getInstance() {
        return getPlugin(ByteTags.class);
    }
}
