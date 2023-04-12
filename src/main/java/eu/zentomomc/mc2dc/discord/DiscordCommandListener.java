package eu.zentomomc.mc2dc.discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
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
            case "developerfeatures":
                String option = event.getOption("feature").getAsString();
                if(discordbot.developerFeatures) {
                    switch (option) {
                        case "enable":
                            discordbot.developerFeatures = true;
                            event.reply("Developer features are now enabled!").queue();
                            break;
                        case "disable":
                            discordbot.developerFeatures = false;
                            event.reply("Developer features are now disabled!").queue();
                            break;
                        case "status":
                            if (discordbot.developerFeatures) {
                                event.reply("Developer features are enabled!").queue();
                            } else {
                                event.reply("Developer features are disabled!").queue();
                            }
                            break;
                        case "id":
                            event.reply("Your User ID is: " + event.getUser().getId().toString()).queue();
                            break;
                        case "features":
                            TextChannel textChannel1 = event.getJDA().getTextChannelById(event.getChannel().getId());
                            EmbedBuilder embed1 = new EmbedBuilder();
                            embed1.setTitle("Developer Features");
                            embed1.setColor(Color.RED);
                            embed1.addField("Developer Features Status", "Developer features are enabled!", false);
                            embed1.addField("Developer Features ID", "Your User ID is: " + event.getUser().getId().toString(), false);
                            embed1.addField("Discord Gateway Ping", "The current Gateway ping is: " + String.valueOf(discordbot.getJda().getGatewayPing()), false);
                            embed1.addField("Discord REST Ping", "The current REST ping is: " + String.valueOf(discordbot.getJda().getRestPing().complete()), false);
                            textChannel1.sendMessageEmbeds(embed1.build()).queue();
                            event.reply("The developer features are sent to the Discord Chat").queue();
                            break;
                    }
                } else {
                    event.reply("Developer features are disabled!").queue();
                }
        }
    }
}
