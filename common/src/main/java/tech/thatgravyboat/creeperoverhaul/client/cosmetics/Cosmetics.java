package tech.thatgravyboat.creeperoverhaul.client.cosmetics;

import tech.thatgravyboat.creeperoverhaul.client.cosmetics.service.CosmeticsApi;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Cosmetics {

    private static final Set<UUID> DISABLED = new HashSet<>();

    public static void init() {
        CosmeticsApi.init();
    }

    public static Cosmetic getCosmetic(UUID player) {
        if (DISABLED.contains(player)) return null;
        return CosmeticsApi.getCosmetic(player);
    }

    public static void setCosmeticShown(UUID player, boolean show) {
        if (show) DISABLED.remove(player);
        else DISABLED.add(player);
    }
}
