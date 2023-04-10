package eu.zentomomc.mc2dc.Listeners;

import eu.zentomomc.mc2dc.Listeners.sit.SittingData;
import eu.zentomomc.mc2dc.discord.discordbot;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if(SittingData.getSittingData(event.getPlayer()).isSitting()) {
            SittingData.getSittingData(event.getPlayer()).stopSitting();
        }
        Player player = event.getPlayer();
        String dcmessage = player.getName() + " has left the server!";
        event.setQuitMessage(ChatColor.RED + player.getName() + " has left the server!");
        discordbot.sendMessage(dcmessage, event.getPlayer().getUniqueId().toString(), "quit");
    }
}
