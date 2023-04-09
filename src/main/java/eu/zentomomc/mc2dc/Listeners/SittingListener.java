package eu.zentomomc.mc2dc.Listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class SittingListener implements Listener {
    private static boolean isSitting = false;
    private static Location sitLocation;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().toString().contains("RIGHT_CLICK")
                && event.getClickedBlock() != null
                && event.getClickedBlock().getType().name().endsWith("_STAIRS")) {
            if (!isSitting) {
                isSitting = true;
                player.setSneaking(true);
                player.setVelocity(new Vector(0, 0, 0));
                player.setGravity(false);
                player.setAllowFlight(true);
                player.setFlying(true);
                player.sendTitle("", "§c§lSitting", 0, 20, 0);
                sitLocation = player.getLocation();
                sitLocation.setYaw(player.getLocation().getYaw());
                sitLocation.setPitch(0);
                sitLocation.add(0.5, -0.25, 0.5);
                player.teleport(sitLocation);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (isSitting && event.getPlayer().isSneaking()) {
            Location location = event.getPlayer().getLocation();
            if (location.getBlock().getType().name().endsWith("_STAIRS")
                    && location.getBlock().getLocation().equals(sitLocation.getBlock().getLocation())) {
                return;
            }
            isSitting = false;
            event.getPlayer().setSneaking(false);
            event.getPlayer().setGravity(true);
            event.getPlayer().setAllowFlight(false);
            event.getPlayer().setFlying(false);
            event.getPlayer().sendTitle("", "§a§lStanding up", 0, 20, 0);
            event.getPlayer().teleport(sitLocation.add(0, 1, 0));
            event.setCancelled(true);
        }
    }
}
