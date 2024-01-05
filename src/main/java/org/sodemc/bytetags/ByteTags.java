package org.sodemc.bytetags;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
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

import java.lang.annotation.ElementType;

public final class ByteTags extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("bytetag").setExecutor(new ByteTagsCommand());
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

    @Command("test")
    @Description("A test brigadier command!")
    @Subcommand({"work", "test"})
    public void message(Player sender, boolean players, @Optional int TestInt) {
        sender.sendMessage(Component.text("Works. Heres the value »" + players, TextColor.fromHexString("#b2f57f")));
    }

    public static ByteTags getInstance() {
        return getPlugin(ByteTags.class);
    }
}
