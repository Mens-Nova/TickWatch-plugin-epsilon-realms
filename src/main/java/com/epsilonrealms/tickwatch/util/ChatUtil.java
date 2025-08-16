package com.epsilonrealms.tickwatch.util;

import org.bukkit.ChatColor;

@SuppressWarnings("deprecation")
public class ChatUtil {
    public static String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
