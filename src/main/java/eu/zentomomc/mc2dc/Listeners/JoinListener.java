package eu.zentomomc.mc2dc.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import eu.zentomomc.mc2dc.discord.discordbot;
public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(ChatColor.GREEN + player.getName() + " has joined the server!");
        discordbot.sendMessage(event.getJoinMessage(), event.getPlayer().getUniqueId().toString(), "join");
    }
}
