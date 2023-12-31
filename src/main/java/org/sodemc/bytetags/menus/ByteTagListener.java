package org.sodemc.bytetags.menus;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.sodemc.bytetags.menus.ByteTagMenu;

import java.util.List;

public class ByteTagListener implements Listener {

    private static final String GUI_TITLE = "Custom GUI";
    private static final int ITEMS_PER_PAGE = 9;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().startsWith(GUI_TITLE)) {
            event.setCancelled(true);

            List<ItemStack> items = ByteTagMenu.getItems();

            if (event.getRawSlot() == 45 && ByteTagMenu.FindCurrentPage() > 0) {
                ByteTagMenu.SetCurrentPage(ByteTagMenu.FindCurrentPage() - 1);
                ByteTagMenu.openGUI((org.bukkit.entity.Player) event.getWhoClicked());
            } else if (event.getRawSlot() == 53) {
                int maxPages = (int) Math.ceil((double) items.size() / ITEMS_PER_PAGE) - 1;
                if (ByteTagMenu.FindCurrentPage() < maxPages) {
                    ByteTagMenu.SetCurrentPage(ByteTagMenu.FindCurrentPage() + 1);
                    ByteTagMenu.openGUI((org.bukkit.entity.Player) event.getWhoClicked());
                }
            }
        }
    }
}
