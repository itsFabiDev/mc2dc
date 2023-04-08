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

    /**
     * This class should let a Player on a Right Click Interaction with any Stairs
     * or slabs, be able to sit on them.
     *
     */

    private final Set<UUID> sitting = new HashSet<>();

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && block != null) {
            if (block.getType().toString().contains("_STAIRS") || block.getType().toString().contains("_SLAB")) {
                if (player.getGameMode() != GameMode.ADVENTURE && player.getGameMode() != GameMode.SPECTATOR) {
                    if (player.isSneaking()) {
                        if (sitting.contains(player.getUniqueId())) {
                            player.setVelocity(new Vector(0, 0.5, 0));
                            sitting.remove(player.getUniqueId());
                            player.sendMessage("You are no longer sitting!");
                        } else {
                            sitting.add(player.getUniqueId());
                            player.sendMessage("You are now sitting!");
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (sitting.contains(player.getUniqueId())) {
                                        player.setVelocity(new Vector(0, -0.5, 0));
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
