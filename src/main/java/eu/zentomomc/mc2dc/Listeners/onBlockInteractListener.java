package eu.zentomomc.mc2dc.Listeners;

import eu.zentomomc.mc2dc.Mc2dc;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class onBlockInteractListener implements Listener {

    private final Set<UUID> sittingPlayers = new HashSet<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block clickedBlock = event.getClickedBlock();
            if (clickedBlock != null && clickedBlock.getType() == Material.OAK_STAIRS) {
                Player player = event.getPlayer();
                if (sittingPlayers.contains(player.getUniqueId())) {
                    // Player is already sitting, so stand them up
                    standUp(player);
                } else {
                    // Player is not sitting, so sit them down
                    sitDown(player, clickedBlock);
                }
            }
        }
    }

    private void sitDown(Player player, Block stairBlock) {
        if (player.getLocation().getBlock().getRelative(0, -1, 0).getType().isSolid()) {
            // Calculate seat location based on player's facing direction
            Vector facingDirection = player.getLocation().getDirection().normalize().setY(0);
            Vector seatOffset = facingDirection.multiply(0.5).setY(0.5);
            Vector seatLocation = stairBlock.getLocation().toVector().add(seatOffset);

            // Set player's sitting status and prevent movement
            sittingPlayers.add(player.getUniqueId());
            player.setSneaking(true);
            player.setVelocity(new Vector(0, 0, 0));
            player.teleport(seatLocation.toLocation(stairBlock.getWorld()));
            player.setFallDistance(0);
            player.setGameMode(GameMode.ADVENTURE);

            // Schedule task to periodically check if player is still sitting
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!sittingPlayers.contains(player.getUniqueId())) {
                        // Player is no longer sitting, so cancel task
                        cancel();
                    } else if (player.getLocation().getBlock().getType() != Material.OAK_STAIRS || !player.getLocation().getBlock().getRelative(0, -1, 0).getType().isSolid()) {
                        // Player has moved off the stair block or is no longer standing on a solid block, so stand them up and cancel task
                        standUp(player);
                        cancel();
                    }
                }
            }.runTaskTimer(Mc2dc.getInstance(), 0L, 1L);
        }
    }

    private void standUp(Player player) {
        // Set player's standing status and allow movement
        sittingPlayers.remove(player.getUniqueId());
        player.setSneaking(false);
        player.setVelocity(player.getLocation().getDirection().normalize().multiply(0.5));
        player.setGameMode(GameMode.SURVIVAL);
    }
}
