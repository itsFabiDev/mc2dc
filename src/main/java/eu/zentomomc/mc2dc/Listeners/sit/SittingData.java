package eu.zentomomc.mc2dc.Listeners.sit;

import eu.zentomomc.mc2dc.Mc2dc;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class SittingData {

    private static final Map<Player, SittingData> sittingPlayers = new HashMap<>();

    private final Player player;
    private final Location sittingLocation;
    private boolean isSitting = false;
    public boolean isSitting() {
        return isSitting;
    }
    private float sittingYaw = 0.0f;
    private Horse horse;
    private BukkitTask deleteTask;

    public SittingData(Player player, Location sittingLocation) {
        this.player = player;
        this.sittingLocation = sittingLocation;
    }

    public void startSitting() {
        if (!isSitting) {
            sittingPlayers.put(player, this);
            isSitting = true;
            // Spawn an invisible horse at the sitting location
            horse = (Horse) player.getWorld().spawnEntity(sittingLocation, EntityType.HORSE);
            horse.setAdult();
            horse.setTamed(true);
            horse.setOwner(player);
            horse.setInvulnerable(true);
            horse.setSilent(true);
            horse.setCustomName(player.getName());
            horse.setCustomNameVisible(false);
            horse.setPassenger(player);
            horse.setInvisible(true);
        }
    }


    public void stopSitting() {
        if (isSitting) {
            player.setSneaking(false);
            player.setGravity(true);
            player.setAllowFlight(false);
            player.setFlying(false);
            player.sendTitle("", "§a§lStanding up", 0, 20, 0);
            horse.remove();
            isSitting = false;

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kill @e[name=" + player.getName() + ",type=Minecraft:horse]");
            sittingPlayers.remove(player);
        }
    }


    public void updateSittingPosition() {
        if (isSitting) {
            sittingYaw = player.getLocation().getYaw();
            horse.teleport(sittingLocation.clone().add(0.5, -0.5, 0.5));
        }
    }

    public boolean isSittingOn(Location location) {
        if (isSitting) {
            return location.getWorld().equals(sittingLocation.getWorld()) &&
                    location.distanceSquared(horse.getLocation()) <= 0.25;
        }
        return false;
    }

    public static SittingData getSittingData(Player player) {
        return sittingPlayers.get(player);
    }

    public void cancelTask() {
        if (deleteTask != null) {
            deleteTask.cancel();
            deleteTask = null;
        }
    }
}
