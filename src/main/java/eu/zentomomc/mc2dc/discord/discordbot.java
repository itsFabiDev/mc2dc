package eu.zentomomc.mc2dc.discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import eu.zentomomc.mc2dc.discord.*;
import org.bukkit.Bukkit;

import java.awt.*;
import java.time.LocalDateTime;

public class discordbot {
    private static JDA jda;
    private static String guildID = "876088468466450472";
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
        jda.shutdownNow();

        String token = Token.getToken();

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
                Commands.slash("tps", "Returns the current TPS of the Server! MCServer is online!")
        ).queue();

    }
    public static boolean sendMessage(String message, String uuid, String event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor(message, null, "https://mc-heads.net/head/" + uuid);
        eb.setTimestamp(LocalDateTime.now());
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

        TextChannel textChannel = jda.getTextChannelById("1088163336065122446");
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

}
