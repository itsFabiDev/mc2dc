package eu.zentomomc.mc2dc.commands.tpacommand;

import java.util.HashMap;
import java.util.UUID;

public class TPARequests {

    private static HashMap<UUID, UUID> requests = new HashMap<>();

    public static void addTPARequest(UUID target, UUID requester) {
        requests.put(target, requester);
    }

    public static UUID getTPARequest(UUID target) {
        return requests.get(target);
    }

    public static boolean hasTPARequest(UUID target) {
        return requests.containsKey(target);
    }

    public static void removeTPARequest(UUID target) {
        requests.remove(target);
    }
}

