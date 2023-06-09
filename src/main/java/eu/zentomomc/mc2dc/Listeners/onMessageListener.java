package eu.zentomomc.mc2dc.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import eu.zentomomc.mc2dc.discord.discordbot;
import eu.zentomomc.mc2dc.commands.TPACommand;

public class onMessageListener implements Listener {
    @EventHandler
    public void onMessage(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (TPACommand.awaitingResponse && player.getName().equals(TPACommand.tpaReceiver)) {

            String message = event.getMessage().toLowerCase();
            if (message.equals("tpaccept")) {
                TPACommand.onTPAccept(player);
                event.setCancelled(true);
            } else if (message.equals("tpdeny")) {
                TPACommand.onTPDeny(player);
                event.setCancelled(true);
            } else {
                player.sendMessage(ChatColor.RED + "Invalid command. Type tpaccept or tpdeny.");
                event.setCancelled(true);
            }
            TPACommand.awaitingResponse = false;
        } else {
            String dcmessage = event.getMessage();
            event.setFormat(ChatColor.DARK_BLUE + "<" + player.getName().toString() + "> " + ChatColor.WHITE + "%2$s");
            event.setMessage(event.getMessage());
            discordbot.sendMessage(dcmessage, player.getUniqueId().toString(), "chat");
        }
    }
}
