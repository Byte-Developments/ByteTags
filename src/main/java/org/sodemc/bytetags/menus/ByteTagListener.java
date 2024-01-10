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
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.sodemc.bytetags.ByteTags;
import org.sodemc.bytetags.files.ByteTagsConfig;
import org.sodemc.bytetags.menus.ByteTagMenu;
import org.sodemc.bytetags.util.DataTag;
import org.sodemc.bytetags.util.SelectTag;

import java.util.List;

public class ByteTagListener implements Listener {
    private static final int ITEMS_PER_PAGE = 9;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player OpenEventPlayer = (Player) event.getWhoClicked();

        if (OpenEventPlayer.hasMetadata("OpenedTagMenu")) {
            List<ItemStack> items = ByteTagMenu.getItems();
            int currentPage = ByteTagMenu.FindCurrentPage();

            if (event.getRawSlot() == ByteTagsConfig.getInstance().getInt("Menu.Items.BackPageButton.Slot") && currentPage > 0) {
                ByteTagMenu.SetCurrentPage(currentPage - 1);
                ByteTagMenu.OpenTagMenu(OpenEventPlayer);
            }
            else if (event.getRawSlot() == ByteTagsConfig.getInstance().getInt("Menu.Items.NextPageButton.Slot") && (currentPage + 1) * ITEMS_PER_PAGE < items.size() && (currentPage + 1) < ByteTagMenu.FindMaxPage()) {
                ByteTagMenu.SetCurrentPage(currentPage + 1);
                ByteTagMenu.OpenTagMenu(OpenEventPlayer);
            }
            else {
                for (String LoopTag : ByteTagsConfig.getInstance().getValList("Tags")) {
                    if (DataTag.GetDataTag(event.getCurrentItem().getItemMeta(), LoopTag) && event.getCurrentItem().getType() != Material.AIR && event.getCurrentItem().getType() != Material.GRAY_STAINED_GLASS_PANE) {
                        SelectTag.SelectByteTag(OpenEventPlayer, LoopTag);
                        
                        break;
                    }
                }
            }

            event.setCancelled(true);
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