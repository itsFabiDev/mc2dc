package eu.zentomomc.mc2dc.discord;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

public class DiscordCommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "ping":
                event.reply("Pong!").queue();
                break;
            case "tps":
                double tps = Bukkit.getServer().getTPS()[0];
                String formattedTps = String.format("%.2f", tps);
                event.reply("The current TPS of the server is: " + formattedTps).queue();
                break;
        }
    }
}
