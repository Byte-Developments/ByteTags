package org.sodemc.bytetags.menus;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
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
import org.sodemc.bytetags.util.SelectTag;

import java.util.List;

public class ByteTagListener implements Listener {
    private static final int ITEMS_PER_PAGE = 9;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player OpenEventPlayer = (Player) event.getWhoClicked();

        TextComponent MenuTitle = (TextComponent) MiniMessage.miniMessage().deserialize(ByteTagsConfig.getInstance().getValue("Menu.Title"), Placeholder.unparsed("page", String.valueOf(ByteTagMenu.FindCurrentPage() + 1)), Placeholder.unparsed("max", String.valueOf(ByteTagMenu.FindMaxPage())));

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
                    if ((MiniMessage.miniMessage().deserialize(ByteTagsConfig.getInstance().getValue("Tags." + LoopTag + ".Name")).decoration(TextDecoration.ITALIC, false)).contains(event.getCurrentItem().getItemMeta().displayName())) {
                        SelectTag.SelectByteTag(OpenEventPlayer, LoopTag);
                        OpenEventPlayer.sendMessage("Selecting Now...");
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