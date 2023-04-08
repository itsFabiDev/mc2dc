package eu.zentomomc.mc2dc;

import eu.zentomomc.mc2dc.Listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import eu.zentomomc.mc2dc.discord.discordbot;

//Import Listeners
import org.bukkit.scheduler.BukkitRunnable;
import eu.zentomomc.mc2dc.commands.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public final class Mc2dc extends JavaPlugin {
    private PluginManager manager;
    private static Mc2dc instance;
    public Map<UUID, UUID> tpaRequests = new HashMap<>();
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
        registerListener(new onBlockInteractListener());
        registerCommand(new TPACommand(), "tpa");
    }



    public void registerListener (Listener listener) {
        manager.registerEvents(listener, this);
    }

    public void registerCommand (CommandExecutor command, String name) {
        getCommand(name).setExecutor(command);
    }

    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage("Plugin disabled");
    }
}
