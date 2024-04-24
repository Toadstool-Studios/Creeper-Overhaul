package tech.thatgravyboat.creeperoverhaul.client.renderer.normal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import tech.thatgravyboat.creeperoverhaul.client.RenderTypes;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.utils.PlatformUtils;

public class CreeperPowerLayer<E extends BaseCreeper> extends GeoRenderLayer<E> {

    public CreeperPowerLayer(GeoRenderer<E> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, E creeper, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if (!creeper.isPowered() || PlatformUtils.shouldHidePowerLayer()) return;

        float f = (float)creeper.tickCount + partialTick;

        RenderType type = RenderTypes.getSwirl(creeper.type.chargedTexture().apply(creeper), f * 0.005F % 1F,  f * 0.005F % 1F);
        VertexConsumer armorConsumer = bufferSource.getBuffer(type);

        getRenderer().reRender(
                getDefaultBakedModel(creeper), poseStack, bufferSource, creeper,
                type, armorConsumer,
                partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                1f, 1f, 1f, 1f);
    }
}
