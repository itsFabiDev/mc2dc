package eu.zentomomc.mc2dc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import eu.zentomomc.mc2dc.discord.discordbot;

public class DiscordStartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("mc2dc.start")) {
                discordbot.start();
                player.sendMessage("Started Discord Bot");
            } else {
                player.sendMessage("You don't have permission to use this command!");
            }
        } else {
            discordbot.start();
            sender.sendMessage("Started Discord Bot");
        }
        return true;
    }
}
