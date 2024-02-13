package net.serahill.redbeardeconomy;

public class treasureHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if the command sender has permission to execute the command
        if (!sender.hasPermission("myplugin.command.example")) {
            sender.sendMessage("You don't have permission to use this command.");
            return true; // Return true to indicate that the command was handled
        }

        // Your command logic here
        sender.sendMessage("Your command was executed successfully!");
        return true; // Return true to indicate that the command was handled
    }
}
