package eu.zentomomc.mc2dc.Listeners;

import eu.zentomomc.mc2dc.discord.discordbot;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class onDeathListener implements Listener {
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        event.setDeathMessage(ChatColor.BLACK + "<" + player.getName() + "> " + ChatColor.WHITE + event.getDeathMessage());
        discordbot.sendMessage(event.getDeathMessage(), player.getUniqueId().toString(), "death");
    }
}
