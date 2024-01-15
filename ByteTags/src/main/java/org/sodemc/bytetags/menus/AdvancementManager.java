package org.sodemc.bytetags.menus;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.sodemc.bytetags.ByteTags;

import java.util.UUID;

public class AdvancementManager {

    private final NamespacedKey NamespaceKey;
    private final String AdvancementIcon;
    private final Component AdvancementMessage;
    private final Style AdvancementStyle;

    public static void DisplayCustomAdvancement(Player DisplayAdvancementPlayer, String AdvanceIcon, Component AdvanceMessage, Style AdvanceStyle) {
        new AdvancementManager(AdvanceIcon, AdvanceMessage, AdvanceStyle).StartManager(DisplayAdvancementPlayer);
    }

    public AdvancementManager(String advancementIcon, Component advancementMessage, Style advancementStyle) {
        this.NamespaceKey = new NamespacedKey(ByteTags.getInstance(), UUID.randomUUID().toString());
        this.AdvancementIcon = advancementIcon;
        this.AdvancementMessage = advancementMessage;
        this.AdvancementStyle = advancementStyle;
    }

    public void StartManager(Player AdPlayer) {
        CreateCustomAdvancement();
        GrantCustomAdvancement(AdPlayer);

        Bukkit.getScheduler().runTaskLater(ByteTags.getInstance(), () -> {
            RevokeCustomAdvancement(AdPlayer);
        }, 10);
    }

    private void CreateCustomAdvancement() {

        Bukkit.getUnsafe().loadAdvancement(NamespaceKey, "{\n" +
                "    \"criteria\": {\n" +
                "        \"trigger\": {\n" +
                "            \"trigger\": \"minecraft:impossible\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"display\": {\n" +
                "        \"icon\": {\n" +
                "            \"item\": \"minecraft:" + AdvancementIcon + "\"\n" +
                "        },\n" +
                "        \"title\": {\n" +
                "            \"text\": \"" + AdvancementMessage + "\"\n" +
                "        },\n" +
                "        \"description\": {\n" +
                "            \"text\": \"\"\n" +
                "        },\n" +
                "        \"background\": \"minecraft:textures/gui/advancements/backgrounds/adventure.png\",\n" +
                "        \"frame\": \"" + AdvancementStyle + "\",\n" +
                "        \"announce_to_chat\": false,\n" +
                "        \"show_toast\": true,\n" +
                "        \"hidden\": true\n" +
                "    },\n" +
                "    \"requirements\": [\n" +
                "        [\n" +
                "            \"trigger\"\n" +
                "        ]\n" +
                "    ]\n" +
                "}");
    }

    private void GrantCustomAdvancement(Player GrantPlayer) {
        GrantPlayer.getAdvancementProgress(Bukkit.getAdvancement(NamespaceKey)).awardCriteria("trigger");
    }

    private void RevokeCustomAdvancement(Player RevokePlayer) {
        RevokePlayer.getAdvancementProgress(Bukkit.getAdvancement(NamespaceKey)).revokeCriteria("trigger");
    }

    public static enum Style {
        GOAL,
        TASK,
        CHALLENGE;
    }
}