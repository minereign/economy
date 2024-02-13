package net.serahill.redbeardeconomy;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

import static net.serahill.redbeardeconomy.RedbeardEconomy.econ;
import static org.bukkit.Bukkit.getLogger;

public class treasureHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            getLogger().info("Only players are supported for this Example Plugin, but you should not do this!!!");
            return true;
        }

        // Check if the command sender has permission to execute the command
        if (!sender.hasPermission("treasure.basic")) {
            sender.sendMessage("You don't have permission to use this command.");
            return true; // Return true to indicate that the command was handled
        }

        if (args.length == 0) {
            sender.sendMessage(String.format("You have %s", econ.format(econ.getBalance((OfflinePlayer) sender))));
            return true;
        }
        String subCommand = args[0];
        if (Objects.equals(subCommand, "help")) {
            sender.sendMessage("Usage: /treasure");
            return true;
        }

        if (Objects.equals(subCommand, "send")) {
            if (args.length < 3) {
                sender.sendMessage("Usage: /treasure send <player> <amount>");
                return true;
            }
            String playerName = args[1];
            int amount = Integer.parseInt(args[2]);
            // Check if the player exists
            Player receiver = sender.getServer().getPlayer(playerName);
            if (receiver == null) {
                sender.sendMessage("Player not found.");
                return true;
            }
            // Check if the amount is positive
            if (amount <= 0) {
                sender.sendMessage("Amount must be positive.");
                return true;
            }
            // Check if the sender has enough money
            if (econ.getBalance((Player) sender) < amount) {
                sender.sendMessage("You don't have enough money.");
                return true;
            }
            // Transfer money
            econ.withdrawPlayer((Player) sender, amount);
            econ.depositPlayer(receiver, amount);
            sender.sendMessage("Sent " + amount + " to " + playerName);
            // Send message to receiver
            receiver.sendMessage("Received " + amount + " from " + sender.getName());
            return true;
        }

        return true;
    }
}
