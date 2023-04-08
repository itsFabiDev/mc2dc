package eu.zentomomc.mc2dc.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import eu.zentomomc.mc2dc.discord.discordbot;

public class onMessageListener implements Listener {
    @EventHandler
    public void onMessage(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String dcmessage = event.getMessage();
        String playerName = player.getName().toString();
        String message = dcmessage.replace("<" + playerName + ">", ChatColor.DARK_BLUE + "<" + playerName + "> " + ChatColor.WHITE);
        event.setMessage(message);
        discordbot.sendMessage(dcmessage, player.getUniqueId().toString(), "chat");
    }
}
