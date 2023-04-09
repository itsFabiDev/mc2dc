package eu.zentomomc.mc2dc.commands.tpacommand;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class TPAAccept implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        if (TPARequests.hasTPARequest(player.getUniqueId())) {
            Player requester = Bukkit.getPlayer(TPARequests.getTPARequest(player.getUniqueId()));
            if (requester != null) {
                player.teleport(requester.getLocation());
                player.sendMessage("Teleported to " + requester.getName());
                requester.sendMessage(player.getName() + " has accepted your teleportation request.");
                TPARequests.removeTPARequest(player.getUniqueId());
            } else {
                player.sendMessage("The player who requested to teleport to you is no longer online.");
            }
        } else {
            player.sendMessage("You don't have any pending teleportation requests.");
        }

        return true;
    }
}

