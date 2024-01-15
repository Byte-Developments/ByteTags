package org.sodemc.bytetags.menus;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.sodemc.bytetags.ByteTags;
import org.sodemc.bytetags.files.ByteTagsConfig;
import org.sodemc.bytetags.util.TagsItemFinal;

import java.io.Console;
import java.util.*;

public class ByteTagMenu {
    private static final int ItemsPerRow = 7;

    private static final int ItemsPerPage = ItemsPerRow * 4;

    private static List<ItemStack> tagItems;
    private static int currentPage = 0;
    public static int numPages = 0;

    public static void OpenTagMenu(Player TagMenuPlayer) {
        tagItems = new ArrayList<>();

        List<Component> TagIndicatorLore = new ArrayList<>();

        for (String loopTreeValue : ByteTagsConfig.getInstance().getValList("Tags")) {
            tagItems.add(TagsItemFinal.TagGUIItemCreate(loopTreeValue, TagMenuPlayer.getUniqueId().toString(), TagMenuPlayer));
        }

        int numItems = tagItems.size();
        numPages = (int) Math.ceil((double) numItems / ItemsPerPage);
        int startIndex = currentPage * ItemsPerPage;
        int endIndex = Math.min(startIndex + ItemsPerPage, numItems);

        TextComponent MenuTitle = (TextComponent) MiniMessage.miniMessage().deserialize(ByteTagsConfig.getInstance().getValue("Menu.Title"), Placeholder.unparsed("page", String.valueOf(currentPage + 1)), Placeholder.unparsed("max", String.valueOf(numPages)));

        Inventory gui = Bukkit.createInventory(null, 54, MenuTitle);

        for (int i = 0; i < 54; i += 9) {
            gui.setItem(i, CreateButtonItem(Material.GRAY_STAINED_GLASS_PANE, Component.text("", NamedTextColor.GREEN), 1));
            gui.setItem(i + 8, CreateButtonItem(Material.GRAY_STAINED_GLASS_PANE, Component.text("", NamedTextColor.GREEN), 1));
        }
        
        for (int i = 0; i < 9; i++) {
            gui.setItem(i, CreateButtonItem(Material.GRAY_STAINED_GLASS_PANE, Component.text("", NamedTextColor.GREEN), 1));
            gui.setItem(i + 45, CreateButtonItem(Material.GRAY_STAINED_GLASS_PANE, Component.text("", NamedTextColor.GREEN), 1));
        }

        int guiIndex = 10;
        for (int i = 0; i < ItemsPerPage; i++) {
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

        // Add navigation buttons.
        if (currentPage > 0) {
            gui.setItem(ByteTagsConfig.getInstance().getInt("Menu.Items.BackPageButton.Slot"), CreateButtonItem(Material.getMaterial(ByteTagsConfig.getInstance().getValue("Menu.Items.BackPageButton.Material")), MiniMessage.miniMessage().deserialize(ByteTagsConfig.getInstance().getValue("Menu.Items.NextPageButton.Name")).decoration(TextDecoration.ITALIC, false), ByteTagsConfig.getInstance().getInt("Menu.Items.NextPageButton.Amount")));
        }
        if (currentPage < numPages - 1) {
            gui.setItem(ByteTagsConfig.getInstance().getInt("Menu.Items.NextPageButton.Slot"), CreateButtonItem(Material.getMaterial(ByteTagsConfig.getInstance().getValue("Menu.Items.NextPageButton.Material")), MiniMessage.miniMessage().deserialize(ByteTagsConfig.getInstance().getValue("Menu.Items.NextPageButton.Name")).decoration(TextDecoration.ITALIC, false), ByteTagsConfig.getInstance().getInt("Menu.Items.NextPageButton.Amount")));
        }

        if (ByteTagsConfig.getInstance().getValue("Menu.Items.Tag-Indicator") != null && !ByteTagsConfig.getInstance().getValue("Menu.Items.Tag-Indicator").isEmpty()) {
            ItemStack TagIndicatorItem = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta TagIndicatorMeta = (SkullMeta) TagIndicatorItem.getItemMeta();

            if (ByteTagsConfig.getInstance().getValue("Menu.Items.Tag-Indicator.Texture") != null) {
                if (ByteTagsConfig.getInstance().getValue("Menu.Items.Tag-Indicator.Texture").equals("<player>")) {
                    TagIndicatorMeta.setOwningPlayer(TagMenuPlayer);
                }
                else {
                    PlayerProfile TagIndicatorProfile = Bukkit.createProfile(UUID.fromString("f00265d7-0e17-44a8-aa46-b16a46b633f4"), UUID.randomUUID().toString());
                    Set<ProfileProperty> TagIndicatorProperty = TagIndicatorProfile.getProperties();
                    TagIndicatorProperty.add(new ProfileProperty("textures", ByteTagsConfig.getInstance().getValue("Menu.Items.Tag-Indicator.Texture")));

                    TagIndicatorMeta.setPlayerProfile(TagIndicatorProfile);
                }
            }
            else {
                TagIndicatorMeta.setOwningPlayer(TagMenuPlayer);
            }

//            for (String LoopLoreValue : ByteTagsConfig.getInstance().getList("Menu.Items.Tag-Indicator.Lore")) {
//                if (!LoopLoreValue.isEmpty()) {
//                    //TagIndicatorLore.add(MiniMessage);
//                }
//            }

            Component TagIndicatorName = MiniMessage.miniMessage().deserialize(ByteTagsConfig.getInstance().getValue("Menu.Items.Tag-Indicator.Name"), Placeholder.unparsed("player", TagMenuPlayer.getName()));

            TagIndicatorMeta.displayName(TagIndicatorName);
            TagIndicatorItem.setItemMeta(TagIndicatorMeta);

            gui.setItem(4, TagIndicatorItem);
        }

        TagMenuPlayer.openInventory(gui);

        TagMenuPlayer.setMetadata("OpenedTagMenu", new FixedMetadataValue(ByteTags.getInstance(), true));
    }

    public static List<ItemStack> getItems() {
        return tagItems;
    }

    private static ItemStack CreateButtonItem(Material ButtonMaterial, Component ButtonItemName, int ButtonCount) {
        if (ButtonMaterial == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Invalid material for button.");
            return new ItemStack(Material.ANVIL);
        }

        ItemStack ButtonItem = new ItemStack(ButtonMaterial, ButtonCount);
        ItemMeta ButtonItemMeta = ButtonItem.getItemMeta();
        ButtonItemMeta.displayName(ButtonItemName);
        ButtonItem.setItemMeta(ButtonItemMeta);
        return ButtonItem;
    }

    public static void UpdateTagMenu(Player player) {
        if (player.hasMetadata("OpenedTagMenu")) {
            Inventory gui = player.getOpenInventory().getTopInventory();

            if (gui != null && gui.getHolder() == null) {
                int startIndex = currentPage * ItemsPerPage;
                int endIndex = Math.min(startIndex + ItemsPerPage, tagItems.size());

                for (int i = 10; i <= 16; i++) {
                    gui.setItem(i, new ItemStack(Material.AIR));
                }
                for (int i = 19; i <= 25; i++) {
                    gui.setItem(i, new ItemStack(Material.AIR));
                }
                for (int i = 28; i <= 34; i++) {
                    gui.setItem(i, new ItemStack(Material.AIR));
                }
                for (int i = 37; i <= 43; i++) {
                    gui.setItem(i, new ItemStack(Material.AIR));
                }

                gui.setItem(ByteTagsConfig.getInstance().getInt("Menu.Items.BackPageButton.Slot"), CreateButtonItem(Material.GRAY_STAINED_GLASS_PANE, Component.text("", NamedTextColor.GREEN), 1));
                gui.setItem(ByteTagsConfig.getInstance().getInt("Menu.Items.NextPageButton.Slot"), CreateButtonItem(Material.GRAY_STAINED_GLASS_PANE, Component.text("", NamedTextColor.GREEN), 1));

                if (currentPage > 0) {
                    gui.setItem(ByteTagsConfig.getInstance().getInt("Menu.Items.BackPageButton.Slot"), CreateButtonItem(Material.getMaterial(ByteTagsConfig.getInstance().getValue("Menu.Items.BackPageButton.Material")), MiniMessage.miniMessage().deserialize(ByteTagsConfig.getInstance().getValue("Menu.Items.NextPageButton.Name")).decoration(TextDecoration.ITALIC, false), ByteTagsConfig.getInstance().getInt("Menu.Items.NextPageButton.Amount")));
                }
                if (currentPage < numPages - 1) {
                    gui.setItem(ByteTagsConfig.getInstance().getInt("Menu.Items.NextPageButton.Slot"), CreateButtonItem(Material.getMaterial(ByteTagsConfig.getInstance().getValue("Menu.Items.NextPageButton.Material")), MiniMessage.miniMessage().deserialize(ByteTagsConfig.getInstance().getValue("Menu.Items.NextPageButton.Name")).decoration(TextDecoration.ITALIC, false), ByteTagsConfig.getInstance().getInt("Menu.Items.NextPageButton.Amount")));
                }

                int guiIndex = 10;
                for (int i = 0; i < ItemsPerPage; i++) {
                    int index = startIndex + i;
                    if (index < tagItems.size()) {
                        int row = i / ItemsPerRow;
                        int column = i % ItemsPerRow;

                        guiIndex = row * 9 + column + 10;
                        gui.setItem(guiIndex, tagItems.get(index));

                        if ((i + 1) % ItemsPerRow == 0 && i != 0) {
                            guiIndex += 3;
                        }
                    }
                }
            }
        }
    }

    public static int FindCurrentPage() {
        return currentPage;
    }

    public static int FindMaxPage() { return numPages; }

    public static void SetCurrentPage(int page) {
        currentPage = page;
    }
}
