package tech.thatgravyboat.creeperoverhaul.client.renderer.replaced;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Creeper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoReplacedEntityRenderer;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.entity.ReplacedCreeper;
import tech.thatgravyboat.creeperoverhaul.common.utils.Events;

public class ReplacedCreeperRenderer extends GeoReplacedEntityRenderer<Creeper, ReplacedCreeper> {

    public ReplacedCreeperRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedCreeperModel<>(), new ReplacedCreeper());
        addRenderLayer(new ReplacedCreeperGlowLayer(this));
        addRenderLayer(new ReplacedCreeperPowerLayer(this));
    }

    @Override
    public void renderRecursively(PoseStack poseStack, ReplacedCreeper animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        boolean shouldRender = switch (bone.getName()) {
            case "Santa" -> Creepers.EVENT.equals(Events.CHRISTMAS);
            case "Witch" -> Creepers.EVENT.equals(Events.HALLOWEEN);
            case "Stpat" -> Creepers.EVENT.equals(Events.ST_PATRICKS_DAY);
            default -> true;
        };

        if (!shouldRender) return;
        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void preRender(PoseStack stack, ReplacedCreeper animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(stack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);

        float f = this.currentEntity.getSwelling(partialTick);
        float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.01F;
        f = Mth.clamp(f, 0.0F, 1.0F);
        f = (f * f) * (f * f);
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        stack.scale(f2, f3, f2);
    }

    @Override
    public int getPackedOverlay(ReplacedCreeper animatable, float u, float partialTick) {
        return super.getPackedOverlay(animatable, getSwellOverlay(this.currentEntity, partialTick));
    }

    protected float getSwellOverlay(Creeper creeper, float partialTicks) {
        float f = creeper.getSwelling(partialTicks);
        return (int) (f * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
    }

    @Override
    public RenderType getRenderType(ReplacedCreeper animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }

    @Override
    public boolean shouldShowName(@NotNull Creeper entity) {
        if (this.currentEntity == null) return false;
        return super.shouldShowName(entity);
    }
}
