package eu.zentomomc.mc2dc.discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import eu.zentomomc.mc2dc.discord.DiscordVerifier;

import java.awt.*;
import java.lang.reflect.Array;

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
                boolean verified = DiscordVerifier.verify(event.getMember().getId(), ingamename);
                if (verified) {
                    event.reply("Your Discord Account was successfully verified!").queue();
                } else {
                    event.reply("Your Discord Account could not be verified!").queue();
                }
                break;
            case "list":
                Object[] onlinePlayer = Bukkit.getServer().getOnlinePlayers().toArray();
                String ausgabe = "";
                ausgabe = "Currently are " + onlinePlayer.length + " players online: \n";
                for(Object player : onlinePlayer) {
                    ausgabe = ausgabe + player.toString() + "\n";
                }
                event.reply(ausgabe).queue();
            case "help":
                event.reply("The following commands are available: \n" +
                        "/ping - Pong! \n" +
                        "/tps - Shows the current TPS of the server \n" +
                        "/verify - Verifies your Discord Account with your Minecraft Account \n" +
                        "/list - Shows the currently online players \n" +
                        "/help - Shows this message\n" +
                        "/features - Shows the features that are enabled on the Server").queue();
                break;
            case "features":
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Features");
                embed.setColor(Color.RED);
                embed.setAuthor("Itsfabidev#3107");
                embed.addField("Discord Verification", "Verifies your Discord Account with your Minecraft Account", false);
                embed.addField("Discord Chat", "Sends all messages from the Minecraft Chat to the Discord Chat and reverse", false);
                embed.addField("Discord Join/Quit", "Sends a message to the Discord Chat when a player joins or quits the server", false);
                embed.addField("Discord Advancement", "Sends a message to the Discord Chat when a player gets an advancement", false);
                embed.addField("Discord Death", "Sends a message to the Discord Chat when a player dies", false);
                embed.addField("Discord TPS", "Sends the current TPS of the server to the Discord Chat", false);
                embed.addField("Discord List", "Sends a list of all online players to the Discord Chat", false);
                embed.addField("Discord Help", "Sends a list of all commands to the Discord Chat", false);
                embed.addField("Discord Ping", "Sends a pong! to the Discord Chat", false);
                embed.addField("Discord Features", "Sends this list", false);
                embed.addField("Minecraft Sit", "Sits down when you right click a stair", false);
                TextChannel textChannel = event.getJDA().getTextChannelById(event.getChannel().getId());
                textChannel.sendMessageEmbeds(embed.build()).queue();
                event.reply("The features are sent to the Discord Chat").queue();
                break;
            case "featurerequest":
                String feature = event.getOption("feature").getAsString();
                String message = "A new feature request was sent: " + feature + " by " + event.getMember().getNickname();
                if (discordbot.getDeveloper() != null) {
                    User user = discordbot.getDeveloper();
                    user.openPrivateChannel().queue(channel -> {
                        channel.sendMessage(message).queue();
                    });
                    event.reply("Your feature request was sent to the developer!").queue();
                } else {
                    event.reply("The developer could not be found!").queue();
                }

        }
    }
}
