package eu.zentomomc.mc2dc.commands.tpacommand;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TPACommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("Usage: /tpa <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("Player not found.");
            return true;
        }

        Player player = (Player) sender;
        player.sendMessage("Teleportation request sent to " + target.getName());

        // Store the teleportation request information in a temporary storage
        TPARequests.addTPARequest(target.getUniqueId(), player.getUniqueId());

        // Send the request to the target player
        target.sendMessage(player.getName() + " has requested to teleport to you. Type '/tpaccept' to accept or '/tpadeny' to deny the request.");

        return true;
    }
}

