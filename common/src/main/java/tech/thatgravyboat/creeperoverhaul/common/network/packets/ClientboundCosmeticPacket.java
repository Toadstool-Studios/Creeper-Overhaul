package tech.thatgravyboat.creeperoverhaul.common.network.packets;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.ClientboundPacketType;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.defaults.CodecPacketType;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.client.cosmetics.Cosmetics;

import java.util.UUID;

public record ClientboundCosmeticPacket(
    Object2BooleanMap<UUID> players
) implements Packet<ClientboundCosmeticPacket> {

    public static final ClientboundPacketType<ClientboundCosmeticPacket> TYPE = new Handler();

    @Override
    public PacketType<ClientboundCosmeticPacket> type() {
        return TYPE;
    }

    private static class Handler extends CodecPacketType<ClientboundCosmeticPacket> implements ClientboundPacketType<ClientboundCosmeticPacket> {

        private static final ByteCodec<Object2BooleanMap<UUID>> UUID_TO_BOOL = ByteCodec.mapOf(ByteCodec.UUID, ByteCodec.BOOLEAN)
                .map(Object2BooleanOpenHashMap::new, Object2BooleanOpenHashMap::new);

        public Handler() {
            super(
                    ClientboundCosmeticPacket.class,
                    Creepers.id("cosmetic"),
                    UUID_TO_BOOL.map(ClientboundCosmeticPacket::new, ClientboundCosmeticPacket::players)
            );
        }

        @Override
        public Runnable handle(ClientboundCosmeticPacket message) {
            return () -> {
                for (var entry : message.players().object2BooleanEntrySet()) {
                    Cosmetics.setCosmeticShown(entry.getKey(), entry.getBooleanValue());
                }
            };
        }
    }
}
