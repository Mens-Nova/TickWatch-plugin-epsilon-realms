package com.epsilonrealms.tickwatch.util;

import org.bukkit.ChatColor;

@SuppressWarnings("deprecation")
public class ColorScaleUtil {
    private ColorScaleUtil() {}

    public static ChatColor cpuColor(int percent) {
        if (percent < 0) return ChatColor.GRAY;
        if (percent <= 50) return ChatColor.GREEN;
        if (percent <= 80) return ChatColor.YELLOW;

        return ChatColor.RED;
    }

    public static ChatColor ramColor(double usedRatio) {
        if (usedRatio <= 0.50) return ChatColor.GREEN;
        if (usedRatio <= 0.80) return ChatColor.YELLOW;

        return ChatColor.RED;
    }

    public static ChatColor tpsColor(double tps) {
        if (tps >= 19.5) return ChatColor.GREEN;
        if (tps >= 17.0) return ChatColor.YELLOW;

        return ChatColor.RED;
    }
}
