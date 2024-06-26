package tech.thatgravyboat.creeperoverhaul.client.cosmetics;

import com.google.gson.JsonObject;
import com.mojang.util.UndashedUuid;
import com.teamresourceful.resourcefullib.common.utils.WebUtils;

import java.util.*;

public class Cosmetics {

    private static final String URL = "https://creeper-overhaul.thatgravyboat.tech/cosmetics";

    private static final Map<String, Cosmetic> COSMETICS = new HashMap<>();
    private static final Map<UUID, String> USERS = new HashMap<>();
    private static final Set<UUID> DISABLED = new HashSet<>();

    private static void addCosmetic(String id, Cosmetic cosmetic) {
        COSMETICS.put(id, cosmetic);
    }

    private static void addUser(String uuid, String cosmetic) {
        try {
            getUUID(uuid).ifPresent(uid -> USERS.put(uid, cosmetic));
        } catch (Exception ignored) {
            //does nothing
        }
    }

    private static Optional<UUID> getUUID(String uuid) throws IllegalArgumentException {
        if (uuid.length() == 36 && uuid.contains("-")) {
            return Optional.of(UndashedUuid.fromStringLenient(uuid));
        } else if (uuid.length() == 32) {
            return Optional.of(UUID.fromString(uuid));
        }
        return Optional.empty();
    }

    public static void init() {
        JsonObject json = WebUtils.getJson(URL, true);
        if (json == null) return;
        JsonObject cosmetics = json.getAsJsonObject("cosmetics");
        JsonObject users = json.getAsJsonObject("users");
        cosmetics.entrySet().forEach(entry -> {
            try {
                String id = entry.getKey();
                JsonObject data = entry.getValue().getAsJsonObject();
                CosmeticTexture texture = new CosmeticTexture(data.get("texture").getAsString());
                CosmeticModel.create(data.get("model").getAsString(), texture, model ->
                        addCosmetic(
                                id,
                                new Cosmetic(
                                        texture,
                                        model,
                                        CosmeticAnchor.valueOf(data.get("anchor").getAsString())
                                )
                        )
                );
            } catch (Exception ignored){
                //does nothing
            }
        });
        users.entrySet().forEach(entry -> addUser(entry.getKey(), entry.getValue().getAsString()));
    }

    public static Cosmetic getCosmetic(UUID player) {
        if (DISABLED.contains(player)) return null;
        String cosmetic = USERS.get(player);
        if (cosmetic == null) return COSMETICS.get("default");
        return COSMETICS.get(cosmetic);
    }

    public static void setCosmeticShown(UUID player, boolean show) {
        if (show) {
            DISABLED.remove(player);
        } else {
            DISABLED.add(player);
        }
    }
}
