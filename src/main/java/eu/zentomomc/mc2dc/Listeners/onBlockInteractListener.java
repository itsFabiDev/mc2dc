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
import org.bukkit.util.EulerAngle;
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
                            ArmorStand armorStand = sittingPlayers.get(player.getUniqueId());
                            armorStand.remove();
                            sittingPlayers.remove(player.getUniqueId());
                            player.sendMessage("You are no longer sitting!");
                        } else {
                            ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(block.getLocation().add(0.5, -0.6, 0.5), EntityType.ARMOR_STAND);
                            armorStand.setGravity(false);
                            armorStand.setVisible(false);
                            armorStand.setSmall(true);
                            armorStand.setMarker(true);
                            armorStand.setBasePlate(false);
                            armorStand.setCanPickupItems(false);
                            armorStand.setCustomName(player.getName() + "'s chair");
                            armorStand.setCustomNameVisible(false);
                            armorStand.setHeadPose(new EulerAngle(-Math.PI / 2, 0, 0));
                            sittingPlayers.put(player.getUniqueId(), armorStand);
                            player.sendMessage("You are now sitting!");
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (sittingPlayers.containsKey(player.getUniqueId())) {
                                        ArmorStand armorStand = sittingPlayers.get(player.getUniqueId());
                                        armorStand.teleport(block.getLocation().add(0.5, -0.6, 0.5));
                                        armorStand.setHeadPose(new EulerAngle(-Math.PI / 2, 0, 0));
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
