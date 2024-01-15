package org.sodemc.bytetags.util;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Set;
import java.util.UUID;

public class CustomGUIHead {

    public static ItemStack GUIHead(Component ChatColorHeadName, String ServerHeadName, String ChatColorTextureValue) {
        ItemStack CustomHeadItem = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        SkullMeta CustomHeadMeta = (SkullMeta) CustomHeadItem.getItemMeta();

        PlayerProfile profile = Bukkit.createProfile(UUID.fromString("f00265d7-0e17-44a8-aa46-b16a46b633f4"), ServerHeadName);
        Set<ProfileProperty> properties = profile.getProperties();
        properties.add(new ProfileProperty("textures", ChatColorTextureValue));
        CustomHeadMeta.setPlayerProfile(profile);
        CustomHeadMeta.displayName(ChatColorHeadName);
        CustomHeadItem.setItemMeta(CustomHeadMeta);
        return CustomHeadItem;
    }

}