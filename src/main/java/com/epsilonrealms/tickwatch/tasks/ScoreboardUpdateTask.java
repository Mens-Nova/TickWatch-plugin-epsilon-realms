package com.epsilonrealms.tickwatch.tasks;

import com.epsilonrealms.tickwatch.scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;

public class ScoreboardUpdateTask implements Runnable {
    private final ScoreboardManager manager;

    public ScoreboardUpdateTask(ScoreboardManager manager) {
        this.manager = manager;
    }

    @Override
    public void run(){
        Bukkit.getOnlinePlayers().forEach(manager::update);
    }
}
