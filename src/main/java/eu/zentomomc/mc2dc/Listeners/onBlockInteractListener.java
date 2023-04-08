package eu.zentomomc.mc2dc.Listeners;

import eu.zentomomc.mc2dc.Mc2dc;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class onBlockInteractListener implements Listener {

    /**
     * This class should let a Player on a Right Click Interaction with any Stairs
     * or slabs, be able to sit on them.
     *
     */

    private final Map<UUID, ArmorStand> sittingPlayers = new HashMap<>();

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && block != null) {
            if (block.getType().toString().contains("_STAIRS") || block.getType().toString().contains("_SLAB")) {
                if (player.getGameMode() != GameMode.ADVENTURE && player.getGameMode() != GameMode.SPECTATOR) {
                    if (player.isSneaking()) {
                        if (sittingPlayers.containsKey(player.getUniqueId())) {
                            // Remove the sitting armor stand and update the player's location
                            sittingPlayers.get(player.getUniqueId()).remove();
                            sittingPlayers.remove(player.getUniqueId());
                            player.teleport(player.getLocation().add(0, 1, 0));
                            player.sendMessage("You are no longer sitting!");
                        } else {
                            // Spawn a new armor stand and position it under the player
                            ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation().subtract(0, 1, 0), EntityType.ARMOR_STAND);
                            armorStand.setGravity(false);
                            armorStand.setVisible(false);
                            armorStand.setSmall(true);
                            armorStand.setMarker(true);
                            armorStand.addPassenger(player);
                            sittingPlayers.put(player.getUniqueId(), armorStand);
                            player.sendMessage("You are now sitting!");

                            // Schedule a task to update the player's location to make it look like they are sitting
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (sittingPlayers.containsKey(player.getUniqueId())) {
                                        player.teleport(sittingPlayers.get(player.getUniqueId()).getLocation().add(0, 1, 0));
                                    } else {
                                        cancel();
                                    }
                                }
                            }.runTaskTimer(Mc2dc.getInstance(), 0, 1);
                        }
                    }
                } else {
                    player.sendMessage("You can't sit while in adventure mode or spectator mode!");
                }
            }
        }
    }
}
