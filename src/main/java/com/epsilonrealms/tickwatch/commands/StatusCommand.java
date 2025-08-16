package com.epsilonrealms.tickwatch.commands;

import com.epsilonrealms.tickwatch.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatusCommand implements CommandExecutor{
    private final double MAX_TPS = 20.0;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command!");
            return true;
        }

        Player player = (Player) sender;
        double tps = Math.min(Bukkit.getServer().getTPS()[0], MAX_TPS);
        int ping = player.getPing();

        player.sendMessage(ChatUtil.format("&6Server TPS: &f" + String.format("%.2f", tps)));
        player.sendMessage(ChatUtil.format("&bYour Ping: &f" + ping + "ms"));

        return true;
    }
}
