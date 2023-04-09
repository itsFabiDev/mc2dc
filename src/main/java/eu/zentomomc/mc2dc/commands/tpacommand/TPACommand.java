package eu.zentomomc.mc2dc.commands.tpacommand;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TPACommand implements CommandExecutor {
    private Map<UUID, UUID> requests = new HashMap<>();

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
        requests.put(target.getUniqueId(), player.getUniqueId());
        player.sendMessage("Teleport request sent to " + target.getName());

        return true;
    }

    public void acceptRequest(Player target) {
        UUID senderId = requests.remove(target.getUniqueId());
        if (senderId == null) {
            target.sendMessage("You have no pending teleport requests.");
            return;
        }

        Player sender = Bukkit.getPlayer(senderId);
        if (sender == null) {
            target.sendMessage("Teleport request sender is no longer online.");
            return;
        }

        target.teleport(sender.getLocation());
        target.sendMessage("Teleported to " + sender.getName());
    }

    public void denyRequest(Player target) {
        UUID senderId = requests.remove(target.getUniqueId());
        if (senderId == null) {
            target.sendMessage("You have no pending teleport requests.");
            return;
        }

        Player sender = Bukkit.getPlayer(senderId);
        if (sender == null) {
            target.sendMessage("Teleport request sender is no longer online.");
            return;
        }

        sender.sendMessage("Teleport request to " + target.getName() + " was denied.");
        target.sendMessage("Teleport request from " + sender.getName() + " was denied.");
    }
}
