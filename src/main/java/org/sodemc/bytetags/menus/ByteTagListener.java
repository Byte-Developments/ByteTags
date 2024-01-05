package org.sodemc.bytetags.menus;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.sodemc.bytetags.ByteTags;
import org.sodemc.bytetags.files.ByteTagsConfig;
import org.sodemc.bytetags.menus.ByteTagMenu;

import java.util.List;

public class ByteTagListener implements Listener {
    private static final int ITEMS_PER_PAGE = 9;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player OpenEventPlayer = (Player) event.getWhoClicked();

        if (OpenEventPlayer.hasMetadata("OpenedTagMenu")) {
            event.setCancelled(true);

            List<ItemStack> items = ByteTagMenu.getItems();
            int currentPage = ByteTagMenu.FindCurrentPage();

            if (event.getRawSlot() == 45 && currentPage > 0) {
                ByteTagMenu.SetCurrentPage(currentPage - 1);
                if (OpenEventPlayer.hasMetadata("OpenedTagMenu")) {
                    OpenEventPlayer.removeMetadata("OpenedTagMenu", ByteTags.getInstance());
                }
                ByteTagMenu.OpenTagMenu(OpenEventPlayer);
            } else if (event.getRawSlot() == 53 && (currentPage + 1) * ITEMS_PER_PAGE < items.size()) {
                ByteTagMenu.SetCurrentPage(currentPage + 1);
                if (OpenEventPlayer.hasMetadata("OpenedTagMenu")) {
                    OpenEventPlayer.removeMetadata("OpenedTagMenu", ByteTags.getInstance());
                }
                ByteTagMenu.OpenTagMenu(OpenEventPlayer);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent CloseEvent) {
        Player ClosePlayer = (Player) CloseEvent.getPlayer();

        if (ClosePlayer.hasMetadata("OpenedTagMenu")) {
            ClosePlayer.removeMetadata("OpenedTagMenu", ByteTags.getInstance());
        }
    }
}