package eu.zentomomc.mc2dc.Listeners;

import eu.zentomomc.mc2dc.discord.discordbot;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class onDeathListener implements Listener {
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        String dcmessage = event.getDeathMessage();
        event.setDeathMessage(ChatColor.RED + player.getName() + " has died!");
        discordbot.sendMessage(dcmessage, player.getUniqueId().toString(), "death");
    }
}
