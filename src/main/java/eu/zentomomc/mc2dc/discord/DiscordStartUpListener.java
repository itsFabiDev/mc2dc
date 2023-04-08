package eu.zentomomc.mc2dc.discord;

import net.dv8tion.jda.api.JDA;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordStartUpListener extends ListenerAdapter {
    public void onReady(ReadyEvent event) {
        JDA jda = event.getJDA();
        jda.getTextChannelById("1088163336065122446").sendMessage("The MC Server is now up and running!").queue();
    }
}
