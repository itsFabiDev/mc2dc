package eu.zentomomc.mc2dc.Listeners.sit;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
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
            player.setSneaking(false);
            player.setVelocity(new Vector(0, 0, 0));
            player.setGravity(false);
            player.setAllowFlight(false);
            player.setFlying(false);
            player.sendTitle("", "§c§lSitting", 0, 20, 0);
            sittingYaw = player.getLocation().getYaw();
            horse = (Horse) player.getWorld().spawnEntity(sittingLocation.clone().add(0.5, -0.5, 0.5), EntityType.HORSE);
            horse.setInvulnerable(true);
            horse.setTamed(true);
            horse.setOwner(player);
            horse.setAdult();
            horse.addPassenger(player);
            horse.setPassenger(player);
            horse.setInvisible(true);
            horse.setSilent(true);
            isSitting = true;

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
            horse.remove();
            isSitting = false;

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
