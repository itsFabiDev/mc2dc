package eu.zentomomc.mc2dc.discord;


import java.io.*;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Scanner;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import org.bukkit.Bukkit;

public class DiscordVerifier {
    private static final String VERIFIED_PLAYERS_FILE = "verified_players.txt";

    /**
     * Verifies a Discord user with a Minecraft player.
     * @param discordID The Discord ID of the user.
     * @param minecraftName The Minecraft name of the player.
     * @return Whether the verification was successful.
     */
    public static boolean verify(String discordID, String minecraftName) {
        // Check if the player is already verified
        if (isPlayerVerified(discordID)) {
            return true;
        }
        Guild guild = discordbot.getJda().getGuildById("876088468466450472");
        Member member = guild.getMemberById(discordID);
        if(member == null)
            return false;
        OffsetDateTime joinedAt = member.getTimeJoined();
        OffsetDateTime now = OffsetDateTime.now();
        Duration duration = Duration.between(joinedAt, now);
        if (duration.toDays() < 1)
            return false;
        else
            return addVerifiedPlayer(discordID, minecraftName);
    }

    /**
     * Checks if a Discord user is already verified.
     * @param discordID The Discord ID of the user.
     * @return Whether the user is already verified.
     */
    private static boolean isPlayerVerified(String discordID) {
        try {
            File verifiedPlayersFile = new File(VERIFIED_PLAYERS_FILE);
            Scanner scanner = new Scanner(verifiedPlayersFile);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(":");

                if (parts[0].equals(discordID)) {
                    scanner.close();
                    return true;
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Adds a verified player to the verified players file.
     * @param discordID The Discord ID of the user.
     * @param minecraftName The Minecraft name of the player.
     */
    private static boolean addVerifiedPlayer(String discordID, String minecraftName) {
        try {
            FileWriter writer = new FileWriter(VERIFIED_PLAYERS_FILE, true);
            writer.write(discordID + ":" + minecraftName + "\n");
            writer.close();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist add " + minecraftName);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
