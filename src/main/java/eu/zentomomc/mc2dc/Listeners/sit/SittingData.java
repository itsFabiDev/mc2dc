package eu.zentomomc.mc2dc.Listeners.sit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class SittingData {

    private static final Map<Player, SittingData> sittingPlayers = new HashMap<>();

    private final Player player;
    private final Location sittingLocation;
    private boolean isSitting = false;
    private float sittingYaw = 0.0f;
    private Horse horse;

    public SittingData(Player player, Location sittingLocation) {
        this.player = player;
        this.sittingLocation = sittingLocation;
    }

    public void startSitting() {
        if (!isSitting) {
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

            sittingPlayers.put(player, this);
        }
    }

    public void stopSitting() {
        if (isSitting) {
            player.setSneaking(false);
            player.setGravity(true);
            player.setAllowFlight(false);
            player.setFlying(false);
            player.sendTitle("", "§a§lStanding up", 0, 20, 0);
            Bukkit.getServer().getEntity(horse.getUniqueId()).remove();
            isSitting = false;
            horse.remove();
            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kill @e[name=" + player.getName()+",type=horse]");
                }
            }.runTaskLater(Bukkit.getPluginManager().getPlugin("mc2dc"), 1);
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
                    location.distanceSquared(sittingLocation) <= 0.25;
        }
        return false;
    }

    public static SittingData getSittingData(Player player) {
        return sittingPlayers.get(player);
    }

}
