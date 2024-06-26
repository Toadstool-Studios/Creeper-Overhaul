package tech.thatgravyboat.creeperoverhaul.common.utils;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.world.entity.player.Player;
import tech.thatgravyboat.creeperoverhaul.common.network.NetworkHandler;
import tech.thatgravyboat.creeperoverhaul.common.network.packets.ClientboundCosmeticPacket;

import java.util.UUID;

public class ServerCosmetics {

    private static final Object2BooleanMap<UUID> PLAYER = new Object2BooleanOpenHashMap<>();

    public static void setCosmeticShown(Player player, boolean shown) {
        PLAYER.put(player.getUUID(), shown);
        if (player.getServer() == null) return;
        NetworkHandler.NETWORK.sendToAllPlayers(new ClientboundCosmeticPacket(PLAYER), player.getServer());
    }

    public static void sendToPlayer(Player player) {
        NetworkHandler.NETWORK.sendToPlayer(new ClientboundCosmeticPacket(PLAYER), player);
    }

    public static void onPlayerJoin(Player player) {
        sendToPlayer(player);
    }
}
