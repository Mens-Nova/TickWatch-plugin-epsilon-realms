package com.epsilonrealms.tickwatch.listeners;

import com.epsilonrealms.tickwatch.scoreboard.ScoreboardManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final ScoreboardManager manager;
    
    public PlayerJoinListener(ScoreboardManager manager){
        this.manager = manager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        manager.disable(player); 
    }
}
