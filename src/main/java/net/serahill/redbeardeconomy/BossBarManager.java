package net.serahill.redbeardeconomy;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

import static net.serahill.redbeardeconomy.RedbeardEconomy.econ;

public class BossBarManager {
    private final JavaPlugin plugin;
    private final HashMap<UUID, BossBar> playerBossBars = new HashMap<>();

    public BossBarManager(JavaPlugin plugin) {
        this.plugin = plugin;
        startBossBarUpdateTask();
    }

    public void addPlayer(Player player) {
        if (!playerBossBars.containsKey(player.getUniqueId())) {
            BossBar bar = Bukkit.createBossBar(formatBarTitle(player), BarColor.PURPLE, BarStyle.SOLID);
            bar.addPlayer(player);
            bar.setVisible(true);
            playerBossBars.put(player.getUniqueId(), bar);
        }
    }

    public void removePlayer(Player player) {
        BossBar bar = playerBossBars.remove(player.getUniqueId());
        if (bar != null) {
            bar.removeAll();
        }
    }

    private String formatBarTitle(Player player) {
        String treasure = econ.format(econ.getBalance((OfflinePlayer) player));
        return player.getName() + " || " + treasure + " treasure";
    }

    private void startBossBarUpdateTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                playerBossBars.forEach((uuid, bar) -> {
                    Player player = Bukkit.getPlayer(uuid);
                    if (player != null && player.isOnline()) {
                        bar.setTitle(formatBarTitle(player));
                    }
                });
            }
        }.runTaskTimer(plugin, 0L, 20L * 5); // Update every 5 seconds
    }
}