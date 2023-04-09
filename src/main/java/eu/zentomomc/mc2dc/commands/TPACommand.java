package eu.zentomomc.mc2dc.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class TPACommand implements CommandExecutor {

    public static String tpaRequester;
    public static String tpaReceiver;
    public static boolean awaitingResponse;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage("Usage: /tpa <playername>");
            return true;
        }

        Player receiver = Bukkit.getPlayer(args[0]);

        if (receiver == null) {
            player.sendMessage("Player " + args[0] + " is not online!");
            return true;
        }

        tpaRequester = player.getName();
        tpaReceiver = receiver.getName();
        awaitingResponse = true;

        receiver.sendMessage(ChatColor.RED + tpaRequester + " wants to teleport to you. Type tpaccept to accept or tpdeny to deny.");
        player.sendMessage(ChatColor.RED + "TPA request sent to " + tpaReceiver + ".");
        return true;
    }

    public static void onTPAccept(Player player) {
        Player requester = Bukkit.getPlayer(tpaRequester);
        Player receiver = Bukkit.getPlayer(tpaReceiver);
        if (requester != null) {
            requester.sendMessage(ChatColor.GOLD + tpaReceiver + " has accepted your TPA request.");
            requester.sendMessage(ChatColor.RED + "Teleporting in 5 seconds...");
            receiver.sendMessage(ChatColor.RED + "Teleporting " + tpaRequester + " to you in 5 seconds...\n" +
                    "Please stand still. And get to a safe place.");

            new BukkitRunnable() {
                @Override
                public void run() {
                    requester.teleport(player);
                }
            }.runTaskLater(Bukkit.getPluginManager().getPlugin("mc2dc"), 20 * 5);
            player.sendMessage(ChatColor.YELLOW + "Teleporting " + tpaRequester + " to you.");
            resetTpa();
        }
    }

    public static void onTPDeny(Player player) {
        Player requester = Bukkit.getPlayer(tpaRequester);
        if (requester != null) {
            requester.sendMessage(ChatColor.RED + tpaReceiver + " has denied your TPA request.");
            player.sendMessage(ChatColor.RED + "TPA request denied.");
            resetTpa();
        }
    }

    private static void resetTpa() {
        tpaRequester = null;
        tpaReceiver = null;
        awaitingResponse = false;
    }

}
