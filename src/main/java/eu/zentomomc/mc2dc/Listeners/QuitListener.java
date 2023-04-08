package eu.zentomomc.mc2dc.Listeners;

import eu.zentomomc.mc2dc.discord.discordbot;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(ChatColor.RED + player.getName() + " has left the server!");
        discordbot.sendMessage(event.getQuitMessage(), event.getPlayer().getUniqueId().toString(), "quit");
    }
}
