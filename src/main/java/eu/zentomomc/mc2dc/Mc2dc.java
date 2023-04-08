package eu.zentomomc.mc2dc;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import eu.zentomomc.mc2dc.discord.discordbot;

public final class Mc2dc extends JavaPlugin {

    private discordbot bot;
    public void onEnable() {
        // Plugin startup logic
        bot = new discordbot();
    }

    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage("Plugin disabled");
    }
}
