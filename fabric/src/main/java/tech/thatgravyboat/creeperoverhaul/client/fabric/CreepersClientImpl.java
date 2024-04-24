package tech.thatgravyboat.creeperoverhaul.client.fabric;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class CreepersClientImpl {
    public static <E extends Entity> void registerRenderer(EntityType<E> entity, EntityRendererProvider<E> renderer) {
        EntityRendererRegistry.register(entity, renderer);
    }

    public static void registerBlockRenderType(Supplier<Block> block, RenderType type) {
        BlockRenderLayerMap.INSTANCE.putBlock(block.get(), type);
    }
}
