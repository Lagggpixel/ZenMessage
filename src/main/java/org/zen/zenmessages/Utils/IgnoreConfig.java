package org.zen.zenmessages.Utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.zen.zenmessages.ZenMessages;

import java.io.File;
import java.io.IOException;

public class IgnoreConfig {
    public static FileConfiguration IgnoreConfiguration;
    static File IgnoreFile;
    static ZenMessages plugin = ZenMessages.getInstance();

    public static void Setup() {
        IgnoreFile = new File(plugin.getDataFolder(), "Ignore.yml");
        if (!IgnoreFile.exists()) {
            try {
                IgnoreFile.getParentFile().mkdirs();
                IgnoreFile.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        IgnoreConfiguration = (FileConfiguration) YamlConfiguration.loadConfiguration(IgnoreFile);
    }

    public static FileConfiguration Get() {
        return IgnoreConfiguration;
    }

    public static void Save() {
        try {
            IgnoreConfiguration.save(IgnoreFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Reload() {
        IgnoreConfiguration = (FileConfiguration) YamlConfiguration.loadConfiguration(IgnoreFile);
    }
}