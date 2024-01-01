package org.sodemc.bytetags.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.sodemc.bytetags.files.ByteTagsConfig;
import org.sodemc.bytetags.util.TagsItemFinal;

import java.util.ArrayList;
import java.util.List;

public class ByteTagMenu {

    private static final String GUI_TITLE = "Custom GUI";
    private static final int ItemsPerRow = 7;
    private static final int ITEMS_PER_PAGE = ItemsPerRow * 4;

    private static List<ItemStack> tagItems;
    private static int currentPage = 0;

    public static void openGUI(Player player) {
        tagItems = new ArrayList<>();

        for (String loopTreeValue : ByteTagsConfig.getInstance().getValList("Tags")) {
            tagItems.add(TagsItemFinal.TagGUIItemCreate(loopTreeValue, player.getUniqueId().toString()));
        }

        int numItems = tagItems.size();
        int numPages = (int) Math.ceil((double) numItems / ITEMS_PER_PAGE);
        int startIndex = currentPage * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, numItems);

        Inventory gui = Bukkit.createInventory(null, 54, GUI_TITLE + " - Page " + (currentPage + 1) + "/" + numPages);
        
        for (int i = 0; i < 54; i += 9) {
            gui.setItem(i, createButtonItem(Material.GLASS_PANE, " "));
            gui.setItem(i + 8, createButtonItem(Material.GLASS_PANE, " "));
        }
        
        for (int i = 0; i < 9; i++) {
            gui.setItem(i, createButtonItem(Material.GRAY_STAINED_GLASS_PANE, " "));
            gui.setItem(i + 45, createButtonItem(Material.GRAY_STAINED_GLASS_PANE, " "));
        }

        int guiIndex = 10;
        for (int i = 0; i < ITEMS_PER_PAGE; i++) {
            int index = startIndex + i;
            if (index < numItems) {
                int row = i / ItemsPerRow;
                int column = i % ItemsPerRow;
                
                guiIndex = row * 9 + column + 10;
                gui.setItem(guiIndex, tagItems.get(index));
                
                if ((i + 1) % ItemsPerRow == 0 && i != 0) {
                    guiIndex += 3;
                }
            }
        }

        // Add navigation buttons
        if (currentPage > 0) {
            gui.setItem(45, createButtonItem(Material.ARROW, "Previous Page"));
        }
        if (currentPage < numPages - 1) {
            gui.setItem(53, createButtonItem(Material.ARROW, "Next Page"));
        }

        player.openInventory(gui);
    }

    public static List<ItemStack> getItems() {
        return tagItems;
    }

    private static ItemStack createButtonItem(Material material, String displayName) {
        ItemStack item = new ItemStack(material, 1);
        item.getItemMeta().setDisplayName(displayName);
        return item;
    }

    public static int FindCurrentPage() {
        return currentPage;
    }

    public static void SetCurrentPage(int page) {
        currentPage = page;
    }
}
