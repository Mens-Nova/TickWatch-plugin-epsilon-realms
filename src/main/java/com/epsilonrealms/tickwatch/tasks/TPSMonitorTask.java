package com.epsilonrealms.tickwatch.tasks;

import com.epsilonrealms.tickwatch.Main;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TPSMonitorTask implements Runnable {

    private final Main plugin;
    private final double TPS_THRESHOLD = 18.0;

    public TPSMonitorTask(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        double tps = plugin.getServer().getTPS()[0];
        if (tps < TPS_THRESHOLD) logTPSDrop(tps);
    }

    private void logTPSDrop(double tps) {
        try (FileWriter writer = new FileWriter(plugin.getDataFolder() + "/tps-log.txt", true)) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write(timestamp + " - TPS dropped to " + String.format("%.2f", tps) + "\n");
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to write TPS log: " + e.getMessage());
        }
    }
}
