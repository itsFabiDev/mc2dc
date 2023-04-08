package eu.zentomomc.mc2dc.commands;

import eu.zentomomc.mc2dc.Mc2dc;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StopPluginCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("mc2dc.stop")) {
                Mc2dc.getInstance().getServer().getPluginManager().disablePlugin(Mc2dc.getInstance());
                player.sendMessage("Stopped Mc2dc Plugin");
            } else {
                player.sendMessage("You don't have permission to use this command!");
            }
        } else {
            Mc2dc.getInstance().getServer().getPluginManager().disablePlugin(Mc2dc.getInstance());
            sender.sendMessage("Stopped Mc2dc Plugin");
        }
        return true;
    }

}
