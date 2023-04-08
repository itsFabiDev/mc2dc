package eu.zentomomc.mc2dc.Listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class onBlockInteractListener extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (block != null && event.getAction() == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.OAK_STAIRS) {
            // Teleport the player to the top of the stairs and set their sitting state.
            Location location = block.getLocation().add(0.5, 0.5, 0.5);
            player.teleport(location);
            player.setSneaking(true);

            // Create a new ArmorStand entity at the location of the stair and set the player as its passenger.
            ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
            armorStand.setVisible(false);
            armorStand.setGravity(false);
            armorStand.setSmall(true);
            armorStand.setPassenger(player);

            // Rotate the ArmorStand to match the orientation of the stair.
            Vector direction = block.getFace(player.getLocation().getBlock()).getDirection();
            armorStand.teleport(location.add(direction.getX(), direction.getY(), direction.getZ()));
            EulerAngle rotation = new EulerAngle(0, getEulerAngle(direction), 0);
            armorStand.setHeadPose(rotation);
        }
    }

    private double getEulerAngle(Vector direction) {
        double angle = Math.atan2(direction.getZ(), direction.getX());
        return angle > 0 ? angle : 2*Math.PI + angle;
    }
}
