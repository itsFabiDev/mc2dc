package eu.zentomomc.mc2dc.Listeners;

import io.papermc.paper.event.server.ServerResourcesReloadedEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ServerRestartListener implements Listener {
    @EventHandler
    public void onServerRestart(ServerResourcesReloadedEvent event) {
        Bukkit.getServer().broadcastMessage("All Server Resources have been reloaded!\n Pls be patient if some lags occure!");
    }
}
