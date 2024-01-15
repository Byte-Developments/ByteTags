package org.sodemc.bytetags.util;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.sodemc.bytetags.ByteTags;

public class DataTag {

    public static ItemMeta SetDataTag(ItemMeta DataTagItem, String DataTagName) {
        NamespacedKey DataTagKey = new NamespacedKey(ByteTags.getInstance(), DataTagName);

        DataTagItem.getPersistentDataContainer().set(DataTagKey, PersistentDataType.INTEGER, 1);

        return DataTagItem;
    }

    public static boolean GetDataTag(ItemMeta GetDataItem, String GetDataName) {
        NamespacedKey GetDataKey = new NamespacedKey(ByteTags.getInstance(), GetDataName);
        PersistentDataContainer GetDataContainer = GetDataItem.getPersistentDataContainer();

        return GetDataContainer.has(GetDataKey, PersistentDataType.INTEGER);
    }
}