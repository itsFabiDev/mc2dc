package eu.zentomomc.mc2dc.config;

import eu.zentomomc.mc2dc.Mc2dc;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigUtility {
    private final File file;
    private final YamlConfiguration config;

    public ConfigUtility(String filename) {
        File dir = new File("./plugins/mc2dc/");

        if (!dir.exists())
            dir.mkdirs();

        this.file = new File(dir, filename);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                Bukkit.getServer().broadcastMessage(ChatColor.RED + "Shutting down plugin because config file could not be created");
                Bukkit.getPluginManager().disablePlugin(Mc2dc.getInstance());
            }
        }

        this.config = YamlConfiguration.loadConfiguration(file);

        setDefault("discord.token", "");
        setDefault("discord.guildID", "");
        setDefault("discord.mc_chat_channel", "");
        setDefault("discord.developerFeatures", true);
        this.save();
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("WARNING: UNABLE TO SAVE CONFIG!");
        }
    }

    private void setDefault(String key, Object defaultValue) {
        if (!this.config.contains(key)) {
            this.config.set(key, defaultValue);
        }
    }
}

