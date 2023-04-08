package eu.zentomomc.mc2dc.discord;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.awt.Color;

public class DiscordJoinListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Bukkit.getServer().broadcastMessage("A new player joined the server!" + event.getMember().getNickname());
    }

}


