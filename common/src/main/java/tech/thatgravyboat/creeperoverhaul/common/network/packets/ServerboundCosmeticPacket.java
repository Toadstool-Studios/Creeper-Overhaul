package tech.thatgravyboat.creeperoverhaul.common.network.packets;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.NetworkHandle;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;
import com.teamresourceful.resourcefullib.common.network.defaults.CodecPacketType;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.utils.ServerCosmetics;

public record ServerboundCosmeticPacket(
    boolean shown
) implements Packet<ServerboundCosmeticPacket> {

    public static final ServerboundPacketType<ServerboundCosmeticPacket> TYPE = CodecPacketType.Server.create(
            Creepers.id("set_cosmetic"),
            ByteCodec.BOOLEAN.map(ServerboundCosmeticPacket::new, ServerboundCosmeticPacket::shown),
            NetworkHandle.handle((message, player) -> ServerCosmetics.setCosmeticShown(player, message.shown()))
    );

    @Override
    public PacketType<ServerboundCosmeticPacket> type() {
        return TYPE;
    }
}
