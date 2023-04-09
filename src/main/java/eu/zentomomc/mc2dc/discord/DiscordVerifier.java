package eu.zentomomc.mc2dc.discord;


import java.io.*;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Scanner;
import eu.zentomomc.mc2dc.discord.discordbot;

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
        } else if (Duration.between(discordbot.getJda().getGuildById("876088468466450472").getMemberById(discordID).getTimeJoined(), OffsetDateTime.now()).toDays() > 0) {
            addVerifiedPlayer(discordID, minecraftName);
            return true;
        }
        return false;

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
    private static void addVerifiedPlayer(String discordID, String minecraftName) {
        try {
            FileWriter writer = new FileWriter(VERIFIED_PLAYERS_FILE, true);
            writer.write(discordID + ":" + minecraftName + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
