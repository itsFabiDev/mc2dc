package eu.zentomomc.mc2dc.discord;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import javax.annotation.Nonnull;
public class DiscordMcChat extends ListenerAdapter {

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        User author = event.getAuthor();
        if (event.isWebhookMessage()) return;
        if (author.isBot()) return;
        if (event.getChannel().getId().equals("1088163336065122446")) {
            String message = event.getMessage().getContentDisplay();
            Bukkit.broadcastMessage(
                    ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Discord" + ChatColor.DARK_GRAY + "] " +
                            ChatColor.WHITE + "<" + author.getName() + "#" + author.getDiscriminator() + "> " + message);
        }
    }


}

