package eu.zentomomc.mc2dc;

import eu.zentomomc.mc2dc.Listeners.*;
import eu.zentomomc.mc2dc.Listeners.sit.SittingListener;
import eu.zentomomc.mc2dc.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import eu.zentomomc.mc2dc.discord.discordbot;

public final class Mc2dc extends JavaPlugin {
    private PluginManager manager;

    private static Mc2dc instance;
    public static Mc2dc getInstance() {
        return instance;
    }
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
        registerListener(new SittingListener());
        registerListener(new ServerRestartListener());
        registerCommand(new TPACommand(), "tpa");
        registerCommand(new DiscordStopCommand(),"stopdc");
        registerCommand(new DiscordStartCommand(),"startdc");
        registerCommand(new DiscordRestartCommand(),"restartdc");
        registerCommand(new StopPluginCommand(),"stopplugin");
    }



    public void registerListener (Listener listener) {
        manager.registerEvents(listener, this);
    }

    public void registerCommand (CommandExecutor command, String name) {
        getCommand(name).setExecutor(command);
    }
    public void onDisable() {
        // Plugin shutdown logic
        discordbot.stop();
        Bukkit.getConsoleSender().sendMessage("Plugin disabled");
    }
}
