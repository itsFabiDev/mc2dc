package eu.zentomomc.mc2dc;

import eu.zentomomc.mc2dc.Listeners.*;
import eu.zentomomc.mc2dc.Listeners.sit.SittingData;
import eu.zentomomc.mc2dc.Listeners.sit.SittingListener;
import eu.zentomomc.mc2dc.Listeners.sit.onPlayerMove;
import eu.zentomomc.mc2dc.commands.*;
import eu.zentomomc.mc2dc.config.ConfigUtility;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import eu.zentomomc.mc2dc.discord.discordbot;
import org.bukkit.configuration.file.YamlConfiguration;

public final class Mc2dc extends JavaPlugin {
    private PluginManager manager;

    private static Mc2dc instance;
    public static Mc2dc getInstance() {
        return instance;
    }
    private static discordbot bot;

    private ConfigUtility config;
    public void onLoad() {
        instance = this;
        this.config = new ConfigUtility("config.yml");
    }


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
        registerListener(new onPlayerMove());
        //registerListener(new onPrepareAnvilListener());
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
        for(Player player: SittingData.players()) {
            SittingData.getSittingData(player).stopSitting();
        }
        Bukkit.getConsoleSender().sendMessage("Plugin disabled");
    }

    public static discordbot getBot() {
        return bot;
    }

    public YamlConfiguration getConfiguration() {
        return config.getConfig();
    }

}
