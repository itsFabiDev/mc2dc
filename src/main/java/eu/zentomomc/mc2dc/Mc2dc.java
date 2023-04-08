package eu.zentomomc.mc2dc;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import eu.zentomomc.mc2dc.discord.discordbot;

//Import Listeners
import eu.zentomomc.mc2dc.Listeners.JoinListener;
import eu.zentomomc.mc2dc.Listeners.QuitListener;
import eu.zentomomc.mc2dc.Listeners.onDeathListener;
import eu.zentomomc.mc2dc.Listeners.onMessageListener;
import eu.zentomomc.mc2dc.Listeners.onAdvancementListener;

public final class Mc2dc extends JavaPlugin {
    private PluginManager manager;


    private discordbot bot;
    public void onEnable() {
        // Plugin startup logic
        bot = new discordbot();
        manager = Bukkit.getPluginManager();

        registerListener(new JoinListener());
        registerListener(new QuitListener());
        registerListener(new onDeathListener());
        registerListener(new onMessageListener());
        registerListener(new onAdvancementListener());
    }

    public void registerListener (Listener listener) {
        manager.registerEvents(listener, this);
    }

    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage("Plugin disabled");
    }
}
