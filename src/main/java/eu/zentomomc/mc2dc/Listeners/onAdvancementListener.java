package eu.zentomomc.mc2dc.Listeners;

import eu.zentomomc.mc2dc.discord.discordbot;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class onAdvancementListener implements Listener {
    public void onAdvancement(PlayerAdvancementDoneEvent event) {
        Player player = event.getPlayer();

        if (event.getAdvancement().getDisplay() == null) {
            return;
        }

        discordbot.sendMessage(player.getDisplayName() + " has completed the challenge [" + event.getAdvancement().getDisplay().title() + "]", player.getUniqueId().toString(), "chat");

    }
}
