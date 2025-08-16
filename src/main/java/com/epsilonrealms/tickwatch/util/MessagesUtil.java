package com.epsilonrealms.tickwatch.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

public class MessagesUtil {
    private static FileConfiguration config;

    public static void reload(JavaPlugin plugin) {
        File file = new File(plugin.getDataFolder(), "messages.yml");
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static String get(String path) {
        return ChatUtil.format(config.getString(path, "&cMissing message: " + path));
    }

    public static String get(String path, Map<String, String> placeholders) {
        String msg = config.getString(path, "&cMissing message: " + path);
        for (Entry<String, String> entry : placeholders.entrySet()) {
            msg = msg.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return ChatUtil.format(msg);
    }
}
