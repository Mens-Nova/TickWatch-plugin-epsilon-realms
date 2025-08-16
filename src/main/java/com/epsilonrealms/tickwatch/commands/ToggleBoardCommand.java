package com.epsilonrealms.tickwatch.commands;

import com.epsilonrealms.tickwatch.scoreboard.ScoreboardManager;
import com.epsilonrealms.tickwatch.util.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleBoardCommand  implements CommandExecutor {
    private final ScoreboardManager manager;

    public ToggleBoardCommand (Object plugin, ScoreboardManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command!");
            return true;
        }

        Player player = (Player) sender;
        if(!player.hasPermission("tickwatch.toggle")) {
            player.sendMessage(ChatUtil.format("&cYou must be an operator to use this!"));
            return true;
        }

        manager.toggle(player);
        return true;
    }
}
