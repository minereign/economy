package net.serahill.redbeardeconomy;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.security.Permission;

public final class RedbeardEconomy extends JavaPlugin implements CommandExecutor, Listener {

    private RedbeardEconomy redbeardEconomy;
    static Economy econ = null;
    private static final Permission perms = null;
    private static final Chat chat = null;
    private BossBarManager bossBarManager;

    @Override
    public void onEnable() {

        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        bossBarManager = new BossBarManager(this);
        getServer().getPluginManager().registerEvents(this, this);

        this.saveDefaultConfig();
        this.reloadConfig();

        this.getCommand("treasure").setExecutor((CommandExecutor) new treasureHandler());

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        bossBarManager.addPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        bossBarManager.removePlayer(event.getPlayer());
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }
}
