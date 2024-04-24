package tech.thatgravyboat.creeperoverhaul.client.renderer.normal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;

import static net.minecraft.client.renderer.entity.LivingEntityRenderer.getOverlayCoords;

public class CreeperRenderer<E extends BaseCreeper> extends GeoEntityRenderer<E> {

    public CreeperRenderer(EntityRendererProvider.Context renderManager, GeoModel<E> modelProvider) {
        super(renderManager, modelProvider);
        addRenderLayer(new CreeperGlowLayer<>(this));
        addRenderLayer(new CreeperPowerLayer<>(this));
    }

    @Override
    public void render(@NotNull E creeper, float entityYaw, float partialTicks, PoseStack stack, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
        float f = creeper.getSwelling(partialTicks);
        float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.01F;
        f = Mth.clamp(f, 0.0F, 1.0F);
        f = f * f;
        f = f * f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        stack.scale(f2, f3, f2);
        super.render(creeper, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(@NotNull E entity) {
        return this.model.getTextureResource(entity);
    }

    @Override
    public void reRender(BakedGeoModel model, PoseStack poseStack, MultiBufferSource bufferSource, E animatable, RenderType renderType, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        float f = animatable.getSwelling(partialTick);
        f = (int) (f * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
        super.reRender(model, poseStack, bufferSource, animatable, renderType, buffer, partialTick,
                packedLight, getOverlayCoords(animatable, f), red, green, blue, alpha);
    }

    @Override
    public RenderType getRenderType(E animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }
}
