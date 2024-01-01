package org.sodemc.bytetags.util;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.sodemc.bytetags.files.ByteTagDatabase;
import org.sodemc.bytetags.files.ByteTagsConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TagsItemFinal {

    public static ItemStack TagGUIItemCreate(String MainTagTree, String PlayerUUID) {
        List<Component> AllTagItemLore = new ArrayList<>();
        List<String> AllTagItemAtt = new ArrayList<>();

        String ItemMetaType = "ItemMeta";

        String RandomSessionUUID = UUID.randomUUID().toString();

        ItemStack TagGUItem = new ItemStack(Material.BARRIER);
        Component TagItemDisplayname = Component.text(TagGUItem.getItemMeta().getDisplayName() + RandomSessionUUID).decoration(TextDecoration.ITALIC, false);

        if (ByteTagsConfig.getInstance().getValue("Tags." + MainTagTree + ".Material") != null) {
            TagGUItem.setType(Material.valueOf(ByteTagsConfig.getInstance().getValue("Tags." + MainTagTree + ".Material")));
        }

        if (ByteTagsConfig.getInstance().getInt("Tags." + MainTagTree + ".Amount") != null) {
            TagGUItem.setAmount(ByteTagsConfig.getInstance().getInt("Tags." + MainTagTree + ".Amount"));
        }

        ItemMeta TagGUIMeta = TagGUItem.getItemMeta();

        if (ByteTagsConfig.getInstance().getValue("Tags." + MainTagTree + ".Name") != null) {
            TagItemDisplayname = MiniMessage.miniMessage().deserialize(ByteTagsConfig.getInstance().getValue("Tags." + MainTagTree + ".Name")).decoration(TextDecoration.ITALIC, false);
            TagGUIMeta.displayName(TagItemDisplayname);
        }

        if (ByteTagsConfig.getInstance().getInt("Tags." + MainTagTree + ".CustomModelData") != null) {
            TagGUIMeta.setCustomModelData(ByteTagsConfig.getInstance().getInt("Tags." + MainTagTree + ".CustomModelData"));
        }

        for (String LoopTreeLoreValue : ByteTagsConfig.getInstance().getList("Tags." + MainTagTree + ".Lore" + ".Selected")) {
            AllTagItemLore.add(MiniMessage.miniMessage().deserialize(LoopTreeLoreValue).decoration(TextDecoration.ITALIC, false));

            if (ByteTagDatabase.findTagByUUID(PlayerUUID).equals(MainTagTree)) {
                System.out.println("Works, aka equals tag");
            }
            else if (Bukkit.getPlayer(PlayerUUID).isOp() || Bukkit.getPlayer(PlayerUUID).hasPermission("bytetag.tag.amethyst")) {
                System.out.println("Still techinically works cuz said im op");
            }
            else {
                System.out.println("Dead :skull:");
            }
        }

//        if (!ByteTagsConfig.getInstance().getList("Tags." + MainTagTree + ".Enchantments").isEmpty()) {
//            for (String LoopTreeEnchantVal : ByteTagsConfig.getInstance().getList("Tags." + MainTagTree + ".Enchantments")) {
//                if (!LoopTreeEnchantVal.isEmpty()) {
//                    System.out.println(LoopTreeEnchantVal);
//                    if (ByteTagsConfig.getInstance().getInt("Tags." + MainTagTree + LoopTreeEnchantVal) != null) {
//                        TagGUIMeta.addEnchant(Enchantment.getByName(LoopTreeEnchantVal.toUpperCase()), ByteTagsConfig.getInstance().getInt("Tags." + MainTagTree + LoopTreeEnchantVal), true);
//                    }
//                    else {
//                        TagGUIMeta.addEnchant(Enchantment.getByName(LoopTreeEnchantVal.toUpperCase()), 1, true);
//                    }
//                }
//            }
//        }

        for (String LoopTreeAttValue : ByteTagsConfig.getInstance().getList("Tags." + MainTagTree + ".Attributes")) {
            if (!LoopTreeAttValue.isEmpty()) {
                AllTagItemAtt.add(LoopTreeAttValue.toUpperCase());
            }
        }

        if (ByteTagsConfig.getInstance().getValue("Tags." + MainTagTree + ".Material").equals("PLAYER_HEAD") && ByteTagsConfig.getInstance().getValue("Tags." + MainTagTree + ".Texture") != null) {
            SkullMeta TagGUISkullMeta = (SkullMeta) TagGUItem.getItemMeta();
            PlayerProfile profile = Bukkit.createProfile(UUID.fromString("f00265d7-0e17-44a8-aa46-b16a46b633f4"), UUID.randomUUID().toString());
            Set<ProfileProperty> properties = profile.getProperties();
            properties.add(new ProfileProperty("textures", ByteTagsConfig.getInstance().getValue("Tags." + MainTagTree + ".Texture")));
            TagGUISkullMeta.setPlayerProfile(profile);

            if (TagItemDisplayname != Component.text(TagGUItem.getItemMeta().getDisplayName() + RandomSessionUUID).decoration(TextDecoration.ITALIC, false)) {
                TagGUISkullMeta.displayName(TagItemDisplayname);
            }
            if (!AllTagItemLore.isEmpty()) {
                TagGUISkullMeta.lore(AllTagItemLore);
            }
            if (!AllTagItemAtt.isEmpty()) {
                for (String LoopTreeItemAtt : AllTagItemAtt) {
                    TagGUISkullMeta.addItemFlags(ItemFlag.valueOf(LoopTreeItemAtt));
                }
            }

            TagGUItem.setItemMeta(TagGUISkullMeta);
            ItemMetaType = "SkullMeta";
        }

        TagGUIMeta.lore(AllTagItemLore);

        for (String LoopItemAtt : AllTagItemAtt) {
            TagGUIMeta.addItemFlags(ItemFlag.valueOf(LoopItemAtt));
        }

        if (ItemMetaType.equals("ItemMeta")) {
            TagGUItem.setItemMeta(TagGUIMeta);
        }

        return TagGUItem;
    }
}