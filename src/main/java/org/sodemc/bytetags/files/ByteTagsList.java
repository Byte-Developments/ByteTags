package org.sodemc.bytetags.files;

import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.sodemc.bytetags.ByteTags;

import java.io.File;
import java.util.List;

public class ByteTagsList {
    private final static ByteTagsList instance = new ByteTagsList();
    private File file;
    private YamlConfiguration config;
    private ByteTagsList() {
    }


    public List<String> getValList(String ValListName) { return this.config.getConfigurationSection(ValListName).getKeys(false).stream().toList(); }

    public void load() {
        file = new File(ByteTags.getInstance().getDataFolder(),"tags.yml");

        if (!file.exists()) {
            ByteTags.getInstance().saveResource("tags.yml", false);
        }

        config = new YamlConfiguration();
        config.options().parseComments(true);

        try {
            config.load(file);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getValue(String valLocation) {
        return config.getString(valLocation);
    }

    public List<String> getList(String listLocation) { return config.getStringList(listLocation); }

    public void save() {
        try {
            config.save(file);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void set(String path, Object value) {
        config.set(path, value);

        save();
    }

    public static ByteTagsList getInstance() {
        return instance;
    }

}