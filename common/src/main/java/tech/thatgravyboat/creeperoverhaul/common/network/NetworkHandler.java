package tech.thatgravyboat.creeperoverhaul.common.network;

import com.teamresourceful.resourcefullib.common.network.Network;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.network.packets.ClientboundCosmeticPacket;
import tech.thatgravyboat.creeperoverhaul.common.network.packets.ServerboundCosmeticPacket;

public class NetworkHandler {

    public static final Network NETWORK = new Network(Creepers.id("main"), 1);

    public static void init() {
        NETWORK.register(ClientboundCosmeticPacket.TYPE);
        NETWORK.register(ServerboundCosmeticPacket.TYPE);
    }
}
