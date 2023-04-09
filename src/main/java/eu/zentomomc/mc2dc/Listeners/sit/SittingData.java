package eu.zentomomc.mc2dc.Listeners.sit;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class SittingData {

    private static final Map<Player, SittingData> sittingPlayers = new HashMap<>();

    private final Player player;
    private final Location sittingLocation;
    public Location getSittingLocation() {
        return sittingLocation;
    }
    private boolean isSitting = false;
    private float sittingYaw = 0.0f;

    public SittingData(Player player, Location sittingLocation) {
        this.player = player;
        this.sittingLocation = sittingLocation;
    }

    public void startSitting() {
        if (!isSitting) {
            player.setSneaking(true);
            player.setVelocity(new Vector(0, 0, 0));
            player.setGravity(false);
            player.setAllowFlight(true);
            player.setFlying(true);
            player.sendTitle("", "§c§lSitting", 0, 20, 0);
            sittingYaw = player.getLocation().getYaw();
            player.teleport(sittingLocation.add(0.5, -0.5, 0.5));
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
            player.teleport(sittingLocation.add(0.5, 0.5, 0.5));
            isSitting = false;

            sittingPlayers.remove(player);
        }
    }

    public void updateSittingPosition() {
        if (isSitting) {
            sittingYaw = player.getLocation().getYaw();
            player.teleport(sittingLocation.add(0.5, -0.5, 0.5));
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
