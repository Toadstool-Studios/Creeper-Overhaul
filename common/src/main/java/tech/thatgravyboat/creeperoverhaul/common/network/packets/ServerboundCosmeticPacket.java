package tech.thatgravyboat.creeperoverhaul.common.network.packets;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.ClientboundPacketType;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;
import com.teamresourceful.resourcefullib.common.network.defaults.CodecPacketType;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.world.entity.player.Player;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.client.cosmetics.Cosmetics;
import tech.thatgravyboat.creeperoverhaul.common.utils.ServerCosmetics;

import java.util.UUID;
import java.util.function.Consumer;

public record ServerboundCosmeticPacket(
    boolean shown
) implements Packet<ServerboundCosmeticPacket> {

    public static final ServerboundPacketType<ServerboundCosmeticPacket> TYPE = new Handler();

    @Override
    public PacketType<ServerboundCosmeticPacket> type() {
        return TYPE;
    }

    private static class Handler extends CodecPacketType<ServerboundCosmeticPacket> implements ServerboundPacketType<ServerboundCosmeticPacket> {

        public Handler() {
            super(
                    ServerboundCosmeticPacket.class,
                    Creepers.id("cosmetic"),
                    ByteCodec.BOOLEAN.map(ServerboundCosmeticPacket::new, ServerboundCosmeticPacket::shown)
            );
        }

        @Override
        public Consumer<Player> handle(ServerboundCosmeticPacket message) {
            return player -> ServerCosmetics.setCosmeticShown(player, message.shown());
        }
    }
}
