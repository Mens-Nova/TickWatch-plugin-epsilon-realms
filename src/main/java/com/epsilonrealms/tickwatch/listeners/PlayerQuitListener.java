package com.epsilonrealms.tickwatch.listeners;

import com.epsilonrealms.tickwatch.scoreboard.ScoreboardManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private final ScoreboardManager manager;

    public PlayerQuitListener(ScoreboardManager manager) {
        this.manager=  manager;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        manager.remove(e.getPlayer());
    }
}
