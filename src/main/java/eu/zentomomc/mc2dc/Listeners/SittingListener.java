package eu.zentomomc.mc2dc.Listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import org.bukkit.util.Vector;
public class SittingListener implements Listener {
    private static boolean isSitting = false;
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().toString().contains("RIGHT_CLICK")
                && player.getLocation().getBlock().getType().toString().contains("STAIRS")) {
            isSitting = true;
            player.setSneaking(true);
            player.setVelocity(new Vector(0, 0, 0));
            player.setGravity(false);
            player.setAllowFlight(true);
            player.setFlying(true);
            player.sendTitle("", "§c§lSitting", 0, 20, 0);
            player.teleport(player.getLocation().add(0.5, -0.25, 0.5));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if(isSitting) {
            Player player = event.getPlayer();
            Location location = player.getLocation();
            if (player.isSneaking()) {
                if (location.getBlock().getType() != Material.OAK_STAIRS) {
                    isSitting = false;
                    player.setSneaking(false);
                    player.setGravity(true);
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    player.sendTitle("", "§a§lStanding up", 0, 20, 0);
                    player.teleport(location.add(0, 1, 0));
                    event.setCancelled(true);
                }
            }
        }
    }
}
