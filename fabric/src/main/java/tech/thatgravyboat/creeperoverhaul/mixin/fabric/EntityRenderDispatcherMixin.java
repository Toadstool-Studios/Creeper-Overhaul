package tech.thatgravyboat.creeperoverhaul.mixin.fabric;

import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tech.thatgravyboat.creeperoverhaul.client.CreepersClient;

import java.util.Map;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

    @Shadow
    private Map<PlayerSkin.Model, EntityRenderer<? extends Player>> playerRenderers;

    @Inject(
            method = "onResourceManagerReload",
            at = @At("TAIL")
    )
    private void rbees$onResourceManagerReload(ResourceManager resourceManager, CallbackInfo ci) {
        CreepersClient.registerEntityLayers(
                id -> (PlayerRenderer) this.playerRenderers.get(id)
        );
    }
}