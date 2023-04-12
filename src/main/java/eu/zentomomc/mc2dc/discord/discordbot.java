package eu.zentomomc.mc2dc.discord;

import eu.zentomomc.mc2dc.Mc2dc;
import eu.zentomomc.mc2dc.config.ConfigUtility;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.Bukkit;
import org.checkerframework.checker.units.qual.C;

import java.util.List;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class discordbot {
    static ZoneId berlinZone = ZoneId.of("Europe/Berlin");
    static LocalDateTime now = LocalDateTime.now(berlinZone);
    private static JDA jda;
    public static JDA getJda() {
        return jda;
    }
    static YamlConfiguration config = Mc2dc.getInstance().getConfiguration();
    private static String guildID = config.getString("discord.guildID", "");
    public static boolean developerFeatures = Boolean.parseBoolean(config.getString("discord.developerFeatures", ""));


    //Constructor to start up Discord Bot
    public discordbot (){
        init();
    }

    public static void init() {
        if (jda != null && jda.getStatus() == JDA.Status.CONNECTED) {
            // JDA is already connected, return without doing anything
            Bukkit.broadcastMessage("Couldn't start Discord Bot, because it is already running");
            jda.shutdownNow();
        }


        String token = config.getString("discord.token", "");

        if (guildID.isEmpty() || token.isEmpty()) {
            Bukkit.getConsoleSender().sendMessage("Discord was not activated, because of an error");
        }

        //Listeners
        DiscordCommandListener commandListener = new DiscordCommandListener();
        DiscordJoinListener joinListener = new DiscordJoinListener();
        DiscordStartUpListener startUpListener = new DiscordStartUpListener();
        DiscordMcChat onMessageListener = new DiscordMcChat();

        //JDA Builder
        JDABuilder builder = JDABuilder.createDefault(token);
        builder.setActivity(Activity.playing("Minecraft"));
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
        builder.enableIntents(GatewayIntent.GUILD_MESSAGES);
        builder.enableIntents(GatewayIntent.GUILD_MESSAGE_REACTIONS);
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        builder.enableIntents(GatewayIntent.GUILD_PRESENCES);
        builder.enableIntents(GatewayIntent.GUILD_VOICE_STATES);
        builder.enableIntents(GatewayIntent.DIRECT_MESSAGES);
        builder.enableIntents(GatewayIntent.DIRECT_MESSAGE_REACTIONS);
        builder.enableIntents(GatewayIntent.DIRECT_MESSAGE_TYPING);
        builder.enableIntents(GatewayIntent.GUILD_INVITES);
        builder.enableIntents(GatewayIntent.GUILD_MESSAGE_TYPING);
        builder.enableIntents(GatewayIntent.GUILD_WEBHOOKS);
        builder.enableIntents(GatewayIntent.GUILD_BANS);
        builder.addEventListeners(commandListener);
        builder.addEventListeners(joinListener);
        builder.addEventListeners(startUpListener);
        builder.addEventListeners(onMessageListener);
        jda = builder.build();

        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            Bukkit.getConsoleSender().sendMessage("Discord was not activated, because of an error");
            e.printStackTrace();
        }

        jda.updateCommands().addCommands(
                Commands.slash("ping", "Returns a pong! MCBot is online!"),
                Commands.slash("tps", "Returns the current TPS of the Server! MCServer is online!"),
                Commands.slash("verify", "Verifies your Minecraft Account with your Discord Account!")
                        .addOption(OptionType.STRING, "ingamename", "Your Minecraft Username goes here!", true),
                Commands.slash("list", "Returns a list of all online players!"),
                Commands.slash("help", "Returns a list of all commands!"),
                Commands.slash("features", "Returns a list of all features!"),
                Commands.slash("developerfeatures", "Returns the wanted developer features!")
                        .addOption(OptionType.STRING, "feature", "Your wanted developer feature goes here!", true)

        ).queue();

    }
    public static boolean sendMessage(String message, String uuid, String event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor(message, null, "https://mc-heads.net/head/" + uuid);
        eb.setTimestamp(now);
        if(event.equals("join"))
            eb.setColor(Color.GREEN);
        if(event.equals("quit"))
            eb.setColor(Color.RED);
        if(event.equals("chat"))
            eb.setColor(Color.BLUE);
        if (event.equals("death"))
            eb.setColor(Color.BLACK);
        if (event.equals("advancement"))
            eb.setColor(Color.YELLOW);

        TextChannel textChannel = jda.getTextChannelById(config.getString("discord.mc_chat_channel", ""));
        if (textChannel == null) {
            return false;
        }

        textChannel.sendMessageEmbeds(eb.build()).queue();
        return true;
    }

    public static void stop() {
        jda.shutdown();
    }

    public static void start() {
        init();
    }

    public static void restart() {
        stop();
        start();
    }
    /**private static User developer = jda.getUserById("334794651041136641");

    public static User getDeveloper() {
        return developer;
    }
    **/





}
