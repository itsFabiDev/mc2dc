package eu.zentomomc.mc2dc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import eu.zentomomc.mc2dc.discord.discordbot;

public class DiscordStopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("mc2dc.stop")) {
                discordbot.stop();
                player.sendMessage("Stopped Discord Bot");
            } else {
                player.sendMessage("You don't have permission to use this command!");
            }
        } else {
            discordbot.stop();
            sender.sendMessage("Stopped Discord Bot");
        }
        return true;
    }
}
