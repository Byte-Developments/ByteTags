package org.sodemc.bytetags;

import org.bukkit.plugin.java.JavaPlugin;
import org.sodemc.bytetags.commands.ByteTagsCommand;
import org.sodemc.bytetags.files.ByteTagsConfig;

public final class ByteTags extends JavaPlugin {

    @Override
    public void onEnable() {

        ByteTagsConfig.getInstance().load();
        getCommand("bytetag").setExecutor(new ByteTagsCommand());
    }

    @Override
    public void onDisable() {

    }

    public static ByteTags getInstance() {
        return getPlugin(ByteTags.class);
    }
}
