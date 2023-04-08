package eu.zentomomc.mc2dc.Listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
public class onBlockInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block clickedBlock = event.getClickedBlock();
            if (clickedBlock != null && clickedBlock.getType() == Material.OAK_STAIRS) {
                Player player = event.getPlayer();
                Vector facingDirection = player.getLocation().getDirection().normalize().setY(0);
                Vector seatOffset = facingDirection.multiply(0.5).setY(0.5);
                Vector seatLocation = clickedBlock.getLocation().toVector().add(seatOffset);
                player.teleport(seatLocation.toLocation(clickedBlock.getWorld()));
            }
        }
    }
}

