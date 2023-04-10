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
        String dcmessage = player.getName() + " has joined the server!";
        event.setJoinMessage(ChatColor.GREEN + player.getName() + " has joined the server!");
        player.sendMessage(ChatColor.RED + "Features: \n"+ChatColor.WHITE+"- Discord Chat\n- TPA-Feature: um dich zu einem Spieler zu TPen schreibe einfach /tpa\nDer Spieler muss anschließend tpaccept schreiben um diese anzunehmen\n- Sit-Feature: um dich zu setzen rechts klicke eine Stair.\nFür weitere Features oder Bug Hinweise schreibe mir einfach auf DC :)");

        discordbot.sendMessage(dcmessage, event.getPlayer().getUniqueId().toString(), "join");
    }
}
