package tech.thatgravyboat.creeperoverhaul.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.CommonListenerCookie;
import net.minecraft.network.Connection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tech.thatgravyboat.creeperoverhaul.common.config.ClientConfig;
import tech.thatgravyboat.creeperoverhaul.common.network.NetworkHandler;
import tech.thatgravyboat.creeperoverhaul.common.network.packets.ServerboundCosmeticPacket;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin extends ClientCommonPacketListenerImpl {

    protected ClientPacketListenerMixin(Minecraft minecraft, Connection connection, CommonListenerCookie commonListenerCookie) {
        super(minecraft, connection, commonListenerCookie);
    }

    @Inject(method = "handleLogin", at = @At("TAIL"))
    private void onPlayerJoin(CallbackInfo ci) {
        this.minecraft.tell(() -> NetworkHandler.NETWORK.sendToServer(
                new ServerboundCosmeticPacket(ClientConfig.showCosmetic.get())
        ));
    }
}
