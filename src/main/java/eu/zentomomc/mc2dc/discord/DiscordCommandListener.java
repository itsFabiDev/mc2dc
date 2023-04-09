package eu.zentomomc.mc2dc.discord;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import eu.zentomomc.mc2dc.discord.DiscordVerifier;

public class DiscordCommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "ping":
                event.reply("Pong!").queue();
                break;
            case "tps":
                double tps = Bukkit.getServer().getTPS()[0];
                event.reply("The current TPS of the server are: " + (Math.round(tps * 100.0D) / 100.0D)).queue();
                break;
            case "verify":
                String ingamename = event.getOption("ingamename").getAsString();
                boolean verified = DiscordVerifier.verify(event.getUser().getName(), ingamename);
                if (verified) {
                    event.reply("Your Discord Account was successfully verified!").queue();
                } else {
                    event.reply("Your Discord Account could not be verified!").queue();
                }
                break;
        }
    }
}
