package eu.zentomomc.mc2dc.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import eu.zentomomc.mc2dc.discord.*;
import org.bukkit.Bukkit;

public class discordbot {
    private static JDA jda;
    private static String guildID = "876088468466450472";
    //Constructor to start up Discord Bot
    public discordbot (){
        init();
    }

    public static void init() {
        String token = Token.getToken();

        if (guildID.isEmpty() || token.isEmpty()) {
            Bukkit.getConsoleSender().sendMessage("Discord was not activated, because of an error");
        }
        DiscordCommandListener commandListener = new DiscordCommandListener();
        DiscordJoinListener joinListener = new DiscordJoinListener();
        JDABuilder builder = JDABuilder.createDefault(token);
        builder.setActivity(Activity.playing("Minecraft"));
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
        builder.addEventListeners(commandListener);
        builder.addEventListeners(joinListener);
        jda = builder.build();

        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            Bukkit.getConsoleSender().sendMessage("Discord was not activated, because of an error");
            e.printStackTrace();
        }

        jda.updateCommands().addCommands(
                Commands.slash("ping", "Returns a pong! MCBot is online!")
        ).queue();
        jda.updateCommands().addCommands(
                Commands.slash("tps", "Returns the current TPS of the Server! MCServer is online!")
        ).queue();
    }

}
