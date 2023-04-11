package eu.zentomomc.mc2dc.Listeners.sit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class onPlayerMove implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        SittingData sittingData = SittingData.getSittingData(player);
        if (sittingData != null && sittingData.isSitting()) {
            if (player.isSneaking() || !player.isInsideVehicle()) {
                sittingData.stopSitting();
            } else {
                event.setCancelled(true);
            }
        }
    }
}
