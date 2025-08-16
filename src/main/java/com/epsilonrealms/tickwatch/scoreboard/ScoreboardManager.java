package com.epsilonrealms.tickwatch.scoreboard;

import com.epsilonrealms.tickwatch.util.ChatUtil;
import com.epsilonrealms.tickwatch.util.ColorScaleUtil;
import com.epsilonrealms.tickwatch.util.MetricsUtil;
import com.epsilonrealms.tickwatch.util.MessagesUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("deprecation")
public class ScoreboardManager {

    private final Set<Player> enabledPlayers = new HashSet<>();
    private final JavaPlugin plugin;
    private final Path diskBase;

    public ScoreboardManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.diskBase = plugin.getDataFolder().toPath();
    }

    public void toggle(Player player) {
        if (enabledPlayers.contains(player)) {
            disable(player);
            player.sendMessage(ChatUtil.format("&cStatus scoreboard disabled"));
        } else {
            enabledPlayers.add(player);
            player.sendMessage(ChatUtil.format("&aStatus scoreboard enabled"));
        }
    }

    public void disable(Player player) {
        enabledPlayers.remove(player);
        if(Bukkit.getScoreboardManager() != null) {
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
    }

    public void clearAll() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            disable(p);
        }
        enabledPlayers.clear();
    }

    public void remove(Player player) {
        disable(player);
    }

    public void update(Player player) {
        if (!enabledPlayers.contains(player) || !player.isOp()) {
            disable(player);
            return;
        }

        org.bukkit.scoreboard.ScoreboardManager api = Bukkit.getScoreboardManager();
        if(api == null) return;

        Scoreboard board = player.getScoreboard();
        if (board == null || board == Bukkit.getScoreboardManager().getMainScoreboard()) {
            board = Bukkit.getScoreboardManager().getNewScoreboard();
        }

        Objective objective = board.getObjective("TPSPing");
        if (objective == null) {
            objective = board.registerNewObjective("TPSPing", "dummy",
                    ChatColor.GOLD + "" + ChatColor.BOLD + "⚡ Server Stats");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }

        // Clear old lines
        for (String entry : board.getEntries()) {
            board.resetScores(entry);
        }

        // Metrics
        double tps = Math.min(Bukkit.getServer().getTPS()[0], 20.0);
        int ping = player.getPing();

        int sysCpu = MetricsUtil.systemCpuPercent();
        int procCpu = MetricsUtil.processCpuPercent();

        long sysTot = MetricsUtil.systemMemTotalBytes();
        long sysUsed = MetricsUtil.systemMemUsedBytes();
        // double sysRamRatio = sysTot > 0 ? (double) sysUsed / (double) sysTot : 0.0;

        long jvmUsed = MetricsUtil.jvmHeapUsedBytes();
        long jvmMax = MetricsUtil.jvmHeapMaxBytes();
        // double jvmRamRatio = jvmMax > 0 ? (double) jvmUsed / (double) jvmMax : 0.0;

        long diskUsed = MetricsUtil.diskUsedBytes(diskBase);
        long diskTot = MetricsUtil.diskTotalBytes(diskBase);

        // Sidebar
        int line = 9;
        
        objective.getScore(ChatUtil.format("&7")).setScore(line--);

        objective.getScore(MessagesUtil.get("sidebar.tps", Map.of("tps", String.format("%.2f", tps)))).setScore(line--);
        objective.getScore(MessagesUtil.get("sidebar.ping", Map.of("ping", ping + ""))).setScore(line--);

        if(plugin.getConfig().getBoolean("show.cpu", true) && procCpu >= 0) {
            objective.getScore(MessagesUtil.get("sidebar.cpu_sys", Map.of("value", String.valueOf(sysCpu)))).setScore(line--);
        }

        if(plugin.getConfig().getBoolean("show.cpu", true) && procCpu >= 0) {
            objective.getScore(MessagesUtil.get("sidebar.cpu_proc", Map.of("value", String.valueOf(procCpu)))).setScore(line--);
        }

        if(plugin.getConfig().getBoolean("show.ram", true)) {
            objective.getScore(MessagesUtil.get("sidebar.ram_sys", Map.of(
                "used", MetricsUtil.fmtBytesGB(sysUsed),
                "total", MetricsUtil.fmtBytesGB(sysTot)
            ))).setScore(line--);

            objective.getScore(MessagesUtil.get("sidebar.ram_jvm", Map.of(
                "used", MetricsUtil.fmtBytesMB(jvmUsed),
                "total", MetricsUtil.fmtBytesMB(jvmMax)
            ))).setScore(line--);
        }

        if(plugin.getConfig().getBoolean("show.disk", true) && diskTot > 0) {
            objective.getScore(MessagesUtil.get("sidebar.disk", Map.of(
                "used", MetricsUtil.fmtBytesGB(diskUsed),
                "total", MetricsUtil.fmtBytesGB(diskTot)
            ))).setScore(line--);
        }

        player.setScoreboard(board);
    }
}
