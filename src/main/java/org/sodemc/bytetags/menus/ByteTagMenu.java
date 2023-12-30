package org.sodemc.bytetags.menus;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.sodemc.bytetags.util.CustomGUIHead;

public class ByteTagMenu {
    public static void ByteTagOpen(Player ByteTagPlayer) {

        Inventory ByteTagGUI = Bukkit.createInventory(ByteTagPlayer, 9 * 6, Component
                .text("Pick ", NamedTextColor.WHITE)
                .append(Component.text("TAG", TextColor.fromHexString("#349beb"), TextDecoration.BOLD))
        );

        ByteTagGUI.setItem(0, CustomGUIHead.GUIHead(Component
                .text("RICH ", TextColor.fromHexString("#9534eb"), TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Tag", NamedTextColor.GRAY).decoration(TextDecoration.BOLD, false))
                , "RichPlayerTag", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzk2Y2UxM2ZmNjE1NWZkZjMyMzVkOGQyMjE3NGM1ZGU0YmY1NTEyZjFhZGVkYTFhZmEzZmMyODE4MGYzZjcifX19")
        );

        ByteTagPlayer.openInventory(ByteTagGUI);

    }

}
