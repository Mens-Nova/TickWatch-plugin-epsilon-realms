package com.epsilonrealms.tickwatch;

import com.epsilonrealms.tickwatch.commands.ToggleBoardCommand;
import com.epsilonrealms.tickwatch.commands.StatusCommand;
import com.epsilonrealms.tickwatch.listeners.PlayerJoinListener;
import com.epsilonrealms.tickwatch.listeners.PlayerQuitListener;
import com.epsilonrealms.tickwatch.scoreboard.ScoreboardManager;
import com.epsilonrealms.tickwatch.tasks.ScoreboardUpdateTask;
import com.epsilonrealms.tickwatch.tasks.TPSMonitorTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private ScoreboardManager scoreboardManager;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) getDataFolder().mkdirs();

        scoreboardManager = new ScoreboardManager(this);

        getCommand("toggleboard").setExecutor(new ToggleBoardCommand(scoreboardManager, scoreboardManager));
        getCommand("status").setExecutor(new StatusCommand());

        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(scoreboardManager), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(scoreboardManager), this);

        Bukkit.getScheduler().runTaskTimer(this, new ScoreboardUpdateTask(scoreboardManager), 0L, 40L);
        Bukkit.getScheduler().runTaskTimer(this, new TPSMonitorTask(this), 0L, 20L * 60);
    }

    @Override
    public void onDisable() {
        scoreboardManager.clearAll();
    }
}
