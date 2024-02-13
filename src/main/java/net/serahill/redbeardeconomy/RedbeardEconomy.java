package net.serahill.redbeardeconomy;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.security.Permission;

public final class RedbeardEconomy extends JavaPlugin implements CommandExecutor {

    private RedbeardEconomy redbeardEconomy;
    static Economy econ = null;
    private static final Permission perms = null;
    private static final Chat chat = null;

    @Override
    public void onEnable() {

        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.saveDefaultConfig();
        this.reloadConfig();

        this.getCommand("treasure").setExecutor((CommandExecutor) new treasureHandler());

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
