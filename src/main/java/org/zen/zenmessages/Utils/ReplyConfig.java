package org.zen.zenmessages.Utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.zen.zenmessages.ZenMessages;

import java.io.File;
import java.io.IOException;

public class ReplyConfig {
    public static FileConfiguration ReplyConfiguration;
    static File ReplyFile;
    static ZenMessages plugin = ZenMessages.getInstance();

    public static void Setup() {
        ReplyConfig.ReplyFile = new File(plugin.getDataFolder(), "LastMessage.yml");
        if (!ReplyFile.exists()) {
            try {
                ReplyFile.getParentFile().mkdirs();
                ReplyFile.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        ReplyConfiguration = (FileConfiguration)YamlConfiguration.loadConfiguration(ReplyFile);
    }

    public static FileConfiguration Get() {
        return ReplyConfiguration;
    }

    public static void Save() {
        try {
            ReplyConfiguration.save(ReplyFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Reload() {
        ReplyConfiguration = (FileConfiguration) YamlConfiguration.loadConfiguration(ReplyFile);
    }
}

