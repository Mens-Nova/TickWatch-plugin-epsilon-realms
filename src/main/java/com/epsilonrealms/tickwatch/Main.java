package com.epsilonrealms.tickwatch;

import com.epsilonrealms.tickwatch.commands.ToggleBoardCommand;
import com.epsilonrealms.tickwatch.commands.StatusCommand;
import com.epsilonrealms.tickwatch.listeners.PlayerJoinListener;
import com.epsilonrealms.tickwatch.listeners.PlayerQuitListener;
import com.epsilonrealms.tickwatch.scoreboard.ScoreboardManager;
import com.epsilonrealms.tickwatch.tasks.ScoreboardUpdateTask;
import com.epsilonrealms.tickwatch.tasks.TPSMonitorTask;
import com.epsilonrealms.tickwatch.util.MessagesUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private ScoreboardManager scoreboardManager;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) getDataFolder().mkdirs();
        
        saveDefaultConfig();
        saveResource("messages.yml", false);

        reloadConfig();
        MessagesUtil.reload(this);

        scoreboardManager = new ScoreboardManager(this);

        getCommand("toggleboard").setExecutor(new ToggleBoardCommand(scoreboardManager, scoreboardManager));
        getCommand("status").setExecutor(new StatusCommand());

        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(scoreboardManager), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(scoreboardManager), this);

        int interval = getConfig().getInt("update-interval-ticks", 20);
        int tpsInterval = getConfig().getInt("tps-monitor-interval", 20 * 60);

        Bukkit.getScheduler().runTaskTimer(this, new ScoreboardUpdateTask(scoreboardManager), 0L, interval);
        Bukkit.getScheduler().runTaskTimer(this, new TPSMonitorTask(this), 0L, tpsInterval);
    }

    @Override
    public void onDisable() {
        scoreboardManager.clearAll();
    }
}
