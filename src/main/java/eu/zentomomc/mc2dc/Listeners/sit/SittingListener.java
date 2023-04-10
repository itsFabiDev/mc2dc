package eu.zentomomc.mc2dc.Listeners.sit;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class SittingListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && clickedBlock != null) {
            BlockData blockData = clickedBlock.getBlockData();
            if (blockData instanceof Stairs) {
                Stairs stairsData = (Stairs) blockData;
                if (stairsData.getHalf() == Bisected.Half.BOTTOM) {
                    SittingData sittingData = new SittingData(player, clickedBlock.getLocation());
                    sittingData.startSitting();
                }
            }
        }
    }

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