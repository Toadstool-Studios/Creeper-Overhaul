package tech.thatgravyboat.creeperoverhaul.client.cosmetics.service;

import com.mojang.logging.LogUtils;
import com.teamresourceful.resourcefulcosmetics.ResourcefulCosmetics;
import com.teamresourceful.resourcefulcosmetics.errors.*;
import net.minecraft.Optionull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import tech.thatgravyboat.creeperoverhaul.client.cosmetics.Cosmetic;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class CosmeticsApi {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final ResourcefulCosmetics<Cosmetic> API = ResourcefulCosmetics.create(
            "https://creeperoverhaul.thatgravyboat.tech/",
            Cosmetic::fromJson,
            error -> LOGGER.error("Failed to load cosmetics", error)
    );

    public static void init() {
        // class loading will initialize the API
    }

    private static CompletableFuture<StatusCode> tryApiCall(TryApiCall runnable) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                runnable.run();
                return StatusCode.OK;
            } catch (BadRequestException e) {
                return StatusCode.BAD_REQUEST;
            } catch (ForbiddenException e) {
                return StatusCode.FORBIDDEN;
            } catch (InternalServerException e) {
                return StatusCode.INTERNAL_SERVER_ERROR;
            } catch (NotFoundException e) {
                return StatusCode.NOT_FOUND;
            } catch (UnauthorizedException e) {
                return StatusCode.UNAUTHORIZED;
            } catch (Exception e) {
                LOGGER.error("Failed to call API", e);
                return StatusCode.UNKOWN_ERROR;
            }
        });
    }

    private static UUID getProfileId() {
        return Minecraft.getInstance().getUser().getProfileId();
    }

    public static CompletableFuture<StatusCode> login() {
        return tryApiCall(() -> {
            User user = Minecraft.getInstance().getUser();
            String serverId = UUID.randomUUID().toString();
            Minecraft.getInstance().getMinecraftSessionService().joinServer(user.getProfileId(), user.getAccessToken(), serverId);

            API.login(user.getProfileId(), user.getName(), serverId);
        });
    }

    public static CompletableFuture<StatusCode> getCosmetics() {
        return tryApiCall(() -> API.getCosmetics(getProfileId(), false));
    }

    public static CompletableFuture<StatusCode> setCosmetic(@Nullable Cosmetic cosmetic) {
        return tryApiCall(() -> API.setCosmetic(getProfileId(), Optionull.map(cosmetic, Cosmetic::id)));
    }

    public static CompletableFuture<StatusCode> claimReward(String code) {
        return tryApiCall(() -> API.claimCosmetic(getProfileId(), code));
    }


    public static boolean hasSessionToken() {
        return API.hasSession(Minecraft.getInstance().getUser().getProfileId());
    }

    public static List<String> getAvailableCosmetics() {
        try {
            return API.getCosmetics(getProfileId(), true).available();
        }catch (Exception e) {
            return List.of();
        }
    }

    public static Cosmetic getCosmetic(String id) {
        return API.getCosmetic(id);
    }

    public static Cosmetic getCosmetic(UUID id) {
        return API.getSelectedCosmetic(id);
    }

    @FunctionalInterface
    private interface TryApiCall {
        void run() throws Exception;
    }
}
